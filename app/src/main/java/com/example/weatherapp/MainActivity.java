package com.example.weatherapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherapp.adapter.WeatherAdapter;
import com.example.weatherapp.api.ApiNinjasClient;
import com.example.weatherapp.api.ApiNinjasService;
import com.example.weatherapp.api.OpenMeteoClient;
import com.example.weatherapp.api.OpenMeteoService;
import com.example.weatherapp.model.CityInfo;
import com.example.weatherapp.model.CityWeatherItem;
import com.example.weatherapp.model.WeatherResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String API_NINJAS_KEY = "LHb6YonyTmxValpfEWcBK5QMn3E9pu7qbkC4vtZE";

    private static final String DAILY_PARAMS =
            "temperature_2m_max,temperature_2m_min,weathercode,wind_speed_10m_max";

    private static final List<String> DEFAULT_CITIES = Arrays.asList(
            "Moscow", "Kazan", "Saint Petersburg", "Novosibirsk", "Vladivostok"
    );
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private List<CityWeatherItem> cityWeatherList;
    private Button btnAddCity;

    private int pendingRequests = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupRecyclerView();
        loadDefaultCities();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
        btnAddCity   = findViewById(R.id.btnAddCity);

        btnAddCity.setOnClickListener(v -> showAddCityDialog());
    }

    private void setupRecyclerView() {
        cityWeatherList = new ArrayList<>();
        adapter = new WeatherAdapter(cityWeatherList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadDefaultCities() {
        for (String city : DEFAULT_CITIES) {
            startCityWeatherLoad(city);
        }
    }
    private void showAddCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавить город");
        builder.setMessage("Введите название города на английском");

        final EditText input = new EditText(this);
        input.setHint("Например: Izhevsk");
        input.setSingleLine(true);
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        input.setPadding(padding, padding, padding, padding);
        builder.setView(input);

        builder.setPositiveButton("Добавить", (dialog, which) -> {
            String cityName = input.getText().toString().trim();
            if (!cityName.isEmpty()) {
                boolean alreadyAdded = cityWeatherList.stream()
                        .anyMatch(item -> item.getCityInfo().getName()
                                .equalsIgnoreCase(cityName));
                if (alreadyAdded) {
                    Toast.makeText(this, "Город уже в списке", Toast.LENGTH_SHORT).show();
                } else {
                    startCityWeatherLoad(cityName);
                }
            } else {
                Toast.makeText(this, "Введите название города", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    private void startCityWeatherLoad(String cityName) {
        incrementPending();

        ApiNinjasService service = ApiNinjasClient.getClient()
                .create(ApiNinjasService.class);

        Call<List<CityInfo>> call = service.getCityCoordinates(API_NINJAS_KEY, cityName);
        call.enqueue(new Callback<List<CityInfo>>() {

            @Override
            public void onResponse(Call<List<CityInfo>> call,
                                   Response<List<CityInfo>> response) {
                if (response.isSuccessful()
                        && response.body() != null
                        && !response.body().isEmpty()) {

                    CityInfo cityInfo = response.body().get(0);
                    CityWeatherItem item = new CityWeatherItem(cityInfo);
                    addItemToList(item);
                    loadWeatherData(item);

                } else {
                    decrementPending();
                    showToast("Город не найден: " + cityName);
                }
            }

            @Override
            public void onFailure(Call<List<CityInfo>> call, Throwable t) {
                decrementPending();
                showToast("Ошибка сети: " + t.getMessage());
            }
        });
    }

    private void loadWeatherData(CityWeatherItem item) {
        OpenMeteoService service = OpenMeteoClient.getClient()
                .create(OpenMeteoService.class);

        double lat = item.getCityInfo().getLatitude();
        double lon = item.getCityInfo().getLongitude();

        Call<WeatherResponse> call = service.getWeather(
                lat,
                lon,
                true,
                DAILY_PARAMS,
                "auto",
                7
        );

        call.enqueue(new Callback<WeatherResponse>() {

            @Override
            public void onResponse(Call<WeatherResponse> call,
                                   Response<WeatherResponse> response) {
                decrementPending();
                int index = cityWeatherList.indexOf(item);
                if (index == -1) return;

                if (response.isSuccessful() && response.body() != null) {
                    item.setWeatherResponse(response.body());
                } else {
                    item.setError("Ошибка API: " + response.code());
                }
                adapter.notifyItemChanged(index);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                decrementPending();
                int index = cityWeatherList.indexOf(item);
                if (index == -1) return;

                item.setError("Нет соединения");
                adapter.notifyItemChanged(index);
            }
        });
    }
    private void addItemToList(CityWeatherItem item) {
        cityWeatherList.add(item);
        adapter.notifyItemInserted(cityWeatherList.size() - 1);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void incrementPending() {
        pendingRequests++;
        btnAddCity.setEnabled(pendingRequests == 0);
    }
    private void decrementPending() {
        pendingRequests = Math.max(0, pendingRequests - 1);
        btnAddCity.setEnabled(pendingRequests == 0);
    }
}
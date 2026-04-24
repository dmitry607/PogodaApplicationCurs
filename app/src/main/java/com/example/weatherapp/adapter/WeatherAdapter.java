package com.example.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.CityWeatherItem;
import com.example.weatherapp.model.CurrentWeather;
import com.example.weatherapp.model.DailyWeather;
import com.example.weatherapp.util.WeatherUtils;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private final List<CityWeatherItem> items;

    public WeatherAdapter(List<CityWeatherItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        CityWeatherItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class WeatherViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvCityName;
        private final TextView tvCountry;

        private final ProgressBar progressBar;

        private final LinearLayout layoutWeatherData;
        private final TextView tvEmoji;
        private final TextView tvCurrentTemp;
        private final TextView tvWeatherDesc;
        private final TextView tvWindSpeed;
        private final TextView tvCoordinates;

        private final LinearLayout layoutForecast;
        private final TextView tvDay1;
        private final TextView tvDay2;
        private final TextView tvDay3;
        private final TextView tvDay4;
        private final TextView tvDay5;


        private final TextView tvError;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCityName    = itemView.findViewById(R.id.tvCityName);
            tvCountry     = itemView.findViewById(R.id.tvCountry);
            progressBar   = itemView.findViewById(R.id.progressBar);
            layoutWeatherData = itemView.findViewById(R.id.layoutWeatherData);
            tvEmoji       = itemView.findViewById(R.id.tvEmoji);
            tvCurrentTemp = itemView.findViewById(R.id.tvCurrentTemp);
            tvWeatherDesc = itemView.findViewById(R.id.tvWeatherDesc);
            tvWindSpeed   = itemView.findViewById(R.id.tvWindSpeed);
            tvCoordinates = itemView.findViewById(R.id.tvCoordinates);
            layoutForecast= itemView.findViewById(R.id.layoutForecast);
            tvDay1        = itemView.findViewById(R.id.tvDay1);
            tvDay2        = itemView.findViewById(R.id.tvDay2);
            tvDay3        = itemView.findViewById(R.id.tvDay3);
            tvDay4        = itemView.findViewById(R.id.tvDay4);
            tvDay5        = itemView.findViewById(R.id.tvDay5);
            tvError       = itemView.findViewById(R.id.tvError);
        }

        public void bind(CityWeatherItem item) {
            tvCityName.setText(item.getCityInfo().getName());
            tvCountry.setText("🌍 " + item.getCityInfo().getCountry());
            tvCoordinates.setText(String.format(
                    "%.2f°N  %.2f°E",
                    item.getCityInfo().getLatitude(),
                    item.getCityInfo().getLongitude()
            ));

            if (item.isLoading()) {
                progressBar.setVisibility(View.VISIBLE);
                layoutWeatherData.setVisibility(View.GONE);
                layoutForecast.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);

            } else if (item.hasError()) {
                progressBar.setVisibility(View.GONE);
                layoutWeatherData.setVisibility(View.GONE);
                layoutForecast.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("⚠️ " + item.getErrorMessage());

            } else {
                progressBar.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);
                layoutWeatherData.setVisibility(View.VISIBLE);
                layoutForecast.setVisibility(View.VISIBLE);

                bindCurrentWeather(item);
                bindDailyForecast(item);
            }
        }

        private void bindCurrentWeather(CityWeatherItem item) {
            CurrentWeather cw = item.getWeatherResponse().getCurrentWeather();
            if (cw == null) return;

            int code = cw.getWeatherCode();
            boolean isDay = cw.isDay();

            tvEmoji.setText(WeatherUtils.getWeatherEmoji(code, isDay));
            tvCurrentTemp.setText(WeatherUtils.formatTemperature(cw.getTemperature()));
            tvWeatherDesc.setText(WeatherUtils.getWeatherDescription(code));
            tvWindSpeed.setText("💨 Ветер: " + WeatherUtils.formatWindSpeed(cw.getWindSpeed()));
        }

        private void bindDailyForecast(CityWeatherItem item) {
            DailyWeather daily = item.getWeatherResponse().getDaily();
            if (daily == null || daily.getDaysCount() == 0) {
                layoutForecast.setVisibility(View.GONE);
                return;
            }

            TextView[] dayViews = {tvDay1, tvDay2, tvDay3, tvDay4, tvDay5};
            int daysToShow = Math.min(5, daily.getDaysCount());

            for (int i = 0; i < dayViews.length; i++) {
                if (i < daysToShow) {
                    dayViews[i].setVisibility(View.VISIBLE);
                    String date = daily.getTime().get(i);
                    String dayName = (i == 0) ? "Сегодня" : WeatherUtils.getDayOfWeek(date);
                    String maxTemp = WeatherUtils.formatTemperature(daily.getSafeMax(i));
                    String minTemp = WeatherUtils.formatTemperature(daily.getSafeMin(i));
                    String emoji = WeatherUtils.getWeatherEmoji(daily.getSafeWeatherCode(i), true);

                    dayViews[i].setText(String.format(
                            "%s  %s  %s / %s", dayName, emoji, maxTemp, minTemp
                    ));
                } else {
                    dayViews[i].setVisibility(View.GONE);
                }
            }
        }
    }
}





package com.example.weatherapp.util;

public class WeatherUtils {

    public static String getWeatherDescription(int code) {
        switch (code) {
            case 0:  return "Ясно";
            case 1:  return "Преимущественно ясно";
            case 2:  return "Переменная облачность";
            case 3:  return "Пасмурно";
            case 45: return "Туман";
            case 48: return "Изморозевый туман";
            case 51: return "Лёгкая морось";
            case 53: return "Умеренная морось";
            case 55: return "Сильная морось";
            case 56: return "Ледяная морось";
            case 57: return "Сильная ледяная морось";
            case 61: return "Лёгкий дождь";
            case 63: return "Умеренный дождь";
            case 65: return "Сильный дождь";
            case 66: return "Ледяной дождь";
            case 67: return "Сильный ледяной дождь";
            case 71: return "Лёгкий снег";
            case 73: return "Умеренный снег";
            case 75: return "Сильный снег";
            case 77: return "Снежные зёрна";
            case 80: return "Лёгкий ливень";
            case 81: return "Умеренный ливень";
            case 82: return "Сильный ливень";
            case 85: return "Снежный ливень";
            case 86: return "Сильный снежный ливень";
            case 95: return "Гроза";
            case 96: return "Гроза с градом";
            case 99: return "Гроза с сильным градом";
            default: return "Неизвестно (код " + code + ")";
        }
    }

    public static String getWeatherEmoji(int code, boolean isDay) {
        if (code == 0) return isDay ? "☀️" : "🌙";
        if (code <= 2) return isDay ? "🌤️" : "🌤️";
        if (code == 3) return "☁️";
        if (code <= 48) return "🌫️";
        if (code <= 57) return "🌦️";
        if (code <= 67) return "🌧️";
        if (code <= 77) return "❄️";
        if (code <= 82) return "🌧️";
        if (code <= 86) return "🌨️";
        return "⛈️";
    }
    public static String formatTemperature(double temp) {
        return String.format("%.0f°C", temp);
    }

    public static String formatWindSpeed(double speed) {
        return String.format("%.1f км/ч", speed);
    }

    public static String getDayOfWeek(String dateStr) {
        try {
            String[] parts = dateStr.split("-");
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);

            int year = Integer.parseInt(parts[0]);
            if (month < 3) {
                month += 12;
                year--;
            }
            int k = year % 100;
            int j = year / 100;
            int h = (day + (13 * (month + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;
            int dayOfWeek = ((h + 5) % 7);

            String[] days = {"Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс"};
            return days[dayOfWeek];
        } catch (Exception e) {
            return dateStr;
        }
    }
    public static String formatShortDate(String dateStr) {
        try {
            String[] parts = dateStr.split("-");
            String[] months = {"", "янв", "фев", "мар", "апр", "май", "июн",
                    "июл", "авг", "сен", "окт", "ноя", "дек"};
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            return day + " " + months[month];
        } catch (Exception e) {
            return dateStr;
        }
    }
}
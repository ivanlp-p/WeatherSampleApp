package com.example.ivan.weathersampleapp.hourly.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.weathersampleapp.R;
import com.example.ivan.weathersampleapp.hourly.entity.HourlyForecast;
import com.squareup.picasso.Picasso;

import java.util.List;


public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyHolder> {

    private Context context;
    private List<HourlyForecast> forecastList;

    public HourlyAdapter(Context context, List<HourlyForecast> forecastList) {
        this.context = context;
        this.forecastList = forecastList;
    }

    @Override
    public HourlyAdapter.HourlyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_hour_weather,
                parent,
                false
        );

        return new HourlyHolder(v);
    }

    @Override
    public void onBindViewHolder(HourlyAdapter.HourlyHolder holder, int position) {

        holder.cardTime.setText(forecastList.get(position).getFCTTIME().getHour_padded() + ":" +
                                forecastList.get(position).getFCTTIME().getMin());
        holder.cardDate.setText(forecastList.get(position).getFCTTIME().getWeekday_name_abbrev() + ", " +
                                forecastList.get(position).getFCTTIME().getMonth_name_abbrev() + " " +
                                forecastList.get(position).getFCTTIME().getMday_padded());
        Picasso.with(context).load(forecastList.get(position).getIcon_url()).into(holder.cardIcon);
        holder.cardTemp.setText(forecastList.get(position).getTemp().getMetric() + " °C");
        holder.cardWeather.setText(forecastList.get(position).getCondition());
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    class HourlyHolder extends RecyclerView.ViewHolder {

        private final TextView cardTime;
        private final TextView cardDate;
        private final ImageView cardIcon;
        private final TextView cardTemp;
        private final TextView cardWeather;

        public HourlyHolder(View itemView) {
            super(itemView);

            cardTime = (TextView) itemView.findViewById(R.id.card_hour_time);
            cardDate = (TextView) itemView.findViewById(R.id.card_hour_date);
            cardIcon = (ImageView) itemView.findViewById(R.id.card_hour_weather_icon);
            cardTemp = (TextView) itemView.findViewById(R.id.card_hour_temp);
            cardWeather = (TextView) itemView.findViewById(R.id.card_hour_weather);
        }
    }
}

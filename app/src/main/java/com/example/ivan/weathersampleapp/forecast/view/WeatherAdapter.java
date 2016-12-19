package com.example.ivan.weathersampleapp.forecast.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivan.weathersampleapp.R;
import com.example.ivan.weathersampleapp.forecast.entity.forecast.ForecastDay;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by I.Laukhin on 16.12.2016.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    private List<ForecastDay> forecastList;
    private Context context;

    public WeatherAdapter(Context context, List<ForecastDay> forecastList) {
        this.context = context;
        this.forecastList = forecastList;
    }

    @Override
    public WeatherAdapter.WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_day_weather,
                parent,
                false
        );

        return new WeatherHolder(v);
    }

    @Override
    public void onBindViewHolder(WeatherAdapter.WeatherHolder holder, int position) {

            holder.cardDay.setText(forecastList.get(position).getDate().getWeekday_short());
            Picasso.with(context)
                    .load(forecastList.get(position).getIcon_url())
                    .into(holder.cardIcon);
            holder.cardConditions.setText(forecastList.get(position).getConditions());
            holder.cardTemp.setText(
                    forecastList.get(position).getHigh().getCelsius() + "/" + forecastList.get(position).getLow().getCelsius() + " °C"
            );



    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    class WeatherHolder extends RecyclerView.ViewHolder {

        private final TextView cardDay;
        private final ImageView cardIcon;
        private final TextView cardConditions;
        private final TextView cardTemp;

        public WeatherHolder(View itemView) {
            super(itemView);

            cardDay = (TextView) itemView.findViewById(R.id.card_day_name);
            cardIcon = (ImageView) itemView.findViewById(R.id.card_weather_icon);
            cardConditions = (TextView) itemView.findViewById(R.id.card_day_conditions);
            cardTemp = (TextView) itemView.findViewById(R.id.card_day_temp);
        }
    }
}

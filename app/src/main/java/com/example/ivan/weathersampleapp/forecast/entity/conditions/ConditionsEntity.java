package com.example.ivan.weathersampleapp.forecast.entity.conditions;

/**
 * Created by I.Laukhin on 16.12.2016.
 */

public class ConditionsEntity {

    private CurrentObservation current_observation;

    public ConditionsEntity() {
    }

    public ConditionsEntity(CurrentObservation current_observation) {
        this.current_observation = current_observation;
    }

    public CurrentObservation getObservation() {
        return current_observation;
    }

    public void setObservation(CurrentObservation obsercurrent_observation) {
        this.current_observation = current_observation;
    }
}

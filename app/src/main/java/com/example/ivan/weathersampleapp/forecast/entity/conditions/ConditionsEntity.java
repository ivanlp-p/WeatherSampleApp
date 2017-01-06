package com.example.ivan.weathersampleapp.forecast.entity.conditions;

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

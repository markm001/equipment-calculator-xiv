package com.ccat.equipmentcalculator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

public class XivClientResponse {
    @JsonProperty("Pagination")
    private HashMap<String,Integer> pagination;
    @JsonProperty("Results")
    private List<ItemResponse> results;
    @JsonProperty("SpeedMs")
    private Integer speedMs;

    public XivClientResponse(){

    }

    public XivClientResponse(HashMap<String, Integer> pagination, List<ItemResponse> results, Integer speedMs) {
        this.pagination = pagination;
        this.results = results;
        this.speedMs = speedMs;
    }

    public HashMap<String, Integer> getPagination() {
        return pagination;
    }

    public void setPagination(HashMap<String, Integer> pagination) {
        this.pagination = pagination;
    }

    public List<ItemResponse> getResults() {
        return results;
    }

    public void setResults(List<ItemResponse> results) {
        this.results = results;
    }

    public Integer getSpeedMs() {
        return speedMs;
    }

    public void setSpeedMs(Integer speedMs) {
        this.speedMs = speedMs;
    }

    @Override
    public String toString() {
        return "XivClientResponse{" +
                "pagination=" + pagination +
                ", results=" + results +
                ", speedMs=" + speedMs +
                '}';
    }
}

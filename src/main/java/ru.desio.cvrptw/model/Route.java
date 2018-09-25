package ru.desio.cvrptw.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true)
public class Route {
    private List<Customer> customers;
    private List<Double> startServingTime;

    public Route() {
        this.customers = new ArrayList<>();
        this.startServingTime = new ArrayList<>();
    }
}

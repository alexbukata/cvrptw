package ru.desio.cvrptw.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Vehicle {
    private int id;
    private int maxCapacity;
    private int currentCapacity;
    private double currentTime;
    private Customer currentCustomer;

    public void moveAndServe(Customer nextCustomer) {
        currentCapacity -= nextCustomer.demand();
        currentTime = Math.max(nextCustomer.readyTime() + nextCustomer.serviceTime(), currentTime + nextCustomer.serviceTime());
    }
}

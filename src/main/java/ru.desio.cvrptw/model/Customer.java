package ru.desio.cvrptw.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class Customer {
    private int id;
    private int x;
    private int y;
    private int demand;
    private int readyTime;
    private int dueDate;
    private int serviceTime;

    public double distance(Customer otherCustomer) {
        return Math.sqrt(Math.pow(x - otherCustomer.x, 2) + Math.pow(y - otherCustomer.y, 2));
    }
}

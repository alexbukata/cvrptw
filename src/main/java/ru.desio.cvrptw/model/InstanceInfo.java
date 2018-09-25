package ru.desio.cvrptw.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(fluent = true)
public class InstanceInfo {
    private VehiclesConstraints vehiclesConstraints;
    private List<Customer> customers;
}

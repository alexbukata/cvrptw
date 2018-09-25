package ru.desio.cvrptw.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class VehiclesConstraints {
    private int numberOfVehicles;
    private int vehicleCapacity;
}

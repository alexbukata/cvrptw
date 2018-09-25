package ru.desio.cvrptw;

import ru.desio.cvrptw.model.Vehicle;
import ru.desio.cvrptw.model.VehiclesConstraints;

import java.util.ArrayList;
import java.util.List;

public class VehicleFactory {
    private VehiclesConstraints vehiclesConstraints;
    private List<Vehicle> vehicles;

    public VehicleFactory(VehiclesConstraints vehiclesConstraints) {
        this.vehiclesConstraints = vehiclesConstraints;
        this.vehicles = new ArrayList<>();
    }

    public boolean hasAvailableVehicles() {
        return vehicles.size() != vehiclesConstraints.numberOfVehicles();
    }

    public Vehicle build() {
        if (!hasAvailableVehicles()) {
            throw new RuntimeException("No available vehicles");
        }

        Vehicle vehicle = new Vehicle()
                .id(vehicles.size() + 1)
                .maxCapacity(vehiclesConstraints.vehicleCapacity())
                .currentCapacity(vehiclesConstraints.vehicleCapacity())
                .currentTime(0);
        vehicles.add(vehicle);
        return vehicle;
    }
}
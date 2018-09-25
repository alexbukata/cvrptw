package ru.desio.cvrptw.algorithms;

import ru.desio.cvrptw.VehicleFactory;
import ru.desio.cvrptw.model.Customer;
import ru.desio.cvrptw.model.Route;
import ru.desio.cvrptw.model.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoutingGreedyAlgorithm {

    private final VehicleFactory vehicleFactory;
    private List<Customer> customers;
    private List<Customer> visitedCustomers;
    private Customer depot;
    private boolean init;


    public RoutingGreedyAlgorithm(List<Customer> customers, VehicleFactory vehicleFactory) {
        this.customers = customers;
        this.vehicleFactory = vehicleFactory;
        this.visitedCustomers = new ArrayList<>();
    }

    public void init() {
        depot = customers.stream()
                .filter(customer -> customer.id() == 0)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Depot has not been found"));
        customers = customers.stream()
                .filter(customer -> customer.id() != 0)
                .collect(Collectors.toList());
        init = true;
    }

    public List<Route> findGreedy() {
        if (!init) {
            init();
        }
        List<Route> routes = new ArrayList<>();
        while (visitedCustomers.size() != customers.size() && vehicleFactory.hasAvailableVehicles()) {
            Vehicle vehicle = vehicleFactory.build();
            Route route = new Route();
            route.customers().add(depot);
            route.startServingTime().add(0d);
            vehicle.currentCustomer(depot);

            serveCustomers(vehicle, route);

            vehicle.currentCustomer(depot);
            route.startServingTime().add(vehicle.currentTime());
            route.customers().add(depot);
            routes.add(route);
        }
        return routes;
    }

    private void serveCustomers(Vehicle vehicle, Route route) {
        while (visitedCustomers.size() != customers.size()) {
            Customer nextCustomer = pickNextCustomer(vehicle);
            if (nextCustomer == null) {
                return;
            }
            route.customers().add(nextCustomer);
            vehicle.moveAndServe(nextCustomer);
            route.startServingTime().add(vehicle.currentTime() - nextCustomer.serviceTime());
            visitedCustomers.add(nextCustomer);
        }
    }

    private Customer pickNextCustomer(Vehicle vehicle) {
        Customer currentCustomer = vehicle.currentCustomer();
        return customers.stream()
                .filter(customer -> !visitedCustomers.contains(customer))
                .sorted(Comparator.comparingDouble(Customer::readyTime))
                .sorted(Comparator.comparingDouble(currentCustomer::distance))
                .filter(customer -> vehicle.currentTime() < customer.dueDate())
                .filter(customer -> vehicle.currentTime() + customer.serviceTime() < depot.dueDate())
                .filter(customer -> vehicle.currentCapacity() > customer.demand())
                .findFirst()
                .orElse(null);
    }
}

package ru.desio.cvrptw.runners;

import ru.desio.cvrptw.CvrptwInputReader;
import ru.desio.cvrptw.RouteValidator;
import ru.desio.cvrptw.VehicleFactory;
import ru.desio.cvrptw.algorithms.RoutingGreedyAlgorithm;
import ru.desio.cvrptw.model.InstanceInfo;
import ru.desio.cvrptw.model.Route;

import java.util.List;

public class IteratedLocalSearchRunner {

    public static void main(String[] args) {
        CvrptwInputReader cvrptwInputReader = new CvrptwInputReader();
        InstanceInfo instanceInfo = cvrptwInputReader.fromFile(args[0]);
        VehicleFactory vehicleFactory = new VehicleFactory(instanceInfo.vehiclesConstraints());
        RoutingGreedyAlgorithm routingGreedyAlgorithm = new RoutingGreedyAlgorithm(instanceInfo.customers(), vehicleFactory);
        List<Route> routes = routingGreedyAlgorithm.findGreedy();
        RouteValidator.validateAll(instanceInfo, routes);
        System.out.println();
    }
}

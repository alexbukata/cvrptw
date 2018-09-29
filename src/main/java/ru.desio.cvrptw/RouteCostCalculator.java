package ru.desio.cvrptw;

import ru.desio.cvrptw.model.Route;

import java.util.List;

public class RouteCostCalculator {

    public static Double calculateCost(List<Route> routes) {
        return routes.stream()
                .mapToDouble(route -> route.startServingTime().get(route.startServingTime().size() - 1))
                .max().getAsDouble();
    }
}

package ru.desio.cvrptw;

import ru.desio.cvrptw.model.Customer;
import ru.desio.cvrptw.model.InstanceInfo;
import ru.desio.cvrptw.model.Route;

import java.util.Iterator;
import java.util.List;

import static java.lang.Math.abs;

public class RouteValidator {

    public static void validate(Route route) {
        Iterator<Customer> customerIterator = route.customers().iterator();
        Iterator<Double> timeIterator = route.startServingTime().iterator();
        Customer depot = customerIterator.next();
        Double startTime = timeIterator.next();
        assertThat(depot.id() == 0);
        assertThat(startTime == 0);
        Customer lastCustomer = depot;
        double actualTime = 0d;
        while (customerIterator.hasNext() && timeIterator.hasNext()) {
            Customer customer = customerIterator.next();
            Double startServingTime = timeIterator.next();
            assertThat(startServingTime <= customer.dueDate() && startServingTime >= customer.readyTime());
            assertThat(abs(startServingTime - (Math.max(actualTime + lastCustomer.distance(customer), customer.readyTime()))) <= 0.00001);
            actualTime = Math.max(actualTime + lastCustomer.distance(customer), customer.readyTime()) + customer.serviceTime();
            lastCustomer = customer;
            //check last is depot
            if (!customerIterator.hasNext() && !timeIterator.hasNext()) {
                assertThat(customer.id() == 0);
                assertThat(actualTime <= customer.dueDate());
            }
        }
    }

    public static void validateAll(InstanceInfo instanceInfo, List<Route> routes) {
        long visitedCount = routes.stream()
                .flatMap(route -> route.customers().stream())
                .map(Customer::id)
                .distinct()
                .count();
        assert instanceInfo.customers().size() + 1 == visitedCount;
        routes.forEach(RouteValidator::validate);
    }

    public static void assertThat(boolean bool) {
        if (!bool) {
            throw new RuntimeException("FAAAALSE");
        }
    }
}

package ru.desio.cvrptw;

import ru.desio.cvrptw.model.Customer;
import ru.desio.cvrptw.model.InstanceInfo;
import ru.desio.cvrptw.model.VehiclesConstraints;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CvrptwInputReader {

    public InstanceInfo fromFile(String filename) {
        try {
            List<String> strings = Files.readAllLines(Paths.get(filename));
            VehiclesConstraints vehiclesConstraints = parseVehiclesInfo(strings);
            List<Customer> customers = parseCustomers(strings);
            return new InstanceInfo()
                    .vehiclesConstraints(vehiclesConstraints)
                    .customers(customers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private VehiclesConstraints parseVehiclesInfo(List<String> strings) {
        String vehicleInfoString = strings.get(4);
        String[] vehicleInfoArray = vehicleInfoString.split("\\s+");
        return new VehiclesConstraints()
                .numberOfVehicles(Integer.parseInt(vehicleInfoArray[1]))
                .vehicleCapacity(Integer.parseInt(vehicleInfoArray[2]));
    }

    private List<Customer> parseCustomers(List<String> strings) {
        return strings.stream()
                .skip(9)
                .map(str -> str.split("\\s+"))
                .map(this::parseCustomer)
                .collect(Collectors.toList());
    }

    private Customer parseCustomer(String[] args) {
        return new Customer()
                .id(Integer.parseInt(args[1]))
                .x(Integer.parseInt(args[2]))
                .y(Integer.parseInt(args[3]))
                .demand(Integer.parseInt(args[4]))
                .readyTime(Integer.parseInt(args[5]))
                .dueDate(Integer.parseInt(args[6]))
                .serviceTime(Integer.parseInt(args[7]));
    }

    public static void main(String[] args) {
        CvrptwInputReader cvrptwCustomerReader = new CvrptwInputReader();
        cvrptwCustomerReader.fromFile("C:\\Projects\\cvrptw\\src\\main\\resources\\instances\\C266.txt");
    }
}

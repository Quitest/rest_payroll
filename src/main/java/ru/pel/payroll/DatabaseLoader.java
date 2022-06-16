package ru.pel.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseLoader.class);

    @Bean
    public CommandLineRunner initDB(EmployeeRepository employeeRepository, OrderRepository orderRepository){
        return args -> {
            employeeRepository.save(new Employee("Pit", "Smith", "staff"));
            employeeRepository.save(new Employee("Max", "Green", "director"));
            employeeRepository.findAll().forEach(employee->LOG.info("Preloaded -> {}", employee));

            orderRepository.save(new Order("MacBook", Status.COMPLETED));
            orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));
            orderRepository.findAll().forEach(order -> LOG.info("Preloaded -> {}", order));
        };
    }
}

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
    public CommandLineRunner initDB(EmployeeRepository repository){
        return args -> {
            LOG.info("Preloading "+repository.save(new Employee("Pit", "Smith", "staff")));
            LOG.info("Preloading "+repository.save(new Employee("Max", "Green", "director")));
        };
    }
}

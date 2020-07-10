package com.springboot.myshop;

import com.springboot.myshop.domain.customer.Customer;
import com.springboot.myshop.domain.customer.repository.CustomerRepository;
import com.springboot.myshop.domain.customer.value.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(MyshopApplication.class);

    //개발용 메서드
    @Bean
    CommandLineRunner initDatabase(CustomerRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Customer("email1","1234", new Address("address1"))));
            log.info("Preloading " + repository.save(new Customer("email2","5678", new Address("address2"))));
            log.info("Preloading " + repository.save(new Customer("email3","5678", new Address("address2"))));
            log.info("Preloading " + repository.save(new Customer("email4","5678", new Address("address2"))));
            log.info("Preloading " + repository.save(new Customer("email5","5678", new Address("address2"))));
            log.info("Preloading " + repository.save(new Customer("email6","5678", new Address("address2"))));
            log.info("Preloading " + repository.save(new Customer("email7","5678", new Address("address2"))));
            log.info("Preloading " + repository.save(new Customer("email8","5678", new Address("address2"))));
            log.info("Preloading " + repository.save(new Customer("email9","5678", new Address("address2"))));
            log.info("Preloading " + repository.save(new Customer("email10","5678", new Address("address2"))));
        };
    }
}

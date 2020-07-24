package com.springboot.myshop;

import com.springboot.myshop.domain.customer.entity.Customer;
import com.springboot.myshop.domain.customer.repository.CustomerRepository;
import com.springboot.myshop.domain.customer.value.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class MyshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyshopApplication.class, args);
    }

    private static final Logger log = LoggerFactory.getLogger(MyshopApplication.class);

    //개발용 메서드
    @Bean
    CommandLineRunner initDatabase(CustomerRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Customer("email11","1234", new Address("address1"))));
//            log.info("Preloading " + repository.save(new Customer("email21","5678", new Address("address2"))));
//            log.info("Preloading " + repository.save(new Customer("email31","5678", new Address("address2"))));
//            log.info("Preloading " + repository.save(new Customer("email41","5678", new Address("address2"))));
//            log.info("Preloading " + repository.save(new Customer("email51","5678", new Address("address2"))));
//            log.info("Preloading " + repository.save(new Customer("email61","5678", new Address("address2"))));
//            log.info("Preloading " + repository.save(new Customer("email71","5678", new Address("address2"))));
//            log.info("Preloading " + repository.save(new Customer("email81","5678", new Address("address2"))));
//            log.info("Preloading " + repository.save(new Customer("email91","5678", new Address("address2"))));
//            log.info("Preloading " + repository.save(new Customer("email101","5678", new Address("address2"))));
        };
    }
}

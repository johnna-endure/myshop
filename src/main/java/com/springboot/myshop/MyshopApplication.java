package com.springboot.myshop;

import com.springboot.myshop.domain.customer.entity.repository.CustomerRepository;
import com.springboot.myshop.domain.order.subdomain.item.entity.Item;
import com.springboot.myshop.domain.order.subdomain.item.entity.repository.ItemRepository;
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
    CommandLineRunner initDatabase(ItemRepository repository) {
        return args -> {
//            log.info("Preloading " + repository.save(new Item("name0", 1000, 1000)));
//            log.info("Preloading " + repository.save(new Item("name1", 1000, 1000)));
//            log.info("Preloading " + repository.save(new Item("name2", 1000, 1000)));
        };
    }
}

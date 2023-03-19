package org.sid.inventoryservice;

import org.sid.inventoryservice.entities.Product;
import org.sid.inventoryservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(ProductRepository pr, @Autowired(required = false)RepositoryRestConfiguration rrc){
        return  args -> {
            rrc.exposeIdsFor(Product.class);
            pr.saveAll(
                    List.of(
                            Product.builder().name("Computer").price(1233).quantity(2).build(),
                            Product.builder().name("Computer").price(504).quantity(23).build(),
                            Product.builder().name("Computer").price(2098).quantity(256).build()
                    )
            );

        };
    }
}

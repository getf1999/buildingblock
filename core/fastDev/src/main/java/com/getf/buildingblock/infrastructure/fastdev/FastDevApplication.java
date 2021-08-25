package com.getf.buildingblock.infrastructure.fastdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.getf.buildingblock.infrastructure.config",FastDevApplication.PACKAGE_NAME})
@SpringBootApplication
public class FastDevApplication {
    public static final String PACKAGE_NAME="com.getf.buildingblock.infrastructure.fastdev";

    public static void main(String[] args) {

        SpringApplication.run(FastDevApplication.class, args);
    }

}

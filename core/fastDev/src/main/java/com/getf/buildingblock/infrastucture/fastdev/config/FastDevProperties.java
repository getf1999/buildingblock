package com.getf.buildingblock.infrastucture.fastdev.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("getf.buildingblock.infrastucture.fastdev")
public class FastDevProperties {
}

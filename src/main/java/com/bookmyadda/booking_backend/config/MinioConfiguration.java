package com.bookmyadda.booking_backend.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Bean(name = "minioClientCustom")
    public MinioClient minioConfig(){
        return MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("admin", "password")
                .build();
    }
}

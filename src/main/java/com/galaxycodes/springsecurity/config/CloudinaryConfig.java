package com.galaxycodes.springsecurity.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "tiba101",
                "api_key", "377711213542593",
                "api_secret", "Wc5dz3oySz1uvySeYLIibDNdD_w",
                "secure", true
        ));
    }
}

package com.authorization.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import feign.codec.Decoder;


@Configuration
public class FeignClientConfig {
    @Bean
    public Decoder feignDecoder() {
        return new SpringDecoder(new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() {
                return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
            }
        });
    }
}
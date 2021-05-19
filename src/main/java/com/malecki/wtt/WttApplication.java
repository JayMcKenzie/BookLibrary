package com.malecki.wtt;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;



@SpringBootApplication
@EnableCaching
public class WttApplication {



    public static void main(String[] args){
        SpringApplication.run(WttApplication.class, args);
    }
}

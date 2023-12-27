package com.example.springboot;

import java.util.Arrays;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Application class for 'from-scratch' build
 * provides an base for the oparation of this app
 * @author David Liddle
 * @date 10/20/23
 */
@SpringBootApplication
public class Application {

  /** main method */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  /** Command Line Runner */
  @Bean
  public CommandLineRunner commadLineRunner(ApplicationContext ctx) {
    return args -> {
      System.out.println("Let's inspect the beans provided by Spring Boot:");
      String[] beanNames = ctx.getBeanDefinitionNames();
      Arrays.sort(beanNames);
      for (String beanName : beanNames) { System.out.println(beanName); }
    };
  }
  

}

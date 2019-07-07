package com.skcomms.dev.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
@RemoteApplicationEventScan
@SpringBootApplication
public class ApiDeliveryApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiDeliveryApplication.class, args);
  }

}

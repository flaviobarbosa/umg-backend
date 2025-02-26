package com.umg.umg_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfiguration {

  @Bean(name = "tokenRestClient")
  public RestClient tokenRestClient() {
    return RestClient.builder().baseUrl("https://accounts.spotify.com/api/token").build();
  }

  @Bean(name = "apiRestClient")
  @Primary
  public RestClient apiRestClient() {
    return RestClient.builder().baseUrl("https://api.spotify.com/v1").build();
  }

}

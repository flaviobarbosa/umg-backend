package com.umg.umg_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfiguration {

  @Bean
  public RestClient restClient() {
    return RestClient.builder().baseUrl("https://accounts.spotify.com/api/token").build();
  }

}

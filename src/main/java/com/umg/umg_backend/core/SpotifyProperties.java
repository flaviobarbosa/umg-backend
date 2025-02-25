package com.umg.umg_backend.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("spotify")
public class SpotifyProperties {

  private String clientId;
  private String clientSecret;

}

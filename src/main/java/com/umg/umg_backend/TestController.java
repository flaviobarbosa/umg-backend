package com.umg.umg_backend;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class TestController {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public String getSpotifyAccessToken() {
    return "Ok @ " + LocalDateTime.now().toString();
  }
}

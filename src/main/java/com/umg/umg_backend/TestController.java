package com.umg.umg_backend;

import com.umg.umg_backend.domain.model.SpotifyAccessToken;
import com.umg.umg_backend.domain.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

  @Autowired
  private SpotifyService spotifyService;

  @GetMapping
  public String getSpotifyAccessToken() {
    SpotifyAccessToken accessToken = spotifyService.getAccessToken();
    return accessToken.getAccessToken();
  }
}

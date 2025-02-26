package com.umg.umg_backend.api;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import com.umg.umg_backend.domain.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/codechallenge")
public class TrackController {

  @Autowired
  private TrackService trackService;

  @PostMapping("/createTrack")
  @ResponseStatus(HttpStatus.CREATED)
  public SpotifyMetadata createTrack(@RequestParam String isrc) {
    return trackService.createTrack(isrc);
  }


}

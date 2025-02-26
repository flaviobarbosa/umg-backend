package com.umg.umg_backend.domain.service.impl;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import com.umg.umg_backend.domain.service.SpotifyService;
import com.umg.umg_backend.domain.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackServiceImpl implements TrackService {

  @Autowired
  private SpotifyService spotifyService;

  @Override
  public void createTrack(String isrc) {
    //check if a track with the isrc already exists in database

    //call spotify to get metadata
    SpotifyMetadata metadata = spotifyService.getMetadata(isrc);

    //persist metadata

  }
}

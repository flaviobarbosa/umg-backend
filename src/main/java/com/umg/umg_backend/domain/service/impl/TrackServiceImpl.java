package com.umg.umg_backend.domain.service.impl;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import com.umg.umg_backend.domain.repository.SpotifyMetadataRepository;
import com.umg.umg_backend.domain.service.SpotifyService;
import com.umg.umg_backend.domain.service.TrackService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackServiceImpl implements TrackService {

  @Autowired
  private SpotifyService spotifyService;

  @Autowired
  private SpotifyMetadataRepository repository;

  @Override
  public SpotifyMetadata createTrack(String isrc) {
    //check if a track with the isrc already exists in database

    SpotifyMetadata metadata = spotifyService.getMetadata(isrc);
    metadata.setIsrc(isrc);

    return repository.save(metadata);

  }
}

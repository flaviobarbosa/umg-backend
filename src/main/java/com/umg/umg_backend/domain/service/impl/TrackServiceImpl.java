package com.umg.umg_backend.domain.service.impl;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import com.umg.umg_backend.domain.repository.SpotifyMetadataRepository;
import com.umg.umg_backend.domain.service.SpotifyService;
import com.umg.umg_backend.domain.service.TrackService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TrackServiceImpl implements TrackService {

  @Autowired
  private SpotifyService spotifyService;

  @Autowired
  private SpotifyMetadataRepository repository;

  @Override
  public SpotifyMetadata createTrack(String isrc) {
    Optional<SpotifyMetadata> byIsrc = repository.findByIsrc(isrc);

    if(byIsrc.isPresent()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "ISRC already saved");
    }

    SpotifyMetadata metadata = spotifyService.getMetadata(isrc);
    metadata.setIsrc(isrc);

    return repository.save(metadata);

  }
}

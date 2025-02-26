package com.umg.umg_backend.domain.service;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface TrackService {

  SpotifyMetadata createTrack(String isrc);
  SpotifyMetadata getTrackMetadata(String isrc);

}

package com.umg.umg_backend.domain.service;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import java.io.File;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface TrackService {

  SpotifyMetadata createTrack(String isrc);
  SpotifyMetadata getTrackMetadata(String isrc);
  File getCover(String isrc);
  List<SpotifyMetadata> getAllTracksMetadata();
}

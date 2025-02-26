package com.umg.umg_backend.domain.service;

import com.umg.umg_backend.domain.model.SpotifyAccessToken;
import com.umg.umg_backend.domain.model.SpotifyMetadata;

public interface SpotifyService {

  SpotifyMetadata getMetadata(String isrc);
  void downloadCover(SpotifyMetadata metadata);
}

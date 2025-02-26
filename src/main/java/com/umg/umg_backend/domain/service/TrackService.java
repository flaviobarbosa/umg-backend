package com.umg.umg_backend.domain.service;

import com.umg.umg_backend.domain.model.SpotifyMetadata;

public interface TrackService {

  SpotifyMetadata createTrack(String isrc);

}

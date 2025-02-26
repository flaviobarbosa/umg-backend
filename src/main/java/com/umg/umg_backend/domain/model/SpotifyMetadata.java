package com.umg.umg_backend.domain.model;

import lombok.Builder;

@Builder
public class SpotifyMetadata {

  private String name;
  private String artistName;
  private String albumName;
  private String albumId;
  private Boolean isExplicit;
  private Long playbackSeconds;

}

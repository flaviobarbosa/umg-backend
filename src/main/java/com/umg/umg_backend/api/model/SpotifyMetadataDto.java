package com.umg.umg_backend.api.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpotifyMetadataDto {
  private Integer id;
  private String isrc;
  private String name;
  private String artistName;
  private String albumName;
  private String albumId;
  private Boolean isExplicit;
  private Long playbackSeconds;
}

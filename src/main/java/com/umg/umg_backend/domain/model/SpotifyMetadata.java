package com.umg.umg_backend.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SpotifyMetadata {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String isrc;
  private String name;
  private String artistName;
  private String albumName;
  private String albumId;
  private Boolean isExplicit;
  private Long playbackSeconds;

}

package com.umg.umg_backend.api.mapper;

import com.umg.umg_backend.api.model.SpotifyMetadataDto;
import com.umg.umg_backend.domain.model.SpotifyMetadata;
import org.springframework.stereotype.Component;

@Component
public class SpotifyMetadataMapper {

  public SpotifyMetadataDto toDto(SpotifyMetadata spotifyMetadata) {
    return SpotifyMetadataDto.builder()
        .id(spotifyMetadata.getId())
        .isrc(spotifyMetadata.getIsrc())
        .name(spotifyMetadata.getName())
        .artistName(spotifyMetadata.getArtistName())
        .albumName(spotifyMetadata.getAlbumName())
        .albumId(spotifyMetadata.getAlbumId())
        .isExplicit(spotifyMetadata.getIsExplicit())
        .playbackSeconds(spotifyMetadata.getPlaybackSeconds())
        .build();
  }
}

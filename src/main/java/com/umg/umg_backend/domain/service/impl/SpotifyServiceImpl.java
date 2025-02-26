package com.umg.umg_backend.domain.service.impl;

import static com.umg.umg_backend.util.CoverUtil.COVER_PATH;
import static com.umg.umg_backend.util.CoverUtil.FILE_EXTENSION;
import static com.umg.umg_backend.util.CoverUtil.getNameAndExtension;

import com.umg.umg_backend.core.SpotifyProperties;
import com.umg.umg_backend.domain.model.Album;
import com.umg.umg_backend.domain.model.Image;
import com.umg.umg_backend.domain.model.Item;
import com.umg.umg_backend.domain.model.SpotifyAccessToken;
import com.umg.umg_backend.domain.model.SpotifyMetadata;
import com.umg.umg_backend.domain.model.SpotifyTrackResponse;
import com.umg.umg_backend.domain.service.SpotifyService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class SpotifyServiceImpl implements SpotifyService {

  private static final String GRANT_TYPE = "grant_type";
  private static final String CLIENT_ID = "client_id";
  private static final String CLIENT_SECRET = "client_secret";
  private static final String CLIENT_CREDENTIALS = "client_credentials";
  private static final String SEARCH_TYPE_PARAM = "type";
  private static final String TRACK = "track";
  private static final String Q_PARAM = "q";
  private static final String ISRC_PARAM = "isrc:";

  @Autowired
  private SpotifyProperties spotifyProperties;

  @Autowired
  @Qualifier("tokenRestClient")
  private RestClient tokenRestClient;

  @Autowired
  @Qualifier("apiRestClient")
  private RestClient apiRestClient;

  private SpotifyAccessToken accessToken = null;

  @Override
  public SpotifyMetadata getMetadata(String isrc) {
    ResponseEntity<SpotifyTrackResponse> response = apiRestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/search")
            .queryParam(Q_PARAM, ISRC_PARAM + isrc)
            .queryParam(SEARCH_TYPE_PARAM, TRACK)
            .build()
        )
        .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
        .retrieve()
        .toEntity(SpotifyTrackResponse.class);

    return extractData(response.getBody());
  }

  private SpotifyMetadata extractData(SpotifyTrackResponse response) {
    if(response == null) {
      return null;
    }

    Item item = response.getTracks().getItems().get(0);

    return SpotifyMetadata.builder()
        .name(item.getName())
        .artistName(item.getArtists().get(0).getName())
        .albumName(item.getAlbum().getName())
        .albumId(item.getAlbum().getId())
        .isExplicit(item.isExplicit())
        .playbackSeconds(Long.valueOf(item.getDuration_ms()))
        .build();
  }

  private SpotifyAccessToken getAccessToken() {
    if(accessToken == null) {
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add(GRANT_TYPE, CLIENT_CREDENTIALS);
      body.add(CLIENT_ID, spotifyProperties.getClientId());
      body.add(CLIENT_SECRET, spotifyProperties.getClientSecret());

      ResponseEntity<SpotifyAccessToken> response = tokenRestClient.post()
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
          .body(body)
          .retrieve()
          .toEntity(SpotifyAccessToken.class);

      this.accessToken = response.getBody();
    }

    return accessToken;
  }

  @Override
  public void downloadCover(SpotifyMetadata metadata) {
    Album album = getAlbum(metadata.getAlbumId());
    Image image = album.getImages().get(0);

    try {
      URL url = new URL(image.getUrl());
      BufferedImage coverBuffer = ImageIO.read(url);

      if(!Files.exists(COVER_PATH)) {
        Files.createDirectories(COVER_PATH);
      }

      File outputfile = new File(COVER_PATH.toString(), getNameAndExtension(metadata.getIsrc()));
      ImageIO.write(coverBuffer, FILE_EXTENSION, outputfile);
    } catch (Exception e) {
      //log expection
      System.out.println(e);
    }
  }

  private Album getAlbum(String albumId) {
    return apiRestClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/albums/" + albumId)
            .build()
        )
        .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeader())
        .retrieve()
        .toEntity(Album.class)
        .getBody();
  }

  private String getAuthorizationHeader() {
    return getAccessToken().getTokenType() + " " + getAccessToken().getAccessToken();
  }
}

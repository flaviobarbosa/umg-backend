package com.umg.umg_backend.domain.service.impl;

import com.umg.umg_backend.core.SpotifyProperties;
import com.umg.umg_backend.domain.model.Item;
import com.umg.umg_backend.domain.model.SpotifyAccessToken;
import com.umg.umg_backend.domain.model.SpotifyMetadata;
import com.umg.umg_backend.domain.model.SpotifyTrackResponse;
import com.umg.umg_backend.domain.service.SpotifyService;
import java.util.HashMap;
import java.util.Map;
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
  @Qualifier("searchRestClient")
  private RestClient searchRestClient;

  @Override
  public SpotifyMetadata getMetadata(String isrc) {
    SpotifyAccessToken accessToken = getAccessToken();
    String authorizationHeader = accessToken.getTokenType() + " " + accessToken.getAccessToken();

    ResponseEntity<SpotifyTrackResponse> response = searchRestClient.get()
        .uri(uriBuilder -> uriBuilder
            .queryParam(Q_PARAM, ISRC_PARAM + isrc)
            .queryParam(SEARCH_TYPE_PARAM, TRACK)
            .build()
        )
        .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
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
        .albumId(item.getId())
        .isExplicit(item.isExplicit())
        .playbackSeconds(Long.valueOf(item.getDuration_ms()))
        .build();
  }

  private SpotifyAccessToken getAccessToken() {

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add(GRANT_TYPE, CLIENT_CREDENTIALS);
    body.add(CLIENT_ID, spotifyProperties.getClientId());
    body.add(CLIENT_SECRET, spotifyProperties.getClientSecret());

    ResponseEntity<SpotifyAccessToken> response = tokenRestClient.post()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(body)
        .retrieve()
        .toEntity(SpotifyAccessToken.class);

    return response.getBody();
  }
}

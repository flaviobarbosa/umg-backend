package com.umg.umg_backend.domain.service.impl;

import com.umg.umg_backend.core.SpotifyProperties;
import com.umg.umg_backend.domain.model.SpotifyAccessToken;
import com.umg.umg_backend.domain.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
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



  @Autowired
  private SpotifyProperties spotifyProperties;

  @Autowired
  private RestClient restClient;

  @Override
  public SpotifyAccessToken getAccessToken() {

    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add(GRANT_TYPE, CLIENT_CREDENTIALS);
    body.add(CLIENT_ID, spotifyProperties.getClientId());
    body.add(CLIENT_SECRET, spotifyProperties.getClientSecret());

    ResponseEntity<SpotifyAccessToken> response = restClient.post()
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(body)
        .retrieve()
        .toEntity(SpotifyAccessToken.class);

    return response.getBody();
  }
}

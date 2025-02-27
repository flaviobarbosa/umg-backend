package com.umg.umg_backend.domain.service.impl;

import static com.umg.umg_backend.util.CoverUtil.COVER_PATH;
import static com.umg.umg_backend.util.CoverUtil.getNameAndExtension;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import com.umg.umg_backend.domain.repository.SpotifyMetadataRepository;
import com.umg.umg_backend.domain.service.SpotifyService;
import com.umg.umg_backend.domain.service.TrackService;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TrackServiceImpl implements TrackService {

  @Autowired
  private SpotifyService spotifyService;

  @Autowired
  private SpotifyMetadataRepository repository;

  @Override
  @Transactional
  public SpotifyMetadata createTrack(String isrc) {
    if(StringUtils.isEmpty(isrc)) {
      throw new ResponseStatusException(BAD_REQUEST, "ISRC is required");
    }

    Optional<SpotifyMetadata> byIsrc = repository.findByIsrc(isrc);

    if(byIsrc.isPresent()) {
      throw new ResponseStatusException(CONFLICT, "ISRC already saved");
    }

    SpotifyMetadata metadata = spotifyService.getMetadata(isrc);
    metadata.setIsrc(isrc);

    spotifyService.downloadCover(metadata);

    return repository.save(metadata);
  }

  @Override
  public SpotifyMetadata getTrackMetadata(String isrc) {
    return repository.findByIsrc(isrc).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }

  @Override
  public File getCover(String isrc) {
      File coverFile = new File(COVER_PATH.toString(), getNameAndExtension(isrc));

      if(!Files.exists(coverFile.toPath())) {
        throw new ResponseStatusException(NOT_FOUND);
      }

      return coverFile;
  }

  @Override
  public List<SpotifyMetadata> getAllTracksMetadata() {
    return repository.findAll();
  }
}

package com.umg.umg_backend.api;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import com.umg.umg_backend.domain.service.TrackService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/codechallenge")
public class TrackController {

  @Autowired
  private TrackService trackService;

  @PostMapping("/createTrack")
  @ResponseStatus(HttpStatus.CREATED)
  public SpotifyMetadata createTrack(@RequestParam String isrc) {
    return trackService.createTrack(isrc);
  }

  @GetMapping("/getTrackMetadata/{isrc}")
  public SpotifyMetadata getTrackMetadata(@PathVariable String isrc)  {
    return trackService.getTrackMetadata(isrc);
  }

  @GetMapping("/cover/{isrc}")
  @Transactional
  public ResponseEntity<byte[]> getCover(@PathVariable String isrc) throws IOException {
    File coverFile = trackService.getCover(isrc);

    return ResponseEntity.ok()
        .header("Content-Disposition", "attachment; filename=" + coverFile.getName())
        .contentType(MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE))
        .body(Files.readAllBytes(coverFile.toPath()));

  }

}

package com.umg.umg_backend.domain.repository;

import com.umg.umg_backend.domain.model.SpotifyMetadata;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotifyMetadataRepository extends JpaRepository<SpotifyMetadata, Integer> {
}

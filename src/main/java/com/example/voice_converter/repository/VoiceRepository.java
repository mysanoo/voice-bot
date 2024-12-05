package com.example.voice_converter.repository;

import com.example.voice_converter.domain.Voice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoiceRepository extends JpaRepository<Voice, UUID> {
}

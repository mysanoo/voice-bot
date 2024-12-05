package com.example.voice_converter.domain;

import com.example.voice_converter.domain.enums.UserSteps;
import com.example.voice_converter.domain.enums.Voices;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Long telegramId;
    private UserSteps step;
    @Enumerated(EnumType.STRING)
    private Voices voice;
}

package com.example.voice_converter.domain.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum Voices {
//    ALLOY("alloy", "Male Voice"),
    FABLE("fable", "male voice"),
    ECHO("echo", "another different male voice"),
    ONYX("onyx", "man thick voice"),
    NOVA("nova", "girl voice"),
    SHIMMER("shimmer", "woman voice");

    final String name;
    final String description;

    Voices(String name, String description){
        this.name = name;
        this.description = description;
    }
}

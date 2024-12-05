package com.example.voice_converter.dto.json;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    private String role;
    private String content;
    private Object refusal;
}

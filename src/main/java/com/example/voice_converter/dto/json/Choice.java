package com.example.voice_converter.dto.json;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Choice {

    private Integer index;
    private Message message;
    private Object logprobs;
    private String finishReason;
}

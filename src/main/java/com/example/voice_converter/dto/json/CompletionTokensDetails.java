package com.example.voice_converter.dto.json;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompletionTokensDetails {

    private Integer reasoning_tokens;
    private Integer audio_tokens;
    private Integer accepted_prediction_tokens;
    private Integer rejected_prediction_tokens;
}

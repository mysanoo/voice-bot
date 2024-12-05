package com.example.voice_converter.dto.json;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Usage {
    private Integer prompt_tokens;
    private Integer completion_tokens;
    private Integer total_tokens;
    private PromptTokensDetails prompt_tokens_details;
    private CompletionTokensDetails completion_tokens_details;
}

package com.example.app.request;

import com.example.app.model.types.Speaker;
import lombok.Getter;

@Getter
public class EvaluatorRequest {
    private Long meetingId;
    private Speaker speakerType;
    private String speakerUsername;
    private String evaluatorUsername;
}

package com.example.app.dto;

import com.example.app.model.types.Speaker;
import lombok.Data;
import java.io.Serializable;

@Data
public class SpeakerDto implements Serializable {
    private String speakerUsername;
    private String speakerName;
    private Speaker speakerType;
    private String evaluatorUsername = "";
    private String evaluatorName = "";

    public SpeakerDto(String speakerUsername, String speakerName, Speaker speakerType) {
        this.speakerUsername = speakerUsername;
        this.speakerName = speakerName;
        this.speakerType = speakerType;
    }
}

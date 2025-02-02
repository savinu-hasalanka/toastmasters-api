package com.example.app.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class SpeakerDto implements Serializable {
    private String speakerUsername;
    private String speakerName;
    private String evaluatorUsername = "";
    private String evaluatorName = "";
}

package com.example.app.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MeetingSpeakerDto implements Serializable {
    private Long meetingId;
    private List<SpeakerDto> speakers;
}

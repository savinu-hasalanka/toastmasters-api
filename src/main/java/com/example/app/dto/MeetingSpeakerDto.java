package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class MeetingSpeakerDto implements Serializable {
    private Long meetingId;
    private List<SpeakerDto> speakers;
}

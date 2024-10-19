package com.example.app.model.composite_keys;

import com.example.app.model.types.Speaker;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class MeetingSpeakerId {
    private Long meetingId;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private Speaker speakerType;

}

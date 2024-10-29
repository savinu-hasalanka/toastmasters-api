package com.example.app.model.composite_keys;

import com.example.app.model.types.Speaker;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MeetingSpeakerId {
    private Long meetingId;
    private String username;

    @Enumerated(EnumType.STRING)
    private Speaker speakerType;
}

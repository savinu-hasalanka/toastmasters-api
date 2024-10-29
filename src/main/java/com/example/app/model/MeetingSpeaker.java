package com.example.app.model;

import com.example.app.model.composite_keys.MeetingSpeakerId;
import com.example.app.model.types.Speaker;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "meeting_speaker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeetingSpeaker {
    @EmbeddedId
    private MeetingSpeakerId id;

    @ManyToOne
    @MapsId("meetingId") // Maps the meetingId part of the composite key to Meeting
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @MapsId("username") // Maps the username part of the composite key to AppUser
    @JoinColumn(name = "speaker_id")
    private AppUser speaker;

    @Enumerated(EnumType.STRING)
    @Column(insertable = false, updatable = false)
    private Speaker speakerType;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private AppUser evaluator;

    public MeetingSpeaker(MeetingSpeakerId meetingSpeakerId, Meeting meeting, AppUser speaker) {
        this.id = meetingSpeakerId;
        this.meeting = meeting;
        this.speakerType = meetingSpeakerId.getSpeakerType();
        this.speaker = speaker;
    }

    public MeetingSpeaker(MeetingSpeakerId meetingSpeakerId, Meeting meeting, AppUser speaker, AppUser evaluator) {
        this.id = meetingSpeakerId;
        this.meeting = meeting;
        this.speakerType = meetingSpeakerId.getSpeakerType();
        this.speaker = speaker;
        this.evaluator = evaluator;
    }
}

package com.example.app.model;

import com.example.app.model.composite_keys.MeetingSpeakerId;
import jakarta.persistence.*;

@Entity
@Table(name = "meeting_speaker")
public class MeetingSpeaker {
    @EmbeddedId
    private MeetingSpeakerId id;

    @ManyToOne
    @MapsId("meetingId") // Maps the meetingId part of the composite key to Meeting
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @MapsId("userId") // Maps the userId part of the composite key to AppUser
    @JoinColumn(name = "user_id")
    private AppUser speaker;

    @ManyToOne
    @JoinColumn(name = "evaluator_id")
    private AppUser evaluator;

}

package com.example.app.model;

import com.example.app.model.composite_keys.MeetingRolePlayerId;
import com.example.app.model.types.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "meeting_roleplayer")
@Getter
@Setter
public class MeetingRolePlayer {
    @EmbeddedId
    private MeetingRolePlayerId id;

    @ManyToOne
    @MapsId("username") // Maps the userId part of the composite key to AppUser
    @JoinColumn(name = "role_player_username")
    private AppUser rolePlayer;

    @ManyToOne
    @MapsId("meetingId") // Maps the meetingId part of the composite key to Meeting
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @Enumerated(EnumType.STRING)
    private Role role;
}

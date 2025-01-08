package com.example.app.model;

import com.example.app.model.composite_keys.UserClubCompositePK;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follower")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Follower {

    @EmbeddedId
    private UserClubCompositePK userClubCompositePK;

    @ManyToOne
    @MapsId("clubId")
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username")
    private AppUser appUser;


}

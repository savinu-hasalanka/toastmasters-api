package com.example.app.model;

import com.example.app.model.composite_keys.UserClubCompositePK;
import com.example.app.model.types.ExcoRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exco")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exco {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ExcoRole role;

    public Exco(Club club, AppUser appUser, ExcoRole role) {
        this.club = club;
        this.appUser = appUser;
        this.role = role;
    }
}

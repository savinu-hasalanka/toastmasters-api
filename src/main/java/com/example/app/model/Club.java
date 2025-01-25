package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "club")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private int clubId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "club")
    @JsonIgnore
    private List<Meeting> meetings;

    public Club(int clubId, String name, String email, String password) {
        this.clubId = clubId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Club(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

package com.example.app.dto;

import com.example.app.model.Meeting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubDto implements Serializable {
    private int clubId;
    private String name;
    private List<Meeting> meetings;
}

package com.example.app.request;

import com.example.app.model.Club;
import com.example.app.model.types.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AddMeetingRequest {
    private String title;
    private String date;
    private String startTime;
    private String endTime;
    private String location;
    private Status status;
    @Setter
    private Club club;
    private Integer visitingClubId;

}

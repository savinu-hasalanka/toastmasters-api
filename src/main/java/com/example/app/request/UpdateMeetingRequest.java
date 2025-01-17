package com.example.app.request;

import com.example.app.model.types.Status;
import lombok.Getter;

@Getter
public class UpdateMeetingRequest {
    private String title;
    private String date;
    private String startTime;
    private String endTime;
    private String location;
    private Status status;
    private Integer visitingClubId;
}

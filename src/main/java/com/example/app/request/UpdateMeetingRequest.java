package com.example.app.request;

import lombok.Getter;

@Getter
public class UpdateMeetingRequest {
    private String title;
    private String date;
    private String startTime;
    private String endTime;
}

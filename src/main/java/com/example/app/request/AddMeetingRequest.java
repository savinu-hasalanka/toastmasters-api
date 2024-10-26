package com.example.app.request;

import lombok.Getter;

@Getter
public class AddMeetingRequest {
    private String title;
    private String date;
    private String startTime;
    private String endTime;
    private String host;
}

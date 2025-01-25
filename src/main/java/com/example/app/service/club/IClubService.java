package com.example.app.service.club;

import com.example.app.dto.ClubDto;
import com.example.app.request.ClubLoginRequest;
import com.example.app.request.ClubRegisterRequest;

public interface IClubService {

    ClubDto register(ClubRegisterRequest club);

    String login(ClubLoginRequest club);
}

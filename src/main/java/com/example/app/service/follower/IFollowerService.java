package com.example.app.service.follower;

import com.example.app.dto.UserDto;
import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;

import java.util.List;

public interface IFollowerService {

    List<UserDto> getFollowers(Integer clubId) throws ResourceNotFoundException;

    void follow(Integer clubId, String username) throws ResourceNotFoundException, AlreadyExistsException;

    void unfollow(Integer clubId, String username) throws ResourceNotFoundException;

}

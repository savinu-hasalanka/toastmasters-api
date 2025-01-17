package com.example.app.service.roleplayer;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.RolePlayer;
import com.example.app.model.RolePlayerRequest;
import com.example.app.request.RolePlayerRequestRequest;

import java.util.List;

public interface IRolePlayerService {

    List<RolePlayer> getRolePlayers(Integer meetingId) throws ResourceNotFoundException;

    void addRolePlayer(RolePlayerRequestRequest rolePlayerRequestRequest) throws ResourceNotFoundException, AlreadyExistsException;

    void removeRolePlayer(RolePlayerRequestRequest rolePlayerRequestRequest) throws ResourceNotFoundException;

    List<RolePlayerRequest> getRolePlayerRequests(Integer meetingId) throws ResourceNotFoundException;

    void makeRolePlayerRequest(RolePlayerRequestRequest rolePlayerRequestRequest) throws ResourceNotFoundException, AlreadyExistsException;

    void removeRolePlayerRequest(RolePlayerRequestRequest rolePlayerRequestRequest) throws ResourceNotFoundException;

    void acceptRolePlayerRequest(RolePlayerRequestRequest rolePlayerRequestRequest) throws ResourceNotFoundException, AlreadyExistsException;

}

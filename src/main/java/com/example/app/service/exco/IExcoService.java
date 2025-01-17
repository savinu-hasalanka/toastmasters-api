package com.example.app.service.exco;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.model.Exco;
import com.example.app.request.AddExcoRequest;
import com.example.app.request.DeleteExcoRequest;

import java.util.List;

public interface IExcoService {

    List<Exco> getAllExcoMembers(Integer clubId) throws ResourceNotFoundException;

    void addExcoMember(AddExcoRequest addExcoRequest) throws ResourceNotFoundException, AlreadyExistsException;

    void updateExcoMember(AppUser appUser, Integer clubId);

    void deleteExcoMember(DeleteExcoRequest deleteExcoRequest) throws ResourceNotFoundException;

}

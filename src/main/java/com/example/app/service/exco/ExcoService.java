package com.example.app.service.exco;

import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.model.Club;
import com.example.app.model.Exco;
import com.example.app.model.composite_keys.UserClubCompositePK;
import com.example.app.model.types.ExcoRole;
import com.example.app.repo.AppUserRepository;
import com.example.app.repo.ClubRepository;
import com.example.app.repo.ExcoRepository;
import com.example.app.request.AddExcoRequest;
import com.example.app.request.DeleteExcoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcoService implements IExcoService {

    private final ExcoRepository excoRepository;
    private final ClubRepository clubRepository;
    private final AppUserRepository appUserRepository;


    @Override
    public List<Exco> getAllExcoMembers(Integer clubId) throws ResourceNotFoundException {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club not found"));

        return excoRepository.findAllByClub(club);
    }

    @Override
    @Transactional
    public void addExcoMember(AddExcoRequest request) throws ResourceNotFoundException, AlreadyExistsException {
        Club club = clubRepository.findById(request.getClubId())
                .orElseThrow(() -> new ResourceNotFoundException("Club not found"));

        AppUser user = appUserRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ExcoRole excoRole = ExcoRole.valueOf(request.getRole());

        UserClubCompositePK primaryKey = new UserClubCompositePK(request.getUsername(), request.getClubId());

        boolean roleExist = excoRepository.existsByRoleAndClub(excoRole, club);
        boolean userExist = excoRepository.existsByUserClubCompositePK(primaryKey);

        if (roleExist) {
            throw new AlreadyExistsException(request.getRole() + " already exists");
        }

        if (userExist) {
            throw new AlreadyExistsException("User already holds an exco role");
        }

        Exco exco = new Exco(primaryKey, club, user, excoRole);
        excoRepository.save(exco);
    }

    @Override
    @Transactional
    public void updateExcoMember(AppUser appUser, Integer clubId) {

    }

    @Override
    @Transactional
    public void deleteExcoMember(DeleteExcoRequest request) throws ResourceNotFoundException {
        UserClubCompositePK primaryKey = new UserClubCompositePK(request.getUsername(), request.getClubId());

        Exco exco = excoRepository.findById(primaryKey)
                .orElseThrow(() -> new ResourceNotFoundException("No exco record found for clubID: " + request.getClubId() + " and username: " + request.getUsername()));

        excoRepository.delete(exco);
    }
}

package com.example.app.service.follower;

import com.example.app.dto.UserDto;
import com.example.app.exception.AlreadyExistsException;
import com.example.app.exception.ResourceNotFoundException;
import com.example.app.model.AppUser;
import com.example.app.model.Club;
import com.example.app.model.Follower;
import com.example.app.model.composite_keys.UserClubCompositePK;
import com.example.app.repo.AppUserRepository;
import com.example.app.repo.ClubRepository;
import com.example.app.repo.FollowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowerService implements IFollowerService {

    private final FollowerRepository followerRepository;
    private final ClubRepository clubRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<UserDto> getFollowers(Integer clubId) throws ResourceNotFoundException {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club not found"));

        List<Follower> followerRows = followerRepository.findAllByClub(club);
        List<UserDto> followers = new ArrayList<>();

        for (Follower follower : followerRows) {
            UserDto userDto = new UserDto(follower.getAppUser().getUsername(), follower.getAppUser().getEmail());
            followers.add(userDto);
        }

        return followers;
    }

    @Override
    public void follow(Integer clubId, String username) throws ResourceNotFoundException, AlreadyExistsException {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club not found"));

        AppUser appUser = appUserRepository.findById(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserClubCompositePK primaryKey = new UserClubCompositePK(username, clubId);

        if (followerRepository.existsById(primaryKey))
            throw new AlreadyExistsException("Already follows the clubID: " + clubId);

        Follower follower = new Follower(primaryKey, club, appUser);

        followerRepository.save(follower);
    }

    @Override
    public void unfollow(Integer clubId, String username) throws ResourceNotFoundException {
        UserClubCompositePK primaryKey = new UserClubCompositePK(username, clubId);

        if (!(followerRepository.existsById(primaryKey))) {
            throw new ResourceNotFoundException(username + " does not follow the clubID: " + clubId);
        }

        followerRepository.deleteById(primaryKey);
    }
}

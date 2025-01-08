package com.example.app;

import com.example.app.model.AppUser;
import com.example.app.model.Meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
//    private final UserRepository userRepository;
//    private final MeetingRepository meetingRepository;

    @Override
    public void run(String... args) throws Exception {
//        AppUser appUser01 = new AppUser("savi", "Savinu Hasalanka", "123");
//        AppUser appUser02 = new AppUser("shems", "Shemaya Sandanayaka", "123");
//
//        userRepository.save(appUser01);
//        userRepository.save(appUser02);
//
//        meetingRepository.save(
//                new Meeting(
//                        "iit toastmaster",
//                        LocalDate.parse("2024-10-01"),
//                        LocalTime.parse("17:00"),
//                        LocalTime.parse("17:00"),
//                        appUser01
//                )
//        );
//
//        meetingRepository.save(
//                new Meeting(
//                        "cipm toastmaster",
//                        LocalDate.parse("2024-10-01"),
//                        LocalTime.parse("17:00"),
//                        LocalTime.parse("17:00"),
//                        appUser02
//                )
//        );
//
////        meetingRepository.save(
////                new Meeting(
////                        "cipm toastmaster",
////                        LocalDate.now(),
////                        LocalTime.now(),
////                        LocalTime.now(),
////                        appUser02
////                )
////        );
    }
}

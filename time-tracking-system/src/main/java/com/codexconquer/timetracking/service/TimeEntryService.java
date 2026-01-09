package com.codexconquer.timetracking.service;

import com.codexconquer.timetracking.dto.TimeSummaryResponse;
import com.codexconquer.timetracking.entity.TimeEntry;
import com.codexconquer.timetracking.entity.User;
import com.codexconquer.timetracking.exception.AuthException;
import com.codexconquer.timetracking.repository.TimeEntryRepository;
import com.codexconquer.timetracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final UserRepository userRepository;

    // 🔹 Punch In
    public TimeEntry punchIn(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        // Prevent double punch-in
        timeEntryRepository.findByUserAndPunchOutTimeIsNull(user)
                .ifPresent(entry -> {
                    throw new AuthException("User already punched in");
                });

        TimeEntry entry = TimeEntry.builder()
                .user(user)
                .punchInTime(LocalDateTime.now())
                .workDate(LocalDateTime.now())
                .build();

        return timeEntryRepository.save(entry);
    }

    // 🔹 Punch Out
    public TimeEntry punchOut(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        TimeEntry entry = timeEntryRepository
                .findByUserAndPunchOutTimeIsNull(user)
                .orElseThrow(() -> new AuthException("User has not punched in"));

        entry.setPunchOutTime(LocalDateTime.now());
        return timeEntryRepository.save(entry);
    }

    // 🔹 Total Worked Time
    public TimeSummaryResponse getTotalWorkedTime(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        List<TimeEntry> entries =
                timeEntryRepository.findByUserAndPunchOutTimeIsNotNull(user);

        long totalMinutes = entries.stream()
                .mapToLong(TimeEntry::getWorkedMinutes)
                .sum();

        long hours = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        return new TimeSummaryResponse(totalMinutes, hours, minutes);
    }

}

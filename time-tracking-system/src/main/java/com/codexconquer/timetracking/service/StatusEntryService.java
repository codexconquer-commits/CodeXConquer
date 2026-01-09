package com.codexconquer.timetracking.service;

import com.codexconquer.timetracking.entity.*;
import com.codexconquer.timetracking.exception.AuthException;
import com.codexconquer.timetracking.repository.StatusEntryRepository;
import com.codexconquer.timetracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusEntryService {

    private final StatusEntryRepository statusEntryRepository;
    private final UserRepository userRepository;

    public StatusEntry startStatus(Long userId, WorkStatus status) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        // End any active status
        statusEntryRepository.findByUserAndEndTimeIsNull(user)
                .ifPresent(active -> {
                    active.setEndTime(LocalDateTime.now());
                    statusEntryRepository.save(active);
                });

        StatusEntry entry = StatusEntry.builder()
                .user(user)
                .status(status)
                .startTime(LocalDateTime.now())
                .build();

        return statusEntryRepository.save(entry);
    }

    public List<StatusEntry> getUserStatuses(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        return statusEntryRepository.findByUser(user);
    }
}

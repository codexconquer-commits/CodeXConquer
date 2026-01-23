package com.codexconquer.timetracking.service;

import com.codexconquer.timetracking.dto.StatusSummaryResponse;
import com.codexconquer.timetracking.entity.StatusEntry;
import com.codexconquer.timetracking.entity.User;
import com.codexconquer.timetracking.exception.AuthException;
import com.codexconquer.timetracking.repository.StatusEntryRepository;
import com.codexconquer.timetracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.codexconquer.timetracking.entity.WorkStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
@Slf4j
@Service
@RequiredArgsConstructor
public class StatusEntryService {

    private final StatusEntryRepository statusEntryRepository;
    private final UserRepository userRepository;

    // START STATUS (ONLINE, BREAK, IDLE, etc.)
    public StatusEntry startStatus(Long userId, WorkStatus status) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

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


    // STOP CURRENT STATUS
    public StatusEntry stopStatus(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        StatusEntry entry = statusEntryRepository
                .findByUserAndEndTimeIsNull(user)
                .orElseThrow(() -> new AuthException("No active status found"));

        entry.setEndTime(LocalDateTime.now());
        return statusEntryRepository.save(entry);
    }
    // STATUS-WISE SUMMARY
    public List<StatusSummaryResponse> getStatusSummary(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        List<StatusEntry> entries = statusEntryRepository.findAll()
                .stream()
                .filter(e -> e.getUser().getId().equals(userId))
                .filter(e -> e.getEndTime() != null)
                .toList();

        Map<WorkStatus, Long> statusMinutes = new EnumMap<>(WorkStatus.class);

        for (StatusEntry entry : entries) {
            long minutes = Duration.between(entry.getStartTime(), entry.getEndTime()).toMinutes();
            statusMinutes.put(
                    entry.getStatus(),
                    statusMinutes.getOrDefault(entry.getStatus(), 0L) + minutes
            );
        }

        return statusMinutes.entrySet().stream()
                .map(e -> {
                    long hrs = e.getValue() / 60;
                    long mins = e.getValue() % 60;
                    return new StatusSummaryResponse(
                            e.getKey().name(),   // convert enum to String
                            e.getValue(),
                            hrs,
                            mins
                    );
                })
                .toList();
    }



}

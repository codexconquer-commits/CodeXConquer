package com.codexconquer.timetracking.service;

import com.codexconquer.timetracking.dto.StatusTimeResponse;
import com.codexconquer.timetracking.entity.StatusEntry;
import com.codexconquer.timetracking.entity.User;
import com.codexconquer.timetracking.entity.WorkStatus;
import com.codexconquer.timetracking.exception.AuthException;
import com.codexconquer.timetracking.repository.StatusEntryRepository;
import com.codexconquer.timetracking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatusSummaryService {

    private final StatusEntryRepository statusEntryRepository;
    private final UserRepository userRepository;

    public Map<WorkStatus, StatusTimeResponse> getStatusSummary(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        List<StatusEntry> entries = statusEntryRepository.findByUser(user);

        Map<WorkStatus, Long> minutesMap = new EnumMap<>(WorkStatus.class);

        for (StatusEntry entry : entries) {
            if (entry.getEndTime() != null) {
                minutesMap.merge(
                        entry.getStatus(),
                        entry.getDurationMinutes(),
                        Long::sum
                );
            }
        }

        Map<WorkStatus, StatusTimeResponse> response = new EnumMap<>(WorkStatus.class);

        for (WorkStatus status : WorkStatus.values()) {
            long minutes = minutesMap.getOrDefault(status, 0L);
            response.put(
                    status,
                    new StatusTimeResponse(
                            minutes,
                            minutes / 60,
                            minutes % 60
                    )
            );
        }

        return response;
    }
}

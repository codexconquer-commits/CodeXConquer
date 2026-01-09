package com.codexconquer.timetracking.controller;

import com.codexconquer.timetracking.entity.StatusEntry;
import com.codexconquer.timetracking.entity.WorkStatus;
import com.codexconquer.timetracking.service.StatusEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
public class StatusEntryController {

    private final StatusEntryService statusEntryService;

    @PostMapping("/{userId}/{status}")
    public StatusEntry changeStatus(
            @PathVariable Long userId,
            @PathVariable WorkStatus status
    ) {
        return statusEntryService.startStatus(userId, status);
    }

    @GetMapping("/{userId}")
    public List<StatusEntry> getStatuses(@PathVariable Long userId) {
        return statusEntryService.getUserStatuses(userId);
    }
}

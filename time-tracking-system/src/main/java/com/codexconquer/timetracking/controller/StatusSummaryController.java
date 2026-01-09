package com.codexconquer.timetracking.controller;

import com.codexconquer.timetracking.dto.StatusTimeResponse;
import com.codexconquer.timetracking.entity.WorkStatus;
import com.codexconquer.timetracking.service.StatusSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/status-summary")
@RequiredArgsConstructor
public class StatusSummaryController {

    private final StatusSummaryService statusSummaryService;

    @GetMapping("/{userId}")
    public Map<WorkStatus, StatusTimeResponse> getSummary(
            @PathVariable Long userId
    ) {
        return statusSummaryService.getStatusSummary(userId);
    }
}

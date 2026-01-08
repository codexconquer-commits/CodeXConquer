package com.codexconquer.timetracking.controller;

import com.codexconquer.timetracking.dto.TimeSummaryResponse;
import com.codexconquer.timetracking.entity.TimeEntry;
import com.codexconquer.timetracking.service.TimeEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/time")
@RequiredArgsConstructor
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    @PostMapping("/punch-in/{userId}")
    public TimeEntry punchIn(@PathVariable Long userId) {
        return timeEntryService.punchIn(userId);
    }

    @PostMapping("/punch-out/{userId}")
    public TimeEntry punchOut(@PathVariable Long userId) {
        return timeEntryService.punchOut(userId);
    }

    @GetMapping("/summary/{userId}")
    public TimeSummaryResponse getSummary(@PathVariable Long userId) {
        return timeEntryService.getTotalWorkedTime(userId);
    }
}

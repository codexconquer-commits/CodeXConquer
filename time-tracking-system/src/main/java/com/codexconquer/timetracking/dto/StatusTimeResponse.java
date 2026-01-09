package com.codexconquer.timetracking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatusTimeResponse {
    private long totalMinutes;
    private long hours;
    private long remainingMinutes;
}

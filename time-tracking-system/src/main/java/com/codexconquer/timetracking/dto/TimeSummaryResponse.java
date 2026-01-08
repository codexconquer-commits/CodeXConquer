package com.codexconquer.timetracking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TimeSummaryResponse {

    private Long totalMinutes;
    private Long hours;
    private Long minutes;
}

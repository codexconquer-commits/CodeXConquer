package com.codexconquer.timetracking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many entries belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime punchInTime;

    private LocalDateTime punchOutTime;

    private LocalDateTime workDate;

    // Not stored in DB, calculated dynamically
    @Transient
    public long getWorkedMinutes() {
        if (punchInTime != null && punchOutTime != null) {
            return Duration.between(punchInTime, punchOutTime).toMinutes();
        }
        return 0;
    }
}

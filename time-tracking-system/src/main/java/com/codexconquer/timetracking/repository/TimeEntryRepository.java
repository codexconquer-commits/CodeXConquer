package com.codexconquer.timetracking.repository;

import com.codexconquer.timetracking.entity.TimeEntry;
import com.codexconquer.timetracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

    // Active session (punched in but not punched out)
    Optional<TimeEntry> findByUserAndPunchOutTimeIsNull(User user);

    // Completed sessions
    List<TimeEntry> findByUserAndPunchOutTimeIsNotNull(User user);
}

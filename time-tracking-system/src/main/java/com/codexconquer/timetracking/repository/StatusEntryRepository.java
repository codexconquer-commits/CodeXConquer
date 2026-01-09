package com.codexconquer.timetracking.repository;

import com.codexconquer.timetracking.entity.StatusEntry;
import com.codexconquer.timetracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusEntryRepository extends JpaRepository<StatusEntry, Long> {

    Optional<StatusEntry> findByUserAndEndTimeIsNull(User user);

    List<StatusEntry> findByUser(User user);
}

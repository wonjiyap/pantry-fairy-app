package com.wonjiyap.pantryfairyapp.repository;

import com.wonjiyap.pantryfairyapp.domain.RemindMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindMessageRepository extends JpaRepository<RemindMessage,Long> {
}

package com.wonjiyap.pantryfairyapp.repository;

import com.wonjiyap.pantryfairyapp.domain.RemindInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemindInfoRepository extends JpaRepository<RemindInfo,Long> {
}

package com.wonjiyap.pantryfairyapp.service;

import com.wonjiyap.pantryfairyapp.domain.RemindInfo;
import com.wonjiyap.pantryfairyapp.domain.RemindMessage;
import com.wonjiyap.pantryfairyapp.enums.RemindTerm;
import com.wonjiyap.pantryfairyapp.repository.RemindInfoRepository;
import com.wonjiyap.pantryfairyapp.repository.RemindMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RemindMessageService {

    private final RemindInfoRepository remindInfoRepository;
    private final RemindMessageRepository remindMessageRepository;

    /**
     * 새 리마인드 메시지 생성
     */
    @Transactional
    public Long saveRemindMessage() {
        Boolean isValid = validateRemindTime();
        if (!isValid) { return null; }
        RemindMessage remindMessage = new RemindMessage();
        remindMessageRepository.save(remindMessage);
        return remindMessage.getId();
    }

    /**
     * 리마인드 메시지 생성 타이밍 확인
     */
    public Boolean validateRemindTime() {
        List<RemindInfo> findRemindInfos = remindInfoRepository.findAll();
        if (findRemindInfos.isEmpty()) { return false; }
        RemindInfo remindInfo = findRemindInfos.get(0);
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        if (!compareDateString(remindInfo.getRemindTime(), now.getHour())) { return false; }
        if (remindInfo.getRemindTerm() == RemindTerm.MONTH) {
            if (!compareDateString(remindInfo.getRemindDate(), now.getDayOfMonth())) { return false; }
        } else if (remindInfo.getRemindTerm() == RemindTerm.WEEK) {
            if (!compareDateString(remindInfo.getRemindDay(), now.getDayOfWeek().getValue())) { return false; }
        } else {
            return false;
        }
        return true;
    }

    private Boolean compareDateString(String remindInfoValue, int dateValue) {
        return Objects.equals(remindInfoValue, String.valueOf(dateValue));
    }

    /**
     * 신규 리마인드 메시지 조회
     */

    /**
     * 리마인드 메시지 확인 처리
     */
}

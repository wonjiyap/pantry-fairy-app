package com.wonjiyap.pantryfairyapp.service;

import com.wonjiyap.pantryfairyapp.domain.RemindInfo;
import com.wonjiyap.pantryfairyapp.dto.remind_info.RemindInfoRequest;
import com.wonjiyap.pantryfairyapp.repository.RemindInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RemindInfoService {

    private final RemindInfoRepository remindInfoRepository;

    /**
     * 새 리마인드 정보 생성하거나 기존 리마인드 정보가 있으면 업데이트
     */
    @Transactional
    public Long saveRemindInfo(RemindInfoRequest request) {
        RemindInfo remindInfo;

        List<RemindInfo> findRemindInfos = remindInfoRepository.findAll();

        if (!findRemindInfos.isEmpty()) {
            remindInfo = findRemindInfos.get(0);
            remindInfo.update(request.getRemindTerm(),
                    request.getRemindDay(),
                    request.getRemindDate(),
                    request.getRemindTime());
        } else {
            remindInfo = new RemindInfo(request.getRemindTerm(),
                    request.getRemindDay(),
                    request.getRemindDate(),
                    request.getRemindTime());
            remindInfoRepository.save(remindInfo);
        }
        return remindInfo.getId();
    }

    /**
     * 리마인드 정보 조회
     */
    public RemindInfo findRemindInfo() {
        List<RemindInfo> findRemindInfos = remindInfoRepository.findAll();
        return findRemindInfos.isEmpty() ? null : findRemindInfos.get(0);
    }
}

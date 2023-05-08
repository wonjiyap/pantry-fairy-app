package com.wonjiyap.pantryfairyapp.scheduler;

import com.wonjiyap.pantryfairyapp.service.RemindMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemindScheduler {

    private final RemindMessageService remindMessageService;

    @Scheduled(cron = "0 0 0/1 * * ?", zone = "Asia/Seoul")
    public void sendReminder() {
        remindMessageService.saveRemindMessage();
    }
}

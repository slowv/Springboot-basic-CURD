package com.slowv.youtuberef.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessageSendingOperations messagingTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendNotification() {
//        log.info("sending notification");
//        messagingTemplate.convertAndSend("/notification", "Push notification!!!");
    }
}

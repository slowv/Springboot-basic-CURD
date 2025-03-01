package com.slowv.youtuberef.schedule;

import com.slowv.youtuberef.event.producer.VideoProducer;
import com.slowv.youtuberef.repository.VideoRepository;
import com.slowv.youtuberef.service.mapper.VideoMapper;
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
    private final VideoProducer videoProducer;
    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Scheduled(fixedRate = 5000)
    public void sendNotification() {
//        final var request = new VideoSearchRequest();
//        final var videos = videoRepository.findAll(request.specification(), request.getPaging().pageable())
//                .map(videoMapper::toDto);
        videoProducer.syncVideos("SlowV Hello AE nhé!!!!");
    }
}

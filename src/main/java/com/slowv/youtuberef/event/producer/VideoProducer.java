package com.slowv.youtuberef.event.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoProducer {

    private final StreamBridge streamBridge;

    public void syncVideos(final String content) {
        streamBridge.send("syncVideos-out-0", content);
    }
}

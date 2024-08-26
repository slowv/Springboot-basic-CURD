package com.slowv.youtuberef.web.rest.impl;

import com.slowv.youtuberef.integration.youtube.YoutubeChannel;
import com.slowv.youtuberef.integration.youtube.model.YoutubeItem;
import com.slowv.youtuberef.service.dto.response.Response;
import com.slowv.youtuberef.web.rest.YoutubeController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YoutubeControllerImpl implements YoutubeController {

    private final YoutubeChannel youtubeChannel;

    @Override
    public Response<List<YoutubeItem>> search(final String search, final int maxSize) {
        return Response.ok(youtubeChannel.search(search, maxSize));
    }
}

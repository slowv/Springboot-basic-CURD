package com.slowv.youtuberef.web.rest;

import com.slowv.youtuberef.integration.youtube.model.YoutubeItem;
import com.slowv.youtuberef.service.dto.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(value = "/youtube")
public interface YoutubeController {

    @GetMapping("/search")
    Response<List<YoutubeItem>> search(
            @RequestParam String search,
            @RequestParam(required = false, defaultValue = "10") int maxSize
    );
}

package com.slowv.youtuberef.web.rest;

import com.slowv.youtuberef.integration.youtube.model.YoutubeItem;
import com.slowv.youtuberef.service.dto.response.Response;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(value = "/_api/v1/youtube")
public interface YoutubeController {

    @Secured({"ROLE_USER"})
    @GetMapping("/search")
    Response<List<YoutubeItem>> search(
            @RequestParam String search,
            @RequestParam(required = false, defaultValue = "10") int maxSize
    );
}

package com.slowv.youtuberef.web.rest;

import com.slowv.youtuberef.service.dto.VideoDto;
import com.slowv.youtuberef.service.dto.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/video")
public interface VideoController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Response<VideoDto> create(@RequestBody final VideoDto dto);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Response<Page<VideoDto>> getVideos();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Response<VideoDto> getVideo(@NonNull @PathVariable(name = "id") final String id);

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    Response<VideoDto> update(@RequestBody final VideoDto dto);

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    Response<Void> delete(@RequestBody final List<String> ids);
}

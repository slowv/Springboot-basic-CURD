package com.slowv.youtuberef.web.rest;

import com.slowv.youtuberef.service.dto.VideoDto;
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
    ResponseEntity<VideoDto> create(@RequestBody final VideoDto dto);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Page<VideoDto>> getVideos();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<VideoDto> getVideo(@NonNull @PathVariable(name = "id") final String id);

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<VideoDto> update(@RequestBody final VideoDto dto);

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> delete(@RequestBody final List<String> ids);
}

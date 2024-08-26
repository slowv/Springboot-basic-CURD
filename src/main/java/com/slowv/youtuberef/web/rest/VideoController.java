package com.slowv.youtuberef.web.rest;

import com.slowv.youtuberef.service.dto.VideoDto;
import com.slowv.youtuberef.service.dto.request.VideoSearchRequest;
import com.slowv.youtuberef.service.dto.response.PagingResponse;
import com.slowv.youtuberef.service.dto.response.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/_api/v1/admin/video")
public interface VideoController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Response<VideoDto> create(@Valid @RequestBody final VideoDto dto);

    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Response<PagingResponse<VideoDto>> getVideos(@RequestBody final VideoSearchRequest request);

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

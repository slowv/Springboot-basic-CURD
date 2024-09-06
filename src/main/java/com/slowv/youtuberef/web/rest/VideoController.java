package com.slowv.youtuberef.web.rest;

import com.slowv.youtuberef.service.dto.VideoDto;
import com.slowv.youtuberef.service.dto.request.VideoSearchRequest;
import com.slowv.youtuberef.service.dto.response.PagingResponse;
import com.slowv.youtuberef.service.dto.response.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/_api/v1/admin/video")
public interface VideoController {

    @Secured("ROLE_ADMIN")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Response<VideoDto> create(@Valid @RequestBody final VideoDto dto);

    @Secured("ROLE_ADMIN")
    @PostMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    Response<PagingResponse<VideoDto>> getVideos(@RequestBody final VideoSearchRequest request);

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Response<VideoDto> getVideo(@NonNull @PathVariable(name = "id") final String id);

    @Secured("ROLE_ADMIN")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    Response<VideoDto> update(@RequestBody final VideoDto dto);

    @Secured("ROLE_ADMIN")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    Response<Void> delete(@RequestBody final List<String> ids);
}

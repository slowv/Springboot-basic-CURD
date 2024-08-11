package com.slowv.youtuberef.service.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageableData {
    int pageNumber;
    int pageSize;
    int totalPage;
    long totalRecord;

    public PageableData setPageNumber(final int pageNumber) {
        this.pageNumber = pageNumber + 1;
        return this;
    }

    public PageableData setPageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageableData setTotalPage(final int totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public PageableData setTotalRecord(final long totalRecord) {
        this.totalRecord = totalRecord;
        return this;
    }
}

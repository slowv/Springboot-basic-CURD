package com.slowv.youtuberef.integration.youtube.model;


import java.math.BigInteger;

public record YoutubeItem (
        // Video
        String url,
        String title,
        String thumbnailUrl,
        String description,
        // Thông số
        BigInteger commentCount,
        BigInteger dislikeCount,
        BigInteger likeCount,
        BigInteger viewCount,
        // Kênh
        String channelName,
        String channelAvatar
){
    public YoutubeItem {
        dislikeCount = BigInteger.ZERO;
    }
}

package com.slowv.youtuberef.config.properties;

public interface YubutuDefault {
    interface Redis {
        String[] server = new String[]{"redis://localhost:6379"};
        int expiration = 3600;
        boolean cluster = false;
        int connectionPoolSize = 64;
        int connectionMinimumIdleSize = 24;
        int subscriptionConnectionPoolSize = 50;
        int subscriptionConnectionMinimumIdleSize = 1;
    }
}

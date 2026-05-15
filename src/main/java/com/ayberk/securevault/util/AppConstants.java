package com.ayberk.securevault.util;

public class AppConstants {

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String LOG_TOPIC = "securevault-autdit-logs";

    public static final int RATE_LIMIT_CAPACITY = 5;
    public static final int RATE_LIMIT_REFILL_TOKENS = 1;
    public static final int RATE_LIMIT_REFILL_DURATION_SECONDS = 1;

    private AppConstants() {}
}

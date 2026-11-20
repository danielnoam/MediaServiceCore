package com.liskovsoft.youtubeapi.block;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SponsorBlockApiHelper {
    public static final String SEGMENTS_URL = "https://sponsor.ajay.app/api/skipSegments";
    public static final String SEGMENTS_URL2 = "https://api.sponsor.ajay.app/api/skipSegments";
    /**
     * RETUBE(privacy): k-anonymity lookups. The server gets the first 4 hex chars of the video id's
     * SHA-256 and answers every video sharing that prefix, so it can't tell which one is playing.
     * <a href="https://wiki.sponsor.ajay.app/w/API_Docs#GET_/api/skipSegments/:sha256HashPrefix">API docs</a>
     */
    public static final int HASH_PREFIX_LENGTH = 4;

    public static String hashPrefix(String videoId) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(videoId.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (int i = 0; hex.length() < HASH_PREFIX_LENGTH; i++) {
                hex.append(String.format("%02x", digest[i]));
            }
            return hex.substring(0, HASH_PREFIX_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}

package com.liskovsoft.youtubeapi.block.data;

import com.liskovsoft.googlecommon.common.converters.jsonpath.JsonPath;

import java.util.List;

/**
 * Answer of {@code /api/skipSegments/<hashPrefix>}: one entry per video whose id hashes to that prefix.
 */
public class HashedSegmentList {
    @JsonPath("$[*]")
    private List<VideoSegments> mVideos;

    public SegmentList getSegmentList(String videoId) {
        if (mVideos == null || videoId == null) {
            return null;
        }

        for (VideoSegments video : mVideos) {
            if (video != null && videoId.equals(video.mVideoId)) {
                return new SegmentList(video.mSegments);
            }
        }

        return null;
    }

    public static class VideoSegments {
        @JsonPath("$.videoID")
        private String mVideoId;

        @JsonPath("$.segments[*]")
        private List<Segment> mSegments;
    }
}

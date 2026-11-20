package com.liskovsoft.youtubeapi.block.data;

import com.liskovsoft.googlecommon.common.converters.jsonpath.JsonPath;

import java.util.List;

public class SegmentList {
    @JsonPath("$[*]")
    private List<Segment> mSegments;

    public SegmentList() {
    }

    public SegmentList(List<Segment> segments) {
        mSegments = segments;
    }

    public List<Segment> getSegments() {
        return mSegments;
    }
}

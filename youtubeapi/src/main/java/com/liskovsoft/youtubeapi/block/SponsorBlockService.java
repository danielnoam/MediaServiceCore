package com.liskovsoft.youtubeapi.block;

import com.liskovsoft.sharedutils.prefs.GlobalPreferences;
import com.liskovsoft.youtubeapi.block.data.HashedSegmentList;
import com.liskovsoft.youtubeapi.block.data.SegmentList;
import com.liskovsoft.googlecommon.common.helpers.RetrofitHelper;
import com.liskovsoft.googlecommon.common.helpers.ServiceHelper;
import retrofit2.Call;

import java.util.Set;

public class SponsorBlockService {
    private static SponsorBlockService sInstance;
    private final SponsorBlockApi mSponsorBlockApi;

    private SponsorBlockService() {
        mSponsorBlockApi = RetrofitHelper.create(SponsorBlockApi.class);
    }

    public static SponsorBlockService instance() {
        if (sInstance == null) {
            sInstance = new SponsorBlockService();
        }

        return sInstance;
    }

    public SegmentList getSegmentList(String videoId) {
        if (videoId == null) {
            return null;
        }

        String prefix = SponsorBlockApiHelper.hashPrefix(videoId);
        Call<HashedSegmentList> wrapper =
                isAltServerEnabled() ? mSponsorBlockApi.getSegments2(prefix) : mSponsorBlockApi.getSegments(prefix);

        return pick(RetrofitHelper.get(wrapper), videoId);
    }

    public SegmentList getSegmentList(String videoId, Set<String> categories) {
        return categories != null && categories.size() > 0 ? getSegmentListInt(videoId, categories) : getSegmentList(videoId);
    }

    private SegmentList getSegmentListInt(String videoId, Set<String> categories) {
        if (videoId == null) {
            return null;
        }

        String prefix = SponsorBlockApiHelper.hashPrefix(videoId);
        String categoriesJson = ServiceHelper.toJsonArrayString(categories);
        Call<HashedSegmentList> wrapper = isAltServerEnabled() ?
                mSponsorBlockApi.getSegments2(prefix, categoriesJson) :
                mSponsorBlockApi.getSegments(prefix, categoriesJson);

        return pick(RetrofitHelper.get(wrapper), videoId);
    }

    private static SegmentList pick(HashedSegmentList list, String videoId) {
        return list != null ? list.getSegmentList(videoId) : null;
    }

    private boolean isAltServerEnabled() {
        return GlobalPreferences.isInitialized() && GlobalPreferences.sInstance.isContentBlockAltServerEnabled();
    }
}

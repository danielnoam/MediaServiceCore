package com.liskovsoft.youtubeapi.block;

import com.liskovsoft.mediaserviceinterfaces.data.SponsorSegment;
import com.liskovsoft.youtubeapi.block.data.HashedSegmentList;
import com.liskovsoft.youtubeapi.block.data.Segment;
import com.liskovsoft.youtubeapi.block.data.SegmentList;
import com.liskovsoft.googlecommon.common.helpers.ServiceHelper;
import com.liskovsoft.googlecommon.common.helpers.RetrofitHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;
import retrofit2.Call;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class SponsorBlockApiTest {
    private static final String VIDEO_ID = "0e3GPea1Tyg"; // Mr. Beast
    private SponsorBlockApi mService;

    @Before
    public void setUp() {
        // fix issue: No password supplied for PKCS#12 KeyStore
        // https://github.com/robolectric/robolectric/issues/5115
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");

        ShadowLog.stream = System.out; // catch Log class output

        mService = RetrofitHelper.create(SponsorBlockApi.class);
    }

    @Test
    public void testHashPrefixIsFirstFourHexCharsOfSha256() {
        assertEquals("46cb", SponsorBlockApiHelper.hashPrefix(VIDEO_ID)); // sha256("0e3GPea1Tyg")
    }

    @Test
    public void testThatSegmentResultNotEmpty() {
        Call<HashedSegmentList> wrapper = mService.getSegments(SponsorBlockApiHelper.hashPrefix(VIDEO_ID));

        SegmentList segmentList = RetrofitHelper.get(wrapper).getSegmentList(VIDEO_ID);

        List<Segment> segments = segmentList.getSegments();

        assertNotNull("Segment list not empty", segments);

        Segment firstSegment = segments.get(0);

        assertNotNull("Category not empty", firstSegment.getCategory());
        assertTrue("Start not empty", firstSegment.getStart() > 0);
        assertTrue("End not empty", firstSegment.getEnd() > 0);
        assertNotNull("UUID not empty", firstSegment.getUuid());
    }

    @Test
    public void testThatSegmentByCategoryResultNotEmpty() {
        Call<HashedSegmentList> wrapper = mService.getSegments(
                SponsorBlockApiHelper.hashPrefix(VIDEO_ID), ServiceHelper.toJsonArrayString(
                        SponsorSegment.CATEGORY_SPONSOR, SponsorSegment.CATEGORY_INTRO, SponsorSegment.CATEGORY_OUTRO,
                        SponsorSegment.CATEGORY_INTERACTION, SponsorSegment.CATEGORY_SELF_PROMO, SponsorSegment.CATEGORY_MUSIC_OFF_TOPIC
                )
        );

        SegmentList segmentList = RetrofitHelper.get(wrapper).getSegmentList(VIDEO_ID);

        List<Segment> segments = segmentList.getSegments();

        assertNotNull("Segment list not empty", segments);

        Segment firstSegment = segments.get(0);

        assertNotNull("Category not empty", firstSegment.getCategory());
        assertTrue("Start not empty", firstSegment.getStart() > 0);
        assertTrue("End not empty", firstSegment.getEnd() > 0);
        assertNotNull("UUID not empty", firstSegment.getUuid());
    }
}
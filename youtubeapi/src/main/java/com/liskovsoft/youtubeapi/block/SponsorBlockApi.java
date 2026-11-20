package com.liskovsoft.youtubeapi.block;

import com.liskovsoft.youtubeapi.block.data.HashedSegmentList;
import com.liskovsoft.googlecommon.common.converters.jsonpath.WithJsonPath;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * RETUBE(privacy): only the hash-prefix endpoints, never {@code ?videoID=}. See
 * {@link SponsorBlockApiHelper#hashPrefix}.
 */
@WithJsonPath
public interface SponsorBlockApi {
    @GET(SponsorBlockApiHelper.SEGMENTS_URL + "/{hashPrefix}")
    Call<HashedSegmentList> getSegments(@Path("hashPrefix") String hashPrefix);

    @GET(SponsorBlockApiHelper.SEGMENTS_URL + "/{hashPrefix}")
    Call<HashedSegmentList> getSegments(@Path("hashPrefix") String hashPrefix, @Query(value = "categories", encoded = true) String categoriesJsonArray);

    @GET(SponsorBlockApiHelper.SEGMENTS_URL2 + "/{hashPrefix}")
    Call<HashedSegmentList> getSegments2(@Path("hashPrefix") String hashPrefix);

    @GET(SponsorBlockApiHelper.SEGMENTS_URL2 + "/{hashPrefix}")
    Call<HashedSegmentList> getSegments2(@Path("hashPrefix") String hashPrefix, @Query(value = "categories", encoded = true) String categoriesJsonArray);
}

package com.liskovsoft.youtubeapi.dearrow

import com.liskovsoft.googlecommon.common.converters.gson.WithGson
import com.liskovsoft.youtubeapi.dearrow.data.BrandingList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

@WithGson
internal interface DeArrowApi {
    /**
     * RETUBE(privacy): k-anonymity lookup, keyed by video id. The server only sees the hash prefix
     * ([com.liskovsoft.youtubeapi.block.SponsorBlockApiHelper.hashPrefix]), never the video id.
     */
    @GET("${DeArrowApiHelper.BRANDING_URL}/{hashPrefix}")
    fun getBranding(@Path("hashPrefix") hashPrefix: String?): Call<Map<String, BrandingList?>?>?

    @GET(DeArrowApiHelper.THUMBNAIL_URL)
    fun getThumbnail(@Query("videoID") videoId: String?, @Query("time") timestamp: Float?): Call<ResponseBody?>?
}

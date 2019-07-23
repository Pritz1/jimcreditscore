package com.eijs.creditscore.api;


import com.eijs.creditscore.pojo.DefaultResponse;
import com.eijs.creditscore.pojo.DetHistOfDocRes;
import com.eijs.creditscore.pojo.EntDocListRes;
import com.eijs.creditscore.pojo.IsUpdateReqRes;
import com.eijs.creditscore.pojo.ListOfEmpRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    //to login
    @FormUrlEncoded
    @POST("login.php")
    Call<DefaultResponse> Login(
            @Field("ecode") String ecode,
            @Field("password") String password,
            @Field("date") String date
    );

    @POST("isupdaterequire.php")
    Call<IsUpdateReqRes> IsUpdateRequired();

    @POST("reportLink.php")
    Call<IsUpdateReqRes> ReportLink();

    @FormUrlEncoded
    @POST("emplist.php")
    Call<ListOfEmpRes> EmpList(
            @Field("ecode") String ecode,
            @Field("etype") String etype,
            @Field("yr") String yr,
            @Field("mth") String mth
    );

    @FormUrlEncoded
    @POST("historyEmpList.php")
    Call<ListOfEmpRes> HisEmpList(
            @Field("ecode") String ecode,
            @Field("etype") String etype,
            @Field("yr") String yr,
            @Field("mth") String mth,
            @Field("logdate") String logdate
    );

    @FormUrlEncoded
    @POST("hisDocList.php")
    Call<EntDocListRes> hisDocList(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("wrkdate") String date,
            @Field("yr") String yr,
            @Field("mth") String mth
    );


    @FormUrlEncoded
    @POST("getFullHistOfDoc.php")
    Call<DetHistOfDocRes> getFullHistOfDoc(
            @Field("ecode") String ecode,
            @Field("netid") String netid,
            @Field("cntcd") String cntcd,
            @Field("wrkdate") String date,
            @Field("yr") String yr,
            @Field("mth") String mth
    );

    @FormUrlEncoded
    @POST("doentrydoclist.php")
    Call<EntDocListRes> doEntryDocList(
            @Field("netid") String netid,
            @Field("wrkdate") String date
    );

    @FormUrlEncoded
    @POST("deleteEntry.php")
    Call<DefaultResponse> deleteEntry(
            @Field("ecode") String ecode,
            @Field("logecode") String logecode,
            @Field("cntcd") String cntcd,
            @Field("netid") String netid,
            @Field("mth") String mth,
            @Field("yr") String yr,
            @Field("wrkdate") String wrkdate
    );

    @FormUrlEncoded
    @POST("addScore.php")
    Call<DefaultResponse> addScore(
            @Field("ecode") String ecode,
            @Field("logecode") String logecode,
            @Field("ds") String ds,
            @Field("ms") String ms,
            @Field("cntcd") String cntcd,
            @Field("netid") String netid,
            @Field("mth") String mth,
            @Field("yr") String yr,
            @Field("wrkdate") String wrkdate
    );

    @FormUrlEncoded
    @POST("getScore.php")
    Call<DefaultResponse> getScore(
            @Field("ecode") String ecode,
            @Field("cntcd") String cntcd,
            @Field("netid") String netid,
            @Field("mth") String mth,
            @Field("yr") String yr,
            @Field("wrkdate") String wrkdate
    );
}

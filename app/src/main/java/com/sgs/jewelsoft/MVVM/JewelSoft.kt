package com.sgs.jewelsoft.MVVM

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface JewelSoft {

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun login(
        @Field("user") user: String,
        @Field("password") password: String,
        @Field("type") type: String,
        @Field("cid") cid: String
    ): Login

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun success(
        @Field("type") type: String,
        @Field("cid") cid: String,
        @Field("bid") bid: String,
        @Field("uid") uid: String,
        @Field("name") name: String,
        @Field("dob") dob: String,
        @Field("mobile") mobile: String,
        @Field("aniversary_date") aniversarydate: String,
        @Field("pre_address") presentAddress: String,
        @Field("perm_address") PermAddress: String,
        @Field("email") email: String,
        @Field("pan") pan: String,
        @Field("aadhar") aadhar: String
    ): Login

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun receiptType(
        @Field("sub_type") sub_type: String,
        @Field("type") type: String,
        @Field("cid") cid: String
    ): List<ReceiptType>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun paymentType(
        @Field("sub_type") sub_type: String,
        @Field("type") type: String,
        @Field("cid") cid: String
    ): List<PaymentType>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun accountType(
        @Field("sub_type") sub_type: String,
        @Field("type") type: String,
        @Field("cid") cid: String
    ): List<AccountType>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun AutoFillName(
        @Field("sub_type") sub_type: String,
        @Field("type") type: String,
        @Field("cid") cid: String,
        @Field("name") name: String
    ): List<AutoFillName>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun balance(
        @Field("sub_type") sub_type: String,
        @Field("type") type: String,
        @Field("cid") cid: String,
        @Field("name") name: String
    ): List<AutoFillName>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun saveData(
        @Field("sub_type") sub_type: String,
        @Field("type") type: String,
        @Field("cid") cid: String,

    ): List<AutoFillName>

}
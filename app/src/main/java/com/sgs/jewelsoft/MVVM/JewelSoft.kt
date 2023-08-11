package com.sgs.jewelsoft.MVVM

import android.system.StructTimespec
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.time.Duration
import java.time.temporal.TemporalAmount
import java.util.Date

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
        @Field("aadhar") aadhar: String,
        @Field("sch_type") sch_type: String
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
        @Field("name") name: String,
        @Field("chit") chit: String,
    ): Root

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun saveData(
        @Field("type") type: String,
        @Field("cid") cid: String,
        @Field("uid") uid: String,
        @Field("date") date: String,
        @Field("lname") lname: String,
        @Field("name") name: String,
        @Field("chit_id") chit_id: String,
        @Field("balance") balance: String,
        @Field("ptype") ptype: String,
        @Field("remark") remark: String,
        @Field("account") account: String,
        @Field("total_due") total_due: String,
        @Field("total_metal") total_metal: String,
        @Field("total") total: String,
        @Field("receipt_type") receiptType: String
    ): Login

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun viewReceipt(
        @Field("type") type: String,
        @Field("cid") cid: String
    ): List<ViewReceipt>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun viewReport(
        @Field("type") type: String,
        @Field("edate") eDate: String,
        @Field("sdate") sDate: String,
        @Field("cid") cid: String
    ): List<Report>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun scheme(
        @Field("type") type: String,
        @Field("sub_type") sub_type: String,
        @Field("cid") cid: String
    ): List<AutoFillName>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun enrollment(
        @Field("type") type: String,
        @Field("sub_type") sub_type: String,
        @Field("cid") cid: String,
        @Field("name") chit_id: String,
    ): List<Enrollment>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun enrollmentTwo(
        @Field("type") type: String,
        @Field("sub_type") sub_type: String,
        @Field("cid") cid: String,
        @Field("chit_id") chit: String
    ): List<EnrollmentTwo>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun enrollmentTwoShow(
        @Field("type") type: String,
        @Field("sub_type") sub_type: String,
        @Field("cid") cid: String,
        @Field("chit_id") chit: String
    ): List<EnrollmentShow>

    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun enrollmentName(
        @Field("type") type: String,
        @Field("cid") cid: String,
        @Field("sub_type") sub_type: String,
        @Field("name") name: String
    ): List<EnrollmentName>


    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun enrollmentScheme(
        @Field("type") type: String,
        @Field("cid") cid: String,
        @Field("sub_type") sub_type: String,
        @Field("sch") sch: String,
        @Field("date") date: String
    ): List<EnrollmentScheme>


    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun sales(
        @Field("type") type: String,
        @Field("cid") cid: String,
        @Field("sub_type") sub_type: String
    ): List<Sales>


    @FormUrlEncoded
    @POST("jewel_index.php")
    suspend fun saveEnrollment(
        @Field("cid") cid: String,
        @Field("uid") uid: String,
        @Field("lname") lname: String,
        @Field("name") name: String,
        @Field("sch_type") sch_type: String,
        @Field("date") date: String,
        @Field("duration") duration: String,
        @Field("dis") dis: String,
        @Field("metal_pr") metal_pr: String,
        @Field("amount") amount: String,
        @Field("mdate") mdate: String,
        @Field("total") total: String,
        @Field("remark") remark: String,
        @Field("sales_man") sales_man: String,
        @Field("type") type: String,
    ): SaveEnrollment
}
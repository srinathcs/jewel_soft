package com.sgs.jewelsoft.MVVM

class JewelSoftRepository {

    suspend fun login(
        user: String, password: String, type: String, cid: String
    ) = Retrofit.api.login(user, password, type, cid)

    suspend fun success(
        type: String,
        cid: String,
        bid: String,
        uid: String,
        name: String,
        dob: String,
        mobile: String,
        aniversarydate: String,
        presentAddress: String,
        PermAddress: String,
        email: String,
        pan: String,
        aadhar: String
    ) = Retrofit.api.success(
        type,
        cid,
        bid,
        uid,
        name,
        dob,
        mobile,
        aniversarydate,
        presentAddress,
        PermAddress,
        email,
        pan,
        aadhar
    )

    suspend fun receiptType(
        sub_type: String,
        type: String,
        cid: String
    ) = Retrofit.api.receiptType(sub_type, type, cid)

    suspend fun paymentType(
        sub_type: String,
        type: String,
        cid: String
    ) = Retrofit.api.paymentType(sub_type, type, cid)

    suspend fun accountType(
        sub_type: String,
        type: String,
        cid: String
    ) = Retrofit.api.accountType(sub_type, type, cid)

    suspend fun autoFillName(
        sub_type: String,
        type : String,
        cid: String,
        name: String
    ) = Retrofit.api.AutoFillName(sub_type,type, cid, name)

    suspend fun balance(
        sub_type: String,
        type : String,
        cid: String,
        name: String
    ) = Retrofit.api.balance(sub_type,type, cid, name)


}
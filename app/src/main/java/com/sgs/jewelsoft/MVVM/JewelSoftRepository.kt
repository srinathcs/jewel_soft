package com.sgs.jewelsoft.MVVM

import retrofit2.http.Field

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
        type: String,
        cid: String,
        name: String
    ) = Retrofit.api.AutoFillName(sub_type, type, cid, name)

    suspend fun balance(
        sub_type: String,
        type: String,
        cid: String,
        name: String,
        chit: String,
    ) = Retrofit.api.balance(sub_type, type, cid, name,chit)

    suspend fun viewReceipt(
        type: String,
        cid: String
    ) = Retrofit.api.viewReceipt(type, cid)

    suspend fun saveDateReceipt(
        type: String,
        cid: String,
        uid: String,
        date: String,
        lname: String,
        name: String,
        chit_id: String,
        balance: String,
        ptype: String,
        remark: String,
        account: String,
        total_due: String,
        total_metal: String,
        total:String
    ) = Retrofit.api.saveData(type,cid, uid, date, lname,name, chit_id, balance, ptype, remark, account, total_due, total_metal,total)
}
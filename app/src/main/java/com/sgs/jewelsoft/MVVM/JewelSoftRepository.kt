package com.sgs.jewelsoft.MVVM

class JewelSoftRepository {

    suspend fun login(
        user: String,
        password: String,
        type: String,
        cid: String
    ) = Retrofit.api.login(
        user,
        password,
        type,
        cid
    )

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
        aadhar: String,
        sch_type: String
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
        aadhar,
        sch_type
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
    ) = Retrofit.api.autoFillName(sub_type, type, cid, name)

    suspend fun balance(
        sub_type: String,
        type: String,
        cid: String,
        name: String,
        chit: String,
    ) = Retrofit.api.balance(sub_type, type, cid, name, chit)

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
        total: String,
        receiptType: String
    ) = Retrofit.api.saveData(
        type,
        cid,
        uid,
        date,
        lname,
        name,
        chit_id,
        balance,
        ptype,
        remark,
        account,
        total_due,
        total_metal,
        total,
        receiptType
    )

    suspend fun viewReport(
        type: String,
        edate: String,
        sdate: String,
        cid: String
    ) = Retrofit.api.viewReport(type, edate, sdate, cid)

    suspend fun scheme(
        type: String,
        sub_type: String,
        cid: String
    ) = Retrofit.api.scheme(type, sub_type, cid)

    suspend fun enrollment(
        type: String,
        sub_type: String,
        cid: String,
    chit_id: String
    ) = Retrofit.api.enrollment(type, sub_type, cid,chit_id)

    suspend fun enrollmentTwo(
        type: String,
        sub_type: String,
        cid: String,
        chit: String
    ) = Retrofit.api.enrollmentTwo(type, sub_type, cid, chit)

    suspend fun enrollmentShow(
        type: String,
        sub_type: String,
        cid: String,
        chit: String
    ) = Retrofit.api.enrollmentTwoShow(type, sub_type, cid, chit)

    suspend fun enrollmentName(
        type: String,
        sub_type: String,
        cid: String,
        name: String
    ) = Retrofit.api.enrollmentName(type, sub_type, cid,name)

    suspend fun enrollmentScheme(
        type: String,
        cid: String,
        sub_type: String,
        sch:String,
        date: String
    ) = Retrofit.api.enrollmentScheme(type, cid, sub_type, sch,date)

    suspend fun sales(
        type: String,
        cid: String,
        sub_type: String
    ) = Retrofit.api.sales(type, cid, sub_type)

    suspend fun saveEnrollment(
        cid: String,
        uid: String,
        lname: String,
        name: String,
        sch_type: String,
        date: String,
        duration: String,
        dis:String,
        metal_pr:String,
        amount:String,
        mdate:String,
        total: String,
        remark: String,
        sales_man:String,
        type:String
    ) = Retrofit.api.saveEnrollment(cid,uid, lname, name, sch_type, date, duration, dis, metal_pr, amount, mdate, total, remark,sales_man,type)
}
package com.sgs.jewelsoft.MVVM


data class Login(
    val uid: String,
    val led_id: String,
    val photo: String,
    val ssid: String,
    val uname: String,
    val cid: String,
    val role_id: String,
    val mobile: String,
    val com: Com,
    val is_firsttime: Boolean,
    val error: Boolean,
    val error_msg: String,
)

data class Com(
    val cid: String,
    val name: String,
    val address: String,
    val phone: String,
)

data class ReceiptType(
    val name: String,
    val value: String,
)

data class PaymentType(
    val name: String,
    val value: String,
)

data class AccountType(
    val name: String,
    val value: String,
    val chit_id: String,
    val id: String,
    val label: String,
)

data class AutoFillName(
    val label: String,
    val name: String,
    val lname: String,
    val value: String,
    val id: String,
    val chit_id: String,
    val balance: String,
)

data class Rate(
    val act: Any,
    val aid: String,
    val bid: String,
    val cid: String,
    val date: String,
    val del: String,
    val dtime: String,
    val id: String,
    val is_d: String,
    val metal: String,
    val purity: String,
    val rate: String,
    val uid: String
)

data class Balance(
    val balance: String,
    val rate: List<Rate>
)

data class MyRate (
    var purity: String? = null
        )

data class Root (
    var balance: String? = null,
    var rate: MyRate? = null
        )

data class ViewReceipt(

    val date: String,
    val lname: String,
    val name: String,
    val balance: String,
    val total: String,
    val ptype: String,
    val account: String,
    val remark: String,
    val chit_id: String
)

data class SaveData(
    val error: Boolean,
    val error_msg: String,
)

data class Report(
    val date: String,
    val lname: String,
    val name: String,
    val balance: String,
    val total: String,
    val ptype: String,
    val account: String,
    val remark: String,
    val chit_id: String,
)

data class Enrollment(
    val label: String,
    val lname: String,
    val id: String,
    val chit_id: String
)

data class EnrollmentTwo(
    val lname: String,
    val name: String,
    val chit_id: String,
    val mdate: String,
    val date: String,
    val sch_type: String,
    val sch_name: String
)

data class EnrollmentShow(
    val due_no: String,
    val due_date: String,
    val chit_id: String,
    val amount: String,
    val paid_amount: String
)

data class EnrollmentName(
    val name: String,
    val id: String,
    val label: String,
    val mobile: String
)

data class EnrollmentScheme(
    val id: String,
    val aid: String,
    val uid: String,
    val bid: String,
    val cid: String,
    val name: String,
    val emi: String,
    val free_emi: String,
    val metal_booked_purity: String,
    val scheme_type: String,
    val del: String,
    val is_d: String,
    val act: String,
    val dtime: String,
    val mat_date: String
)

data class Sales(
    val name: String,
    val id: String
)

data class SaveEnrollment(
val error: Boolean,
val error_msg: String
)

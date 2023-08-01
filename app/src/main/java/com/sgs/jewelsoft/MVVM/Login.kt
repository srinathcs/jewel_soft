package com.sgs.jewelsoft.MVVM

import java.util.Date

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
)

data class AutoFillName(
    val label: String,
    val value: String,
    val chit_id: String,
    val balance: String,
)

data class SaveData(
    val cid: String,
    val type: String,
    val sub_type: String
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
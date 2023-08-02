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

data class Balance (
    val balance: String,
    val rate: List<Rate>
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
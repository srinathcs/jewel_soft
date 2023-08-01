package com.sgs.jewelsoft.MVVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sgs.jewelsoft.Resources
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JewelSoftViewModel(private val jewelSoftRepo: JewelSoftRepository) : ViewModel() {
    private val testLogin = MutableStateFlow<Resources<Login>>(Resources.Loading())
    val testLoginFlow: StateFlow<Resources<Login>> = testLogin

    suspend fun data(
        user: String, password: String, type: String, cid: String
    ) = viewModelScope.launch {
        try {
            val response = jewelSoftRepo.login(user, password, type, cid)
            testLogin.value = Resources.Success(response)
        } catch (exception: Exception) {
            testLogin.value = Resources.Error(exception.message.toString())
        }
    }

    private val testSuccess = MutableStateFlow<Resources<Login>>(Resources.Loading())
    val testSuccessFlow: StateFlow<Resources<Login>> = testSuccess

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
    ) = viewModelScope.launch {
        try {
            val response = jewelSoftRepo.success(
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
            testSuccess.value = Resources.Success(response)
        } catch (exception: Exception) {
            testSuccess.value = Resources.Error(exception.message.toString())
        }
    }


    private val testEntryReceipt =
        MutableStateFlow<Resources<List<ReceiptType>>>(Resources.Loading())
    val testEntryReceiptFlow: StateFlow<Resources<List<ReceiptType>>> = testEntryReceipt

    suspend fun receiptType(
        sub_type: String, type: String, cid: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.receiptType(
                sub_type, type, cid
            )
            testEntryReceipt.value = Resources.Success(resources)
        } catch (exception: Exception) {
            testEntryReceipt.value = Resources.Error(exception.message.toString())
        }
    }

    private val testPaymentType =
        MutableStateFlow<Resources<List<PaymentType>>>(Resources.Loading())
    val testPaymentTypeFlow: StateFlow<Resources<List<PaymentType>>> = testPaymentType

    suspend fun paymentType(
        sub_type: String, type: String, cid: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.paymentType(
                sub_type, type, cid
            )
            testPaymentType.value = Resources.Success(resources)
        } catch (exception: Exception) {
            testPaymentType.value = Resources.Error(exception.message.toString())
        }
    }

    private val testAccountType =
        MutableStateFlow<Resources<List<AccountType>>>(Resources.Loading())
    val testAccountTypeFlow: StateFlow<Resources<List<AccountType>>> = testAccountType

    suspend fun accountType(
        sub_type: String, type: String, cid: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.accountType(
                sub_type, type, cid
            )
            testAccountType.value = Resources.Success(resources)
        } catch (exception: Exception) {
            testAccountType.value = Resources.Error(exception.message.toString())
        }
    }

    private val testAutoFillName =
        MutableStateFlow<Resources<List<AutoFillName>>>(Resources.Loading())
    val testAutoFillNameFlow: StateFlow<Resources<List<AutoFillName>>> = testAutoFillName

    suspend fun autoFillName(
        sub_type: String, type: String, cid: String, name: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.autoFillName(
                sub_type, type, cid, name
            )
            testAutoFillName.value = Resources.Success(resources)
        } catch (exception: Exception) {
            testAutoFillName.value = Resources.Error(exception.message.toString())
        }
    }

    private val balanceName =
        MutableStateFlow<Resources<List<AutoFillName>>>(Resources.Loading())
    val balanceShow: StateFlow<Resources<List<AutoFillName>>>
        get() = balanceName

    suspend fun balance(
        sub_type: String, type: String, cid: String, name: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.balance(
                sub_type, type, cid, name
            )
            balanceName.value = Resources.Success(resources)
        } catch (exception: Exception) {
            balanceName.value = Resources.Error(exception.message.toString())
        }
    }



}
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
        aadhar: String,
        sch_type: String
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
                aadhar,
                sch_type
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
        MutableStateFlow<Resources<Root>>(Resources.Loading())
    val balanceShow: StateFlow<Resources<Root>>
        get() = balanceName

    suspend fun balance(
        sub_type: String, type: String, cid: String, name: String, chit: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.balance(
                sub_type, type, cid, name, chit
            )
            balanceName.value = Resources.Success(resources)
        } catch (exception: Exception) {
            balanceName.value = Resources.Error(exception.message.toString())
        }
    }

    private val viewReceipt = MutableStateFlow<Resources<List<ViewReceipt>>>(Resources.Loading())
    val viewReceiptShow: StateFlow<Resources<List<ViewReceipt>>> = viewReceipt

    suspend fun viewReceipt(
        type: String,
        cid: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.viewReceipt(
                type, cid
            )
            viewReceipt.value = Resources.Success(resources)
        } catch (exception: Exception) {
            viewReceipt.value = Resources.Error(exception.message.toString())
        }
    }

    private val saveReceipt = MutableStateFlow<Resources<Login>>(Resources.Loading())
    val saveReceiptFlow: StateFlow<Resources<Login>> = saveReceipt

    suspend fun saveReceipt(
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
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.saveDateReceipt(
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
            saveReceipt.value = Resources.Success(resources)
        } catch (exception: Exception) {
            saveReceipt.value = Resources.Error(exception.message.toString())
        }
    }


    private val viewReport = MutableStateFlow<Resources<List<Report>>>(Resources.Loading())
    val viewReportFlow: StateFlow<Resources<List<Report>>> = viewReport

    suspend fun viewReport(

        type: String,
        edate: String,
        sdate: String,
        cid: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.viewReport(
                type,
                edate,
                sdate,
                cid
            )
            viewReport.value = Resources.Success(resources)
        } catch (e: Exception) {
            viewReport.value = Resources.Error(e.message.toString())
        }

    }


    private val scheme = MutableStateFlow<Resources<List<AutoFillName>>>(Resources.Loading())
    val schemeFlow: StateFlow<Resources<List<AutoFillName>>> = scheme

    suspend fun scheme(
        type: String,
        sub_type: String,
        cid: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.scheme(
                type, sub_type, cid
            )
            scheme.value = Resources.Success(resources)
        } catch (e: Exception) {
            scheme.value = Resources.Error(e.message.toString())
        }
    }

    private val enrollment = MutableStateFlow<Resources<List<Enrollment>>>(Resources.Loading())
    val enrollmentFlow: StateFlow<Resources<List<Enrollment>>> = enrollment

    suspend fun enrollment(
        type: String,
        sub_type: String,
        cid: String,
        chit_id: String
    ) = viewModelScope.launch {
        try {
            val resources = jewelSoftRepo.enrollment(
                type, sub_type, cid, chit_id
            )
            enrollment.value = Resources.Success(resources)
        } catch (e: Exception) {
            enrollment.value = Resources.Error(e.message.toString())
        }
    }

    private val enrollmentTwo =
        MutableStateFlow<Resources<List<EnrollmentTwo>>>(Resources.Loading())
    val enrollmentTwoFlow: StateFlow<Resources<List<EnrollmentTwo>>> = enrollmentTwo

    suspend fun enrollmentTwo(
        type: String,
        sub_type: String,
        cid: String,
        chit: String
    ) = viewModelScope.launch {
        try {
            val response = jewelSoftRepo.enrollmentTwo(
                type, sub_type, cid, chit
            )
            enrollmentTwo.value = Resources.Success(response)
        } catch (e: Exception) {
            enrollmentTwo.value = Resources.Error(e.message.toString())
        }
    }

    private val enrollmentShowTwo =
        MutableStateFlow<Resources<List<EnrollmentShow>>>(Resources.Loading())
    val enrollmentShowTwoFlow: StateFlow<Resources<List<EnrollmentShow>>> = enrollmentShowTwo

    suspend fun enrollmentShow(
        type: String,
        sub_type: String,
        cid: String,
        chit: String
    ) = viewModelScope.launch {
        try {
            val response = jewelSoftRepo.enrollmentShow(
                type, sub_type, cid, chit
            )
            enrollmentShowTwo.value = Resources.Success(response)
        } catch (e: Exception) {
            enrollmentShowTwo.value = Resources.Error(e.message.toString())
        }
    }

    private val enrollmentName =
        MutableStateFlow<Resources<List<EnrollmentName>>>(Resources.Loading())
    val enrollmentNameFlow: StateFlow<Resources<List<EnrollmentName>>> = enrollmentName

    suspend fun enrollmentName(
        type: String,
        sub_type: String,
        cid: String,
        name: String
    ) = viewModelScope.launch {
        try {
            val response = jewelSoftRepo.enrollmentName(
                type, sub_type, cid,name
            )
            enrollmentName.value = Resources.Success(response)
        } catch (e: Exception) {
            enrollmentName.value = Resources.Error(e.message.toString())
        }
    }

    private val enrollmentScheme =
        MutableStateFlow<Resources<List<EnrollmentScheme>>>(Resources.Loading())
    val enrollmentSchemeFlow: StateFlow<Resources<List<EnrollmentScheme>>> = enrollmentScheme

    suspend fun enrollmentScheme(
        type: String,
        cid: String,
        sub_type: String,
        sch:String,
        date: String
    ) = viewModelScope.launch {
        try {
            val response = jewelSoftRepo.enrollmentScheme(
                type, cid, sub_type, sch ,date
            )
            enrollmentScheme.value = Resources.Success(response)
        } catch (e: Exception) {
            enrollmentScheme.value = Resources.Error(e.message.toString())
        }
    }

    private val sales = MutableStateFlow<Resources<List<Sales>>>(Resources.Loading())
    val salesFlow: StateFlow<Resources<List<Sales>>> = sales

    suspend fun sales(
        type: String,
        cid: String,
        sub_type: String,

    ) = viewModelScope.launch {
        try {
            val response = jewelSoftRepo.sales(
                type, cid, sub_type
            )
          sales.value = Resources.Success(response)
        } catch (e: Exception) {
            sales.value = Resources.Error(e.message.toString())
        }
    }

    private val saveEnrollment = MutableStateFlow<Resources<SaveEnrollment>>(Resources.Loading())
    val saveEnrollmentFlow: StateFlow<Resources<SaveEnrollment>> = saveEnrollment

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

        ) = viewModelScope.launch {
        try {
            val response = jewelSoftRepo.saveEnrollment(
                cid, uid, lname, name, sch_type, date, duration, dis, metal_pr, amount, mdate, total, remark, sales_man,type)

            saveEnrollment.value = Resources.Success(response)
        } catch (e: Exception) {
            saveEnrollment.value = Resources.Error(e.message.toString())
        }
    }


}
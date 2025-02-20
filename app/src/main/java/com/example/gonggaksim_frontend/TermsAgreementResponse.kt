package com.example.gonggaksim_frontend

data class TermsAgreementResponse(
    val success: Boolean,
    val message: String,
    val data: AgreementData?
)

data class AgreementData(
    val user: UserInfo,
    val agreements: AgreementStatus
)

data class UserInfo(
    val id: Int,
    val email: String,
    val hasAgreedToTerms: Boolean
)

data class AgreementStatus(
    val required1: Boolean,
    val required2: Boolean,
    val optional: Boolean
)
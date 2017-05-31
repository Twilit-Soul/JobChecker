package com.turlington.edjoin


import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class Datum {
    val postingID: Int? = null
    val positionTitle: String? = null
    val salaryInfo: String? = null
    private val beginningSalary: Any? = null
    private val endingSalary: Any? = null
    private val displayFlag: String? = null
    private val postingDate: String? = null
    private val displayUntil: String? = null
    val countyName: String? = null
    val districtName: String? = null
    val city: String? = null
    private val isRecruitmentCenter: Boolean? = null
    private val zip: String? = null
    private val jobClassification: String? = null
    private val numberOpenings: Int? = null
    private val recruitmentCenterID: Int? = null
    private val isAdminJob: Boolean? = null
    private val categoryName: String? = null
    private val categoryID: Int? = null
    private val jobTypeID: Int? = null
    private val countyID: Int? = null
    private val accountID: Int? = null
    private val postingInformation: String? = null
    private val onlineApp: Boolean? = null
    private val state: Int? = null
    private val fullCountyName: String? = null
    private val jobType: Any? = null
    private val isSummerSchool: Boolean? = null
    private val limitPosting: Boolean? = null
    private val accountType: Int? = null

    fun getDisplayUntil(): String {
        val millis = java.lang.Long.valueOf(displayUntil!!.substring(6, displayUntil.length - 2))!!
        val localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime()
        return localDate.format(dateFormatter)
    }

    companion object {
        private val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a")
    }
}


package com.turlington.edjoin;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Datum {

    private Integer postingID;
    private String positionTitle;
    private String salaryInfo;
    private Object beginningSalary;
    private Object endingSalary;
    private String displayFlag;
    private String postingDate;
    private String displayUntil;
    private String countyName;
    private String districtName;
    private String city;
    private Boolean isRecruitmentCenter;
    private String zip;
    private String jobClassification;
    private Integer numberOpenings;
    private Integer recruitmentCenterID;
    private Boolean isAdminJob;
    private String categoryName;
    private Integer categoryID;
    private Integer jobTypeID;
    private Integer countyID;
    private Integer accountID;
    private String postingInformation;
    private Boolean onlineApp;
    private Integer state;
    private String fullCountyName;
    private Object jobType;
    private Boolean isSummerSchool;
    private Boolean limitPosting;
    private Integer accountType;

    public Integer getPostingID() {
        return postingID;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public String getSalaryInfo() {
        return salaryInfo;
    }

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a");

    public String getDisplayUntil() {
    	long millis = Long.valueOf(displayUntil.substring(6, displayUntil.length() - 2));
		LocalDateTime localDate = Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
		return localDate.format(dateFormatter);
	}

    public String getCountyName() {
        return countyName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getCity() {
        return city;
    }
}

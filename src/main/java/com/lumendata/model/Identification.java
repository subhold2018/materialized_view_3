package com.lumendata.model;

import lombok.Data;

@Data
public class Identification {
    private String updated;
    private String updatedBy;
    private String state;
    private String contactId;
    private String country;
    private String effectiveEndDate;
    private String effectiveStartDate;
    private String nationalID;
    private String nationalIDType;
    private String logicalDeleteFlg;
    private String isPrimary;
    private String operation;
    private String source;
    private String sourceId;
}

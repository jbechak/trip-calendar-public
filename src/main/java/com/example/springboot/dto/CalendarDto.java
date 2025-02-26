package com.example.springboot.dto;

import java.util.Date;

public class CalendarDto {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;

//    public CalendarModel() {}
//
//    public CalendarModel(String id, String name, String description) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//    }

    public String getName() {
        return name;
    }
    public void setName(String name) { this.name = name; }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

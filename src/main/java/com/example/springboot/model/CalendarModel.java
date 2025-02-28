package com.example.springboot.model;

import java.util.ArrayList;
import java.util.List;

public class CalendarModel {
    private String id;
    private String userId;
    private String name;
    private String description;
    private List<CalendarDateModel> calendarDates = new ArrayList<>();

    public CalendarModel() {}

    public CalendarModel(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public List<CalendarDateModel> getCalendarDates() {
        return calendarDates;
    }
    public void setCalendarDates(List<CalendarDateModel> calendarDates) {
        this.calendarDates = calendarDates;
    }
}

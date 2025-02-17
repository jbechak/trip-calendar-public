package com.example.springboot.model;

import java.util.ArrayList;
import java.util.List;

public class CalendarModel {
    private String id;
    private String name;
    private String description;
    private List<CalendarDateModel> calendarDates = new ArrayList<>();

    public CalendarModel() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

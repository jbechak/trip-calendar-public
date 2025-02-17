package com.example.springboot.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarDateModel {
    private String id;
    private String calendarId;
    private Date eventDate;
    private List<CellModel> cells = new ArrayList<>();

    public CalendarDateModel() {}

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCalendarId() {
        return calendarId;
    }
    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public Date getEventDate() {
        return eventDate;
    }
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public List<CellModel> getCells() {
        return cells;
    }
    public void setCells(List<CellModel> cells) {
        this.cells = cells;
    }

}

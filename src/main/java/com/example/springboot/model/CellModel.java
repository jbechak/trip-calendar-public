package com.example.springboot.model;

public class CellModel {
    private String id;
    private String calendarDateId;
    private String text;
    private String color;
    private String backgroundColor;
    private Boolean isBold;
    private int sortOrder;

    public CellModel() {}

    public CellModel(String id, String calendarDateId, String text, Boolean isBold, int sortOrder) {
        this.id = id;
        this.calendarDateId = calendarDateId;
        this.text = text;
        this.isBold = isBold;
        this.sortOrder = sortOrder;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getCalendarDateId() {
        return calendarDateId;
    }
    public void setCalendarDateId(String calendarDateId) {
        this.calendarDateId = calendarDateId;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Boolean getIsBold() {
        return isBold;
    }
    public void setIsBold(Boolean isBold) {
        this.isBold = isBold;
    }

    public int getSortOrder() {
        return sortOrder;
    }
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

}

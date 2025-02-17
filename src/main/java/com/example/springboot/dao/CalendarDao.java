package com.example.springboot.dao;

import com.example.springboot.model.CalendarModel;

public interface CalendarDao {
    CalendarModel get(String id);
}

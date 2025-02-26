package com.example.springboot.dao;

import com.example.springboot.dto.CalendarDto;
import com.example.springboot.model.CalendarModel;

import java.util.List;

public interface CalendarDao {
    List<CalendarModel> getAll();
    CalendarModel get(String id);
    CalendarModel createCalendar(CalendarDto dto);
}

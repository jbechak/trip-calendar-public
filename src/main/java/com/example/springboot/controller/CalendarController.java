package com.example.springboot.controller;

import com.example.springboot.dao.CalendarDao;
import com.example.springboot.dto.CalendarDto;
import com.example.springboot.model.CalendarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    CalendarDao dao;

    @GetMapping()
    public List<CalendarModel> getAll() {
        var calendars = dao.getAll();
        return calendars;
    }

    @GetMapping("/{id}")
    public CalendarModel getCalendar(@PathVariable String id) {
        return dao.get(id);
    }

    @PostMapping()
    public CalendarModel createCalendar(@RequestBody CalendarDto dto) {
        var calendar = dao.createCalendar(dto);
        return calendar;
    }
}

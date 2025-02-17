package com.example.springboot.controller;

import com.example.springboot.dao.CalendarDao;
import com.example.springboot.model.CalendarModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/calendar")
public class CalendarController {

    @Autowired
    CalendarDao dao;

    @GetMapping()
    public CalendarModel getCalendar() {
        var calendar = dao.get("99278890C1D343F0910C4B68D2D619CC");
        return calendar;
        //return dao.get("99278890C1D343F0910C4B68D2D619CC");
        //return "Greetings from Spring Boot!";
    }

//    @GetMapping()
//    public String index() {
//        return "Greetings from Spring Boot!";
//    }

}

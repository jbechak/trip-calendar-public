package com.example.springboot.controller;

import com.example.springboot.dao.CellDao;
import com.example.springboot.model.CellModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
@RequestMapping("/cell")
public class CellController {

    @Autowired
    CellDao dao;

    @PostMapping()
    public String createCell(@RequestBody CellModel cell) {
        try {
            return dao.createCell(cell);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PutMapping()
    public void updateCell(@RequestBody CellModel cell) {
        dao.updateCell(cell);
    }
}

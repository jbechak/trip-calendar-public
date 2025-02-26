package com.example.springboot.dao;

import com.example.springboot.model.CellModel;

public interface CellDao {
    String createCell(CellModel cell);
    void updateCell(CellModel cell);
}

package com.example.springboot.dao;

import com.example.springboot.model.CalendarModel;
import com.example.springboot.model.CellModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JdbcCellDao implements CellDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcCellDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String createCell(CellModel cell) {
        String id = UUID.randomUUID().toString().replace("-", "");
        int sortOrder = getCellCountByCalendarDate(cell.getCalendarDateId()) + 1;
        String sql = "INSERT INTO cell (id, calendar_date_id, text, color, background_color, is_bold, sort_order) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(sql,
            id,
            cell.getCalendarDateId(),
            cell.getText(),
            cell.getColor(),
            cell.getBackgroundColor(),
            cell.getIsBold(),
            sortOrder);
        return id;
    }
    @Override
    public void updateCell(CellModel cell) {
        String sql = "update cell " +
            "set text = ?, color = ?, background_color = ?, is_bold = ?, sort_order = ? " +
            "where id = ?;";
        jdbcTemplate.update(sql,
            cell.getText(),
            cell.getColor(),
            cell.getBackgroundColor(),
            cell.getIsBold(),
            cell.getSortOrder(),
            cell.getId());
    }

    private int getCellCountByCalendarDate(String calendarDateId) {
        String sql = "SELECT COUNT(*) FROM cell WHERE calendar_date_id = ?;";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, calendarDateId);
        return count;
    }

    @Override
    public void deleteCell(String id) {
        String sql = "DELETE FROM cell WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }
}

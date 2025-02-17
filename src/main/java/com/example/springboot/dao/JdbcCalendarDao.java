package com.example.springboot.dao;

import com.example.springboot.model.CalendarDateModel;
import com.example.springboot.model.CalendarModel;
import com.example.springboot.model.CellModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcCalendarDao implements CalendarDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcCalendarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CalendarModel get(String id) {
        String sql = "SELECT " +
        "\tcalendar.id AS \"calendarId\",\n" +
        "\tname,\n" +
        "\tdescription,\n" +
        "\tcalendar_date.id AS \"calendarDateId\",\n" +
        "\tevent_date AS \"eventDate\",\n" +
        "\tdate_cell.id AS \"cellId\",\n" +
        "\ttext,\n" +
        "\tcolor,\n" +
        "\tbackground_color AS \"backgroundColor\",\n" +
        "\tis_bold AS \"isBold\"" +
        "FROM calendar " +
        "LEFT JOIN calendar_date ON calendar.id = calendar_date.calendar_id " +
        "LEFT JOIN date_cell ON date_cell.calendar_date_id = calendar_date.id " +
        "WHERE calendar.id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        CalendarModel calendar = mapRowToCalendar(results);
        return calendar;
    }

    private CalendarModel mapRowToCalendar(SqlRowSet results) {
        boolean isFirstRowMapped = false;
        var calendar = new CalendarModel();

        while (results.next()) {
            if (!isFirstRowMapped) {
                calendar.setId(results.getString("calendarId"));
                calendar.setName(results.getString("name"));
                calendar.setDescription(results.getString("description"));
                isFirstRowMapped = true;
            }
            mapCalendarDate(calendar, results);
        }
        return isFirstRowMapped ? calendar : null;
    }

    private void mapCalendarDate(CalendarModel calendar, SqlRowSet results) {
        var calendarDateId = results.getString("calendarDateId");
        var calendarDate = calendar.getCalendarDates()
            .stream()
            .filter(x -> calendarDateId.equals(x.getId()))
            .findAny()
            .orElse(new CalendarDateModel());

        if (calendarDate.getCalendarId() == null) {
            calendarDate.setId(calendarDateId);
            calendarDate.setCalendarId(calendar.getId());
            calendarDate.setEventDate(results.getDate("eventDate"));
            calendar.getCalendarDates().add(calendarDate);
        }
        mapCell(calendarDate, results);
    }

    private void mapCell(CalendarDateModel calendarDate, SqlRowSet results) {
        var cellId = results.getString("cellId");
        if (cellId != null) {
            CellModel cell = new CellModel(
                cellId,
                calendarDate.getId(),
                results.getString("text"),
                results.getBoolean("isBold")
            );
            cell.setColor(results.getString("color"));
            cell.setBackgroundColor(results.getString("backgroundColor"));
            calendarDate.getCells().add(cell);
        }
    }
}
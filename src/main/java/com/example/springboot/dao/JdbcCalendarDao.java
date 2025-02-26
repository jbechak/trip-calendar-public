package com.example.springboot.dao;

import com.example.springboot.dto.CalendarDto;
import com.example.springboot.model.CalendarDateModel;
import com.example.springboot.model.CalendarModel;
import com.example.springboot.model.CellModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.example.springboot.utility.General;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JdbcCalendarDao implements CalendarDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcCalendarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CalendarModel> getAll() {
        String sql = "SELECT * FROM calendar ORDER BY name;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        List<CalendarModel> calendars = new ArrayList<>();
        while (results.next()) {
            calendars.add(mapRowToFlatCalendar(results));
        }
        return calendars;
    }

    @Override
    public CalendarModel get(String id) {
        String sql = "SELECT " +
        "\tcalendar.id AS \"calendarId\",\n" +
        "\tname,\n" +
        "\tdescription,\n" +
        "\tcalendar_date.id AS \"calendarDateId\",\n" +
        "\tevent_date AS \"eventDate\",\n" +
        "\tcell.id AS \"cellId\",\n" +
        "\ttext,\n" +
        "\tcolor,\n" +
        "\tbackground_color AS \"backgroundColor\",\n" +
        "\tis_bold AS \"isBold\",\n" +
        "\tsort_order AS \"sortOrder\"" +
        "FROM calendar " +
        "LEFT JOIN calendar_date ON calendar.id = calendar_date.calendar_id " +
        "LEFT JOIN cell ON cell.calendar_date_id = calendar_date.id " +
        "WHERE calendar.id = ? " +
        "ORDER BY event_date, sort_order;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        CalendarModel calendar = mapRowToCalendar(results);
        return calendar;
    }

    @Override
    public CalendarModel createCalendar(CalendarDto dto) {
        var id = General.getGuid();
        String sql = "INSERT INTO calendar (id, name, description) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, id, dto.getName(), dto.getDescription());
        createCalendarDates(dto, id);
        return get(id);
    }

    private void createCalendarDates(CalendarDto dto, String calendarId) {
        LocalDate startDate = dto.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = dto.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        for (var date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            var id = General.getGuid();
            String sql = "INSERT INTO calendar_date (id, calendar_id, event_date) VALUES (?, ?, ?);";
            jdbcTemplate.update(sql, id, calendarId, date);
        }
    }

    private CalendarModel mapRowToFlatCalendar(SqlRowSet result) {
        return new CalendarModel(
            result.getString("id"),
            result.getString("name"),
            result.getString("description")
        );
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
                results.getBoolean("isBold"),
                results.getInt("sortOrder")
            );
            cell.setColor(results.getString("color"));
            cell.setBackgroundColor(results.getString("backgroundColor"));
            calendarDate.getCells().add(cell);
        }
    }
}
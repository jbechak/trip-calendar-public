import '../App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { useState, useEffect, useMemo } from "react";
import calendarService from '../services/calendarService';
import CalendarCreate from './CalendarCreate';

function CalendarList({ setView, view, userId, setCalendarId }) {
  const [calendarData, setCalendarData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [show, setShow] = useState(false);

  const handleShow = () => setShow(true);

  async function fetchData() {
    try {
      const responseData = await calendarService.getAll(userId);
      setCalendarData(responseData);
      setLoading(false);
    } catch(error) {
      setError(error);
      setLoading(false);
    }
  }
  
  // Call fetchData inside useEffect
  useEffect(() => {
    if (userId) {
      fetchData();
    }
  }, [userId]);  // Depend on userId
  
  if (loading && !userId) return <p>Loading...</p>;
  if (error) return <p>Error: {error.message}</p>;
    
  function handleAddStart() {
    handleShow()
  }

  function openCalendar(id) {
    setCalendarId(id);
    setView("calendar");
  }

  function handleClick(cellId) {
    setCalendarData((prevData) => ({
      ...prevData,
      calendarDates: prevData.calendarDates.map((day) => ({
        ...day,
        cells: day.cells.map((cell) =>
          cell.id === cellId ? { ...cell, text: "Updated!" } : cell
        ),
      })),
    }));
  }
    
  const dataRows = calendarData?.map(calendar => 
    <tr key={calendar.id} className="text-start hover-pointer" onClick={() => openCalendar(calendar.id)}>
      <td>{calendar.name}</td>
      <td>{calendar.description}</td>
    </tr>
  )

  return (
    <>
      <h1>Trip Calendars</h1>
      <table className="table table-bordered text-start">
        <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {dataRows}
          {!calendarData?.length && (<tr className="text-center my-2 border"><td>You have no calendars</td></tr>)}
        </tbody>
      </table>
      <div className="text-start text-primary hover-pointer" onClick={() => handleAddStart()}>
        <FontAwesomeIcon icon={faPlus} className="pe-1" />Add Calendar
      </div>
      <CalendarCreate show={show} setShow={setShow} setView={setView} view={view} userId={userId} setCalendarId={setCalendarId} />
    </>
  )
}

export default CalendarList
import '../App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus, faCircleArrowLeft, faEye, faTrash } from '@fortawesome/free-solid-svg-icons';
import { useState, useEffect } from "react";
import calendarService from '../services/calendarService';

function Calendar({ setView, view, calendarId }) {
  const [calendar, setCalendar] = useState(null);
  const [loading, setLoading] = useState(true);
  const [isInitialized, setIsInitialized] = useState(false);
  const [isUpdating, setIsUpdating] = useState(false);
  const [isViewMode, setIsViewMode] = useState(false);
  const [error, setError] = useState(null);
  const [weeks, setWeeks] = useState([]);
  const [editingCellId, setEditingCellId] = useState(null);
  const [addCellCalendarDateId, setAddCellCalendarDateId] = useState(null);
  const [editText, setEditText] = useState("");
  const [addText, setAddText] = useState("");
  const [textColor, setTextColor] = useState("");
  const [backgroundColor, setBackgroundColor] = useState("");

  async function fetchData() {
    try {
      setLoading(true);
      const responseData = await calendarService.getCalendar(calendarId);
      setCalendar(responseData);
      setLoading(false);
      if (!isInitialized)
        setIsInitialized(true);
    } catch(error) {
      setError(error);
      setLoading(false);
    }
  }
  
  // Call fetchData inside useEffect
  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    let isActive = true;
  
    async function keepViewUpdated() {
      if (isUpdating) return;
  
      setIsUpdating(true);
      await new Promise((r) => setTimeout(r, 3000));
  
      while (isActive && view === 'calendar') {
        await new Promise((r) => setTimeout(r, 3000));
        if (!isActive) break;
        await fetchData();
      }
    }
  
    keepViewUpdated();
  
    return () => {
      isActive = false; // Cleanup function to stop the loop when component unmounts
      setIsUpdating(false);
    };
  }, [view]);
    
  const startingMonth = calendar?.calendarDates?.length
    ? new Date(calendar.calendarDates[0].eventDate).toLocaleString("default", { month: "long" })
    : "";

  const endingMonth = calendar?.calendarDates?.length
    ? new Date(calendar.calendarDates[calendar.calendarDates.length - 1].eventDate).toLocaleString("default", { month: "long" })
    : "";

  const monthHeading = startingMonth === endingMonth
    ? startingMonth
    : `${startingMonth} - ${endingMonth}`;

  useEffect(() => {
    if (!calendar?.calendarDates?.length) return;
  
    setWeeks(() => {
      const newWeeks = [];
      processCalendarDates(calendar.calendarDates, newWeeks);
      return newWeeks;
    });
  }, [calendar]);
  


  if (loading && !isInitialized) return <p>Loading...</p>;
  if (error) return <p>Error: {error.message}</p>;

  function getGuid() {
    return "10000000-1000-4000-8000-100000000000".replace(/[018]/g, c =>
      (+c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> +c / 4).toString(16)
    );
  }

  const daysOfTheWeek = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];

  function handleClick(cellId) {
    setCalendar((prevData) => ({
      ...prevData,
      calendarDates: prevData.calendarDates.map((day) => ({
        ...day,
        cells: day.cells.map((cell) =>
          cell.id === cellId ? { ...cell, text: "Updated!" } : cell
        ),
      })),
    }));
  }


  // 🔥 Start Editing
  function handleEditStart(cell) {
    setEditingCellId(cell.id);
    setEditText(cell.text);
    setTextColor(cell.color ?? 'black');
    setBackgroundColor(cell.backgroundColor ?? 'white');
  }

  async function handleEditSave(cell) {
    if (!editText.trim() || editText === cell.text) { 
      setEditingCellId(null);
      return; // ✅ No update needed if text is unchanged or empty
    }
  
    try {
      await calendarService.putCell({ ...cell, text: editText });
      setEditingCellId(null);
      fetchData(); // ✅ Refresh after successful update
    } catch (error) {
      console.error("Error updating cell:", error);
    }
  }

  function handleAddStart(calendarDate) {
    setAddCellCalendarDateId(calendarDate.id);
  }

  async function handleAddSave(calendarDate) {
    if (!addText.trim()) { 
      setAddCellCalendarDateId(null);
      return;
    }
  
    try {
      const saveObj = {
        calendarDateId: calendarDate.id,
        text: addText,
        color: null,
        backgroundColor: null,
        isBold: false,
      }
      await calendarService.postCell(saveObj);
      setAddCellCalendarDateId(null);
      setAddText("");
      fetchData();
    } catch (error) {
      console.error("Error updating cell:", error);
    }
  }

  // 🔥 Handle Enter Key Press or Blur to Save
  function handleKeyDown(event, cell) {
    if (event.key === "Enter") {
      handleEditSave(cell);
    }
  }

  function handleOnBlur(cell) {
    handleEditSave(cell);
  }

  function handleAddKeyDown(event, cell) {
    if (event.key === "Enter") {
      handleAddSave(cell);
    }
  }

  function addCell(id) {
    //TO DO add cell
    console.log('addCell', id)
  }

  async function deleteCell(id) {
    try {
      await calendarService.deleteCell(id);
      fetchData();
    } catch (error) {
      console.error("Error deleting cell:", error);
    }
  }

  const tableHeader = daysOfTheWeek.map(day => 
    <th key={day}>{day}</th>
  )

  const columnWidths = [1,2,3,4,5,6,7].map(val => 
    <col key={val} style={{ minWidth: "150px" }} />
  );

  const addBlankDay = (week) => week.push({ id: getGuid() });

  function processCalendarDates(dates, newWeeks) {
    const firstDay = new Date(dates[0].eventDate + "T00:00:00").getDay();
    let week = [];

    for (let i = 0; i < 7; i++) {
      if (i < firstDay) {
        addBlankDay(week);
      } else {
        const currentIndex = i - firstDay;
        if (currentIndex < dates.length) {
          week.push(dates[currentIndex]);
        } else {
          addBlankDay(week);
        }

        if (i === 6) {
          newWeeks.push({ id: getGuid(), data: week });
          if (currentIndex + 1 < dates.length) {
            processCalendarDates(dates.slice(currentIndex + 1), newWeeks);
          }
        }
      }
    }
  }

  const getNumberFromDate = (date) => date ? parseInt(date.substring(8), 10) : null;
  const getMonthFromDate = (date) => date 
    ? new Date(date + 'T00:00:00Z').toLocaleString('default', { month: 'long', timeZone: 'UTC' }) 
    : null;
    
  const dataRows = weeks.map(week => 
    <tr key={week.id} className="row-height">
      {week.data.map((entry) => (
        <td key={entry.id} className="px-1">
          <div className="d-flex justify-content-between w-100">
            <div></div>
            {getNumberFromDate(entry.eventDate) === 1 && <h3 className="text-secondary">{`${getMonthFromDate(entry.eventDate)}`}</h3>}
            <h3 className="text-end">{getNumberFromDate(entry.eventDate)}</h3>
          </div>
          <div className="text-start">
            {entry.cells?.map((cell) => (
              <div key={cell.id} onClick={() => handleEditStart(cell)}>
                {editingCellId === cell.id ? (
                  <div className="d-flex justify-content-between">
                    <input
                      type="text"
                      value={editText}
                      onChange={(e) => setEditText(e.target.value)}
                      onKeyDown={(e) => handleKeyDown(e, cell)}
                      onBlur={() => handleOnBlur(cell)}
                      autoFocus
                    />
                    <div className="ps-1 pt-1" onMouseDown={() => deleteCell(cell.id)}>
                      <FontAwesomeIcon icon={faTrash} className="text-danger fa-lg hover-pointer"  />
                    </div>
                  </div>
                ) : (
                  <div onClick={() => addCell()} className="cell-hover px-1">{cell.text}</div>
                )}
              </div>
            ))}
          </div>
          {entry.eventDate && 
            <div>
              {addCellCalendarDateId === entry.id ? (
                <input
                  type="text"
                  value={addText}
                  onChange={(e) => setAddText(e.target.value)}
                  onKeyDown={(e) => handleAddKeyDown(e, entry)}
                  onBlur={() => handleAddSave(entry)}
                  autoFocus
                />
              ) : !isViewMode && (
                
                <div className="text-center text-primary cell-hover px-1" onClick={() => handleAddStart(entry)}>
                  <FontAwesomeIcon icon={faPlus} onClick={() => addCell(entry.id)} />
                </div>
              )}
            </div>
          }
        </td>
      ))}
    </tr>
  )

  function Tools() {
    return editingCellId && (
      <>
        <div className="d-flex justify-content-end">
          <div className="d-flex">
            <label htmlFor="txColorInput" className="form-label ps-2 pe-1">Text Color</label>
            <span className="color-picker">
              <input 
                type="color"
                id="txColorInput"
                value={textColor}
                onChange={(e) => setTextColor(e.target.value)}
              />
            </span>
          </div>
          <div className="d-flex">
            <label htmlFor="bgColorInput" className="form-label ps-2 pe-1">Background Color</label>
            <span className="color-picker">
              <input 
                type="color"
                id="bgColorInput"
                value={backgroundColor}
                onChange={(e) => setBackgroundColor(e.target.value)}
              />
            </span>
          </div>

        </div>
      </>
    )
  }

  return (
    <>
      <div className="mt-1 d-flex justify-content-between w-100">
        <div className="d-flex justify-content-start" onClick={() => setView("list")}>
          <FontAwesomeIcon icon={faCircleArrowLeft} className="text-primary fa-xl hover-pointer" />
        </div>      
        <div className="d-flex justify-content-end" onClick={() => setIsViewMode(!isViewMode)}>
          <FontAwesomeIcon icon={faEye} className="text-primary fa-xl hover-pointer" />
        </div>
      </div> 
      <h1>{calendar.name}</h1>
      <h3>{monthHeading}</h3>
      {/* <Tools /> */}
      <table className="table table-bordered">
        <colgroup>
          {columnWidths}
        </colgroup>
        <thead>
          <tr>
            {tableHeader}
          </tr>
        </thead>
        <tbody>
          {dataRows}
        </tbody>
      </table>
    </>
  )
}

export default Calendar
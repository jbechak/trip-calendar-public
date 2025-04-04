import '../App.css';
import { useState } from "react";
import calendarService from '../services/calendarService';
import Modal from 'react-bootstrap/Modal';

function CalendarCreate({ show, setShow, setView, userId, setCalendarId }) {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");

  const closeModal = () => setShow(false);


  async function save() {
    if (!name || !startDate || !endDate)
      return;

    try {
      const saveObj = {
        name: name,
        userId: userId,
        description: description,
        startDate: startDate,
        endDate: endDate
      }
      const newCalendar = await calendarService.post(saveObj);
      setCalendarId(newCalendar.id);
      setView("calendar");
      closeModal();
    } catch (error) {
      console.error("Error updating cell:", error);
    }
  }

  const toLocalDate = (dateString) => {
    const date = new Date(dateString);
    return new Date(date.getFullYear(), date.getMonth(), date.getDate()); // Creates a date without time zone shift
  };
  
  return (
    <>
      <Modal show={show} onHide={closeModal}>
        <Modal.Header closeButton>
          <Modal.Title>Create Trip Calendar</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <label htmlFor="nameInput" className="form-label mb-0">Name *</label>
          <input 
            type="text" 
            className="form-control mb-2" 
            id="nameInput"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <label htmlFor="descriptionInput" className="form-label mb-0">Description</label>
          <textarea 
            className="form-control mb-2" 
            id="descriptionInput" 
            rows="3"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          >
          </textarea>
          <label htmlFor="startDateInput" className="form-label mb-0">Start Date *</label>
          <input 
            type="date" 
            className="form-control mb-2" 
            id="startDateInput"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
          />
          <label htmlFor="endDateInput" className="form-label mb-0">End Date *</label>
          <input 
            type="date" 
            className="form-control mb-2" 
            id="endDateInput"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
          />
        </Modal.Body>
        <Modal.Footer>
          <div className="d-flex justify-content-end">
            <button className="btn btn-primary me-md-2" type="button" onClick={save}>Save</button>
            <button className="btn btn-primary" type="button" onClick={closeModal}>Cancel</button>
          </div>
        </Modal.Footer>
      </Modal>
    </>
  )
}

export default CalendarCreate
import './App.css';
import Login from './components/Authorization/Login';
import Calendar from './components/Calendar';
import CalendarList from './components/CalendarList';
import { useState } from 'react';


function App() {
  const [view, setView] = useState("list");
  const [calendarId, setCalendarId] = useState("");
  const [user, setUser] = useState({});
  const [token, setToken] = useState("");

  return (
    <>
      {user.id == null ? (<Login setToken={setToken} setUser={setUser} />) 
      : (<div>
          {view === "list" && <CalendarList setView={setView} view={view} userId={user.id} setCalendarId={setCalendarId} />}
          {view === "calendar" && <Calendar setView={setView} view={view} calendarId={calendarId} />}
        </div>)}
    </>
  )
}

export default App
import '../../App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEyeSlash } from '@fortawesome/free-solid-svg-icons';
import { useState } from "react";

function PasswordInput({ password, setPassword }) {
  const [passwordInputType, setPasswordInputType] = useState('password');

  function getGuid() {
    return Math.random().toString(36).substring(2, 15) +
    Math.random().toString(36).substring(2, 15);
  }

  function toggleInputType() {
    setPasswordInputType(passwordInputType === 'password' ? 'text' : 'password');
  }

  const inputId = getGuid();

  return (
    <div className="form-group mb-2">
      <label htmlFor={inputId} className="me-1 label">Password</label>
      <div className="input-group input-group-sm">			
        <input 
          id={inputId}
          name="passwordInput"
          type={passwordInputType}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          maxLength="50"
          className="form-control"
          aria-describedby="basic-addon2"
        />
        <div className="input-group-append">
          <span className="input-group-text px-1 h-100" onClick={() => toggleInputType()}>
            <FontAwesomeIcon icon={faEyeSlash} className="hover-pointer"/>
          </span>
        </div>
      </div>
    </div>
  )
}

export default PasswordInput
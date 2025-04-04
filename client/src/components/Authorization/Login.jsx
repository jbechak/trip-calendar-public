import '../../App.css';
import Register from './Register';
import PasswordInput from './PasswordInput';
import authenticationService from '../../services/authenticationService';
import { useState } from "react";

function Login({ setToken, setUser }) {
  const [isRegistering, setIsRegistering] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [email, setEmail] = useState('');

  async function login() {
    try {
      const loginObj = {
        username: username,
        password: password,
      }
      const result = (await authenticationService.login(loginObj)).data;
      const user = result.user;
      setToken(result.token);
      setUser(user);
    } catch(error) {
      console.log('invalid login', error)
    }
  }

  return (
    <>
    <div className="mt-3 d-flex flex-column align-items-start">
      {isRegistering ? (
        <Register 
          setIsRegistering={setIsRegistering}
          username={username} setUsername={setUsername}
          password={password} setPassword={setPassword}
          confirmPassword={confirmPassword} setConfirmPassword={setConfirmPassword}
          email={email} setEmail={setEmail}
        />
      ) : (
        <div>
          <div className="form-group mb-2">
            <label htmlFor="usernameInput" className="me-1 label">Username</label>
            <input 
              id="usernameInput"
              name="usernameInput"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              maxLength="50"
              className="form-control form-control-sm mb-1"
            />
          </div>
          <PasswordInput password={password} setPassword={setPassword} />
          <div className="text-primary text-start mb-2 hover-pointer" onClick={() => setIsRegistering(true)}>Need an account?</div>
          <div className="d-flex flex-column align-items-start w-100 mb-1">
            <div className="button-row">
              <button
                type="button" 
                className="btn btn-primary mb-2"
                onClick={() => login()}
              >
                Log In
              </button>
              {/* <button
                type="button" 
                className="btn btn-secondary mb-2 mx-1"
              >
                Close
              </button> */}
            </div>
          </div>
        </div>)}
      </div>
    </>
  )
}

export default Login
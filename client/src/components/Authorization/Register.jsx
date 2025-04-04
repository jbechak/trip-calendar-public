import '../../App.css';
import PasswordInput from './PasswordInput';
import authenticationService from '../../services/authenticationService';

function Register({ 
  setIsRegistering, 
  username, 
  setUsername, 
  password, 
  setPassword, 
  confirmPassword, 
  setConfirmPassword, 
  email, 
  setEmail 
}) {

  async function createAccount() {
    try {
      const saveObj = {
        username: username,
        password: password,
        confirmPassword: confirmPassword,
        email: email,
      }
      await authenticationService.register(saveObj);
      setIsRegistering(false);
    } catch(error) {
      console.log('registatration failed', error);
    }
  }

  return (
    <>
      <div className="form-group mb-2">
        <label htmlFor="usernameInput" className="me-1 label">Username *</label>
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
      <PasswordInput password={confirmPassword} setPassword={setConfirmPassword} />
      <div className="form-group mb-2">
        <label htmlFor="emailInput" className="me-1 label">Email *</label>
        <input 
          id="emailInput"
          name="emailInput"
          type="text"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          maxLength="100"
          className="form-control form-control-sm mb-1"
          required
        />
      </div>
      <div className="d-flex flex-column align-items-start border-top pt-2 mt-3 w-100]">
        <div className="button-row">
          <button
            type="button" 
            className="btn btn-primary mb-2"
            onClick={() => createAccount()}
          >
            Create Account
          </button>
          <button
            type="button" 
            className="btn btn-secondary mb-2 ms-1"
            onClick={() => setIsRegistering(false)}
          >
            Back to Login
          </button>
        </div>
      </div>
    </>
  )
}

export default Register
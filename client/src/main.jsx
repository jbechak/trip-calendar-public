import { createRoot } from "react-dom/client";
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App.jsx'
import './App.css';

const root = createRoot(document.getElementById("root"));

function Presentation() {
  return (
    <>
      <ul className="list-class">
        <li>Pricing</li>
        <li>About</li>
        <li>Contact</li>
      </ul>
      <h1>Fun facts about React</h1>
      <li>Was first released in 2013</li>
      <li>Was originally created by Jordan Walke</li>
      <li>Has well over 100k stars on GitHub</li>
      <li>Is maintained by Meta</li>
      <li>Powers thousands of enterprise apps, including mobile apps</li>
    </>
  )
}

root.render(
  <App />
  // <main>
  //   <img src="src/assets/react.svg" width="40px" />
  //   <Presentation />
  // </main>
)


import './App.css'
import picture from "./assets/react.svg"

function Stevie(props) {
  const { name, num, isCat } = props;

  const ninjaTurtles = [
    <h2>Donatello</h2>,
    <h2>Michaelangelo</h2>,
    <h2>Raphael</h2>,
    <h2>Leonardo</h2>
  ]

  return (
    <>
      <div>
        <h1>BIG MEOW {name} {num} {isCat}</h1>
        <img src={picture} />
        {ninjaTurtles}
      </div>

    </>
  )
}

export default Stevie

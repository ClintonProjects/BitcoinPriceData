import './PaymentMethods.css';
import bainace from './images/Bainace.jpg';
import btc from './images/BTC.png';
import coinbase from './images/coinbase.png';
import mastercard from './images/mastercard.png';
import visa from './images/visa.png';
import zelle from './images/zelle.png';
import LTC from './images/LTC.png';
import USDT from './images/usdt.png';

//This was never actually implemented into the final project, since we dicided it was not to take donation for multiple reasons.

function App() {
    return (
            <div class="Paymentmethods">
             <img src={btc} alt="btc" class="methods"/>
            <img src={zelle} alt="zelle" class="methods"/>
            <img src={LTC} alt="zelle" class="methods"/>
            <img src={mastercard} class="methods"/>
            <img src={visa} alt="btc" class="methods"/>
            <img src={coinbase} alt="btc" class="methods"/>
            <img src={bainace} alt="btc" class="methods"/>
            <img src={USDT} alt="btc" class="methods"/>
            </div>
    );
}

export default App;

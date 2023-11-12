import './footer2.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrademark } from '@fortawesome/free-solid-svg-icons';

function App() {
    return (
            <div class="footer2">
                <div class="footercopyright2">
                    All rights reserved to BTCinfo LTD @ 2021.<FontAwesomeIcon icon={faTrademark} />
                </div>
            </div>
    );
}

export default App;

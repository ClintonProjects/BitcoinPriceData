import './footer.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrademark } from '@fortawesome/free-solid-svg-icons';

function App() {
    return (
            <div class="footer">
                <div class="footercopyright">
                    All rights reserved to BTCinfo LTD @ 2021.<FontAwesomeIcon icon={faTrademark} />
                </div>
            </div>
    );
}

export default App;

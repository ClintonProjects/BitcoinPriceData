import './HeaderImage.css';
import coin from './Images/coin.png';
import lock from './Images/lock.png';
import stopwatch from './Images/stopwatch.png';
import hovercoin from './Images/coin.png';

//Display the page with icon and text on the hope page.
function App() {
    return (
        <div className="TextSections">
            <div class="textdiv">
                <div class="spacer" />
                <div class="stopwatchspace">
                    <div class="Image">
                        <img src={stopwatch} alt="HeadImage" class="checkmarkimage" />
                    </div>
                    <div class="text">
                        <h1>Fastest Realtime Data</h1>
                        <div class="paragraph">
                            <p1>We get our data from very fast sources at rapid speed.</p1>
                        </div>
                    </div>
                </div>
                <div class="spacerbetweenimages" />
                <div class="stopwatchspace">
                    <div class="Image">
                        <img src={coin} alt="HeadImage" class="checkmarkimage" hoverSrc={hovercoin} />
                    </div>
                    <div class="text">
                        <h1>Multiple Coins Data</h1>
                        <div class="paragraph">
                            <p1>We get data our data for mutiple coins and currency so you can get best over view of the market.</p1>
                        </div>
                    </div>
                </div>
                <div class="spacerbetweenimages" />
                <div class="stopwatchspace center">
                    <div class="Image">
                        <img src={lock} alt="HeadImage" class="checkmarkimage" />
                    </div>
                    <div class="text">
                        <h1>Safe &#38; Secure</h1>
                        <div class="paragraph">
                            <p1>We are the safest place to get your data, as we our a non-profit!</p1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
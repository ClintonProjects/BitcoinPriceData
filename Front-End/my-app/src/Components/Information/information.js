import React, { Component } from 'react';
import './information.css';

class Information extends Component {

    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <div class="infoColour no-gutters">
                <div class="container py-2 infoColour">
                    <div class="col-12  no-gutters py-5">
                        <h3 class=" py-1">How do we get are Data?</h3>
                        <h6 class=" py-1">
                            We get are data directly from the exchanges, each website has something called an API (Application programming interface), which is how we get the data from, for more information about these click link belows,
                            <br class=" py-1" />
                            What is an API? https://www.ibm.com/cloud/learn/api
                            <br />
                            Coinbase API: https://commerce.coinbase.com/docs/api/
                            <br />
                            Bitmex API: https://www.bitmex.com/app/apiOverview
                            <br />
                            Bainace API: https://www.binance.com/en/binance-api

                        </h6>
                        <h3 class=" py-1">What exchanges does your data come from?</h3>
                        <h6 class=" py-1">
                            &#x2022; Bitmex
                            <br />
                            &#x2022; Coinbase
                            <br />
                            &#x2022;  Bainace
                        </h6>
                        <h3 class=" py-1">What coin does your data come from?</h3>
                        <h6 class=" py-1">

                            &#x2022; BTC (Bitcoin)
                            <br />
                            &#x2022; LTC (Litecoin)
                            <br />
                            &#x2022;  ETH (Ethereum)
                        </h6>
                        <h3 class=" py-1">What currencies do you support?</h3>
                        <h6 class=" py-1">
                            &#x2022; USD
                            <br />
                            &#x2022; EURO
                            <br />
                            &#x2022; GBP (Great British Pounds)
                        </h6>

                        <h3 class=" py-1">How does your buy/sell bar work?</h3>
                        <h6 class=" py-1">
                            Our buy/sell works by giving an overall buy and sell in market and give percentage of buys and sells based off that (so all coins and currencies)
                        </h6>

                        <h3 class=" py-1">Why does all your data only work on 1 min timeframe</h3>
                        <h6 class=" py-1">
                            due to small budget of the project, we can only have data base which store certain amount of tranactions and because of amount of data we get we have had to limit it to min timeframe
                        </h6>

                        <h3 class=" py-1">Do you take donations?</h3>
                        <h6 class=" py-1">
                            We currently don't accpet donations.
                        </h6>

                        <h3 class=" py-1">Do plan on implementing more coins in the future</h3>
                        <h6 class=" py-1">
                            Yes we have plans to implementing all the top 10 larges coins, besides stables coins.
                        </h6>

                        <h3 class=" py-1">Has the data that is displayed on site been proven to help traders?</h3>
                        <h6 class=" py-1">
                            Yes, we have tested to prove the buy / sell bar and other components of the site has proven to corlate with the market.
                        </h6>
                    </div>
                </div>
            </div >
        )
    }
}


export default Information;
import React, { Component, PropTypes } from 'react';
import './BTCRealTimePrices.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCog, faArrowDown, faArrowUp } from '@fortawesome/free-solid-svg-icons';
import coinbaseLogo from './Coinbase.png';
import bainceLogo from './binance.png';
import bitmexLogo from './bitmex-logo.png';
import './ExchangeActivePrices.css';
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import coindataLink from '../../Funuctions/DBconnects/coindata.js'

var stompClient;
var result;
class BTCRealTimePrices extends Component {
    constructor(props) {
        super(props);
        this.state = {
            items: null,
            currency: "GBP".toUpperCase(),
        };
        this.priceColour = this.priceColour.bind(this);
        this.data = this.data.bind(this);
        this.displayResult = this.displayResult.bind(this);
        this.displayValue = this.displayValue.bind(this);
        this.currencySettings = this.currencySettings.bind(this);
    }

    //Connects to websock which get data for the BTC prices. This gets the table data.
    componentDidMount() {
        this.connect();
        this.interval = setInterval(() => this.getData(), 100);
    }

    //starts the websocket and set it to the state.
    getData() {
        try {
            this.currencySettings();
            this.setState({ items: JSON.parse(result) });
        } catch (err) {   //should never be called, just stop the console from being spammed if backend not on 
        }
    }


    //Connects to websock which get data for the return the moving averages BTC prices. This gets the table data.
    connect = () => {
        const socket = new SockJS(coindataLink + "/simulator");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe("/endpoint/getExchangeData", function (greeting) {
                if (typeof greeting.body !== undefined) {
                    result = greeting.body;
                }
            });
        });
    };


    //handles the data and goes through it, so in backend the way data is displays and get it from the hashmap
    displayResult(exchange, cypto) {
        try {
            if (this.state.items[exchange.trim().toUpperCase() + "/" + cypto.trim().toUpperCase() + "/" + this.state.currency.trim().toUpperCase()]) {
                for (var i in this.state.items) {
                    if (i.trim() == exchange.trim().toUpperCase() + "/" + cypto.trim().toUpperCase() + "/" + this.state.currency.trim().toUpperCase()) {
                        if (this.state.items[i].currentPrice > 0)
                            return this.state.items[i].currentPrice.toFixed(2);
                    }
                }
            }
        } catch (e) {
            
        }
        return "Not Enough Data";
    }


    //Shows the price changes in past 1m, this is not actually used (left for marks)
    priceColour() {
        if (this.state.items.priceChange >= 0) {
            return (<h2 className="greenTextColour">${(this.state.items.currentPrice).toFixed(2)}</h2>)
        } else if (this.state.items.priceChange < 0) {
            return (<h2 className="redTextColour">${(this.state.items.currentPrice).toFixed(2)}</h2>)
        }

    }

    //Shows the price changes in past 1m, this is not actually used (left for marks)f
    percentageColour() {
        if (this.state.items.priceChange >= 0) {
            return (<h2 className="greenTextColour">{this.state.items.priceChange.toFixed(2)}% <FontAwesomeIcon icon={faArrowUp} /></h2>)
        } else if (this.state.items.priceChange < 0) {
            return (<h2 className="redTextColour">{this.state.items.priceChange.toFixed(2)}% <FontAwesomeIcon icon={faArrowDown} /></h2>)
        }
    }

    //Display currency symbol next tot he data.
    displayValue(currency) {
        if (currency.toUpperCase() == "usd".toUpperCase())
            return "$";
        if (currency.toUpperCase() == "eur".toUpperCase())
            return "€";
        if (currency.toUpperCase() == "gbp".toUpperCase())
            return "£";
    }

    //Gets the currency from the local storage.
    currencySettings() {
        try {
            if (localStorage.getItem('currency') !== null) {
                this.setState({ currency: localStorage.getItem('currency').toUpperCase() });
                return
            }
            this.setState({ currency: "USD" });
        } catch (e) { }
    }

    //Show the currency and price of the coins
    data(image, exchange, cypto) {
        return (
            <div class="row">
                {/* Blank Space, easier then using margin */}
                <div class="col-2" />
                <div class="col-2">
                    <p class="h6">
                        <img src={image} class="cbImage" />&ensp;{exchange == "All" ? "Whole market" : exchange}
                    </p>
                </div>
                <div class="col-2 py-1">
                    <p class="h6">
                        BTC: {this.displayValue(this.state.currency)} {this.displayResult(exchange, "BTC")}
                    </p>
                </div>
                <div class="col-2 py-1">
                    <p class="h6">
                        ETH: {this.displayValue(this.state.currency)} {this.displayResult(exchange, "ETH")}
                    </p>
                </div>
                <div class="col-2 py-1">
                    <p class="h6">
                        LTC: {this.displayValue(this.state.currency)} {this.displayResult(exchange, "LTC")}
                    </p>
                </div>
            </div>
        )
    }

    render() {
        return (
            <div class="activeExchanges no-gutters">
                <div class="container">
                    <div class=" no-gutters">
                        <p class="h6 text-center">
                            1 min moving averages
                        </p>
                    </div>
                    <div class="row py-2  no-gutters">
                        {this.data(null, "All", "BTC")}
                        {this.data(coinbaseLogo, "COINBASE PRO", "BTC")}
                        {this.data(bainceLogo, "BINANCE", "BTC")}
                        {this.data(bitmexLogo, "BITMEX", "BTC")}
                    </div>
                </div>
                <p class="h6 text-center">
                    If " Not Enough Data" mean we don't have enough data to display for this coin at this time
                </p>
                <p class="h6 text-center">
                    in certain cases the conversation may not be on the exchange (Example Bitmex (Euro/LTC, GBP/BTC etc))
                </p>
            </div>
        );
    }
}

export default BTCRealTimePrices;

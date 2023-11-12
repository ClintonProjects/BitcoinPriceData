import React, { Component, PropTypes } from 'react';
import './BTCPrices.css';
import btc from './bitcoin-icon.png';
import eth from './eth.png';
import ltc from './ltc.png';
import coinbase from './coinbase.png';
import binance from './binance.png';
import bitmex from './bitmex.png';
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { Dropdown } from 'react-bootstrap'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import coindataLink from '../../Funuctions/DBconnects/coindata.js'


var stompClient;
var result;
export default class BTCPrices extends Component {
    constructor(props) {
        super(props);
        this.state = {
            items: [{}],
            result: null,
            btcFilter: false,
            ethFilter: false,
            ltcFilter: false,
            exchange: "",
            cointype: "all".toUpperCase(),
            filterAmountByCurrency: 0,
            filterAmountByCoinSize: 0,
            currencyConvert: "all",
            BSstate: "NA"
        }
        this.BoxColour = this.BoxColour.bind(this);
        this.replace = this.replace.bind(this);
        this.currSymbol = this.currSymbol.bind(this);
        this.setCurrency = this.setCurrency.bind(this);
        this.setExchange = this.setExchange.bind(this);
        this.setCoinFilter = this.setCoinFilter.bind(this);
        this.setCurrencyConver = this.setCurrencyConver.bind(this);
        this.setAmoutFilterByCyptoSize = this.setAmoutFilterByCyptoSize.bind(this);
        this.BuySell = this.BuySell.bind(this);
    }

    //Connects to websock which get data for the BTC prices. This gets the table data.
    connect = () => {
        const socket = new SockJS(coindataLink + "/simulator");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe("/endpoint/greeting", function (greeting) {
                if (typeof greeting.body !== undefined) {
                    result = greeting.body;
                }
            });
        });
    };

    //Allows the user to filter weather they want to only see bitcoin, litecoin or eth
    setCoinFilter(flipCoin) {
        //displays only these coins
        if (flipCoin == "BTC") {
            this.setState({ btcFilter: true });
            this.setState({ ethFilter: false });
            this.setState({ ltcFilter: false });
        }
        else if (flipCoin == "ETH") {
            this.setState({ btcFilter: false });
            this.setState({ ethFilter: true });
            this.setState({ ltcFilter: false });
        }
        else if (flipCoin == "LTC") {
            this.setState({ btcFilter: false });
            this.setState({ ethFilter: false });
            this.setState({ ltcFilter: true });
        }
        else if (flipCoin.toUpperCase() == "All".toUpperCase()) {
            this.setState({ btcFilter: false });
            this.setState({ ethFilter: false });
            this.setState({ ltcFilter: false });
        }

        //This display alert
        toast.info('CoinFilter set to ' + flipCoin, {
            position: "bottom-center",
            autoClose: 2500,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: false,
        });
    }

    //Sets the exchange (basically like setter in java)
    setExchange(exchange) {
        this.setState({ exchange: exchange.toUpperCase() });

        toast.info('Exchange set to ' + exchange, {
            position: "bottom-center",
            autoClose: 2500,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: false,
        });
    }

    //Sets the concery conversation (basically like setter in java)
    setCurrencyConver(currency) {
        if (currency.toUpperCase() != "all".toUpperCase())
            localStorage.setItem('currency', currency.toUpperCase());
        else
            localStorage.setItem('currency', "USD".toUpperCase());

        this.setState({ currencyConvert: currency.toUpperCase() });

        toast.info('Currency converter set to ' + currency.toUpperCase(), {
            position: "bottom-center",
            autoClose: 2500,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: false,
        });
    }

    //Sets the concery so show only trades in this market (example usd, etc)
    setCurrency(currency) {
        this.setState({ cointype: currency.toUpperCase() });
        toast.info('Currency set to ' + currency.toUpperCase(), {
            position: "bottom-center",
            autoClose: 2500,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: false,
        });
    }

    //Basically setter for setter the amount of coin a user can see, so for example 1 btc or 0.5 btc
    setAmoutFilterByCyptoSize() {
        try {
            if (document.getElementById('coinValue').value != '') {
                this.setState({ filterAmountByCurrency: document.getElementById('coinValue').value });
                toast.info('Minimum coin value set to ' + this.state.filterAmountByCurrency, {
                    position: "bottom-center",
                    autoClose: 2500,
                    hideProgressBar: true,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: false,
                });
            }
        } catch (e) {
        }

        try {
            if (document.getElementById('coinSize').value != '') {
                this.setState({ filterAmountByCoinSize: document.getElementById('coinSize').value });
                toast.info('Minimum coin size set to ' + this.state.filterAmountByCoinSize, {
                    position: "bottom-center",
                    autoClose: 2500,
                    hideProgressBar: true,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: false,
                });
            }
        } catch (e) {
            // boolB = false;
        }

    }

    //Removes an item from an array.
    removeFromArray = function (array, value) {
        var idx = array.indexOf(value);
        if (idx !== -1) {
            array.splice(idx, 1);
        }
        return array;
    }

    //handles the websocket and updates every 100m.
    componentDidMount() {
        this.connect();
        this.interval = setInterval(() => this.getData(), 100);
        return;
    }

    //Set the data to the data we are getting from the websocket, and then handles all filters which we have set.
    getData() {
        try {
            this.setState({ items: JSON.parse(result) })
            if (this.state.filterAmountByCoinSize > 0)
                this.setState({ items: this.state.items.filter(i => i.size > this.state.filterAmountByCoinSize) })
            if (this.state.filterAmountByCurrency > 0)
                this.setState({ items: this.state.items.filter(i => i.size * i.price > this.state.filterAmountByCurrency) })
            if (this.state.btcFilter)
                this.setState({ items: this.state.items.filter(i => i.cypto.toUpperCase() == "BTC") })
            if (this.state.ltcFilter)
                this.setState({ items: this.state.items.filter(i => i.cypto.toUpperCase() == "LTC") })
            if (this.state.ethFilter)
                this.setState({ items: this.state.items.filter(i => i.cypto.toUpperCase() == "ETH") })
            if (this.state.cointype == "USD".toUpperCase())
                this.setState({ items: this.state.items.filter(i => i.currency.toUpperCase() == "USD") })
            if (this.state.cointype == "EUR".toUpperCase())
                this.setState({ items: this.state.items.filter(i => i.currency.toUpperCase() == "EUR") })
            if (this.state.cointype == "GBP".toUpperCase())
                this.setState({ items: this.state.items.filter(i => i.currency.toUpperCase() == "GBP") })
            if (this.state.exchange == "Bitmex".toUpperCase())
                this.setState({ items: this.state.items.filter(i => i.exchange.toUpperCase() == "Bitmex".toUpperCase()) })
            if (this.state.exchange == "Binance".toUpperCase())
                this.setState({ items: this.state.items.filter(i => i.exchange.toUpperCase() == "Binance".toUpperCase()) })
            if (this.state.exchange.trim() == "Coinbase Pro".trim().toUpperCase())
                this.setState({ items: this.state.items.filter(i => i.exchange.trim().toUpperCase() == "Coinbase Pro".trim().toUpperCase()) })
            if (this.state.BSstate == "BUY".toUpperCase())
                this.setState({ items: this.state.items.filter(i => i.side.toUpperCase() == "BUY".toUpperCase()) })
            if (this.state.BSstate == "SELL".toUpperCase())
                this.setState({ items: this.state.items.filter(i => i.side.toUpperCase() == "SELL".toUpperCase()) })
        } catch (err) {   //should never be called, just stop the console from being spammed if backend not on 
        }
    }

    currency() {
        //displays only currencies in these types
        if (this.this.state.cointype == "usd") this.setState({ cointype: "usd" });
        else if (this.this.state.cointype == "eur") this.setState({ cointype: "eur" });
        else if (this.this.state.cointype == "gbp") this.setState({ cointype: "gbp" });
        else this.setState({ cointype: "all" });
    }

    covertCurrency(obj) {
        //converts all entries to another currenies, this needs to be added to state, it's not fully implemeneted.
        if (this.state.currencyConvert.toUpperCase() == "usd".toUpperCase()) return obj.priceInUSD;
        if (this.state.currencyConvert.toUpperCase() == "eur".toUpperCase()) return obj.priceInEUR;
        if (this.state.currencyConvert.toUpperCase() == "gbp".toUpperCase()) return obj.priceInGBP;
        return this.currSymbol(obj) + obj.price;
    }


    displayValue(obj) {
        //displays the currency conversation (API handled in backend)
        if (this.state.currencyConvert.toUpperCase() == "usd".toUpperCase()) return "$" + (obj.size * obj.priceInUSD.substring(1)).toFixed(2);
        if (this.state.currencyConvert.toUpperCase() == "eur".toUpperCase()) return "€" + (obj.size * obj.priceInEUR.substring(1)).toFixed(2);
        if (this.state.currencyConvert.toUpperCase() == "gbp".toUpperCase()) return "£" + (obj.size * obj.priceInGBP.substring(1)).toFixed(2);
        return this.currSymbol(obj) + (obj.size * obj.price).toFixed(2);
    }


    currSymbol(obj) {
        if (obj.currency == "gbp".toUpperCase())
            return "£";
        if (obj.currency == "eur".toUpperCase())
            return "€";
        if (obj.currency == "usd".toUpperCase())
            return "$";
    }

    //changes currency conversation symbol so it will display currency which the user want set.
    replace(inc) {
        if (this.state.items[inc].cypto.toUpperCase() == "BTC")
            return (<p1><img src={btc} alt="HeadImage" className="btcImage" /></p1>)
        else if (this.state.items[inc].cypto.toUpperCase() == "ETH")
            return (<p1><img src={eth} alt="HeadImage" className="btcImage" /></p1>)
        else if (this.state.items[inc].cypto.toUpperCase() == "LTC")
            return (<p1><img src={ltc} alt="HeadImage" classNamSe="btcImage" /></p1>)
    }

    //Displays the symbol of cypto which is being displayed only (BTC PICTURE) (LTC PICTURE)
    replaceExchange(inc) {
        if (this.state.items[inc].exchange.trim().toUpperCase() == "COINBASE PRO".trim())
            return (<p1><img src={coinbase} alt="HeadImage" className="btcImage" />Coinbase</p1>)
        if (this.state.items[inc].exchange.toUpperCase() == "binance".toUpperCase())
            return (<p1><img src={binance} alt="HeadImage" className="btcImage" />Binance</p1>)
        if (this.state.items[inc].exchange.toUpperCase() == "bitmex".toUpperCase())
            return (<p1><img src={bitmex} alt="HeadImage" className="btcImage" />Bitmex</p1>)
    }

    //Displays the exchanges (COINABSE PRO PICTURE) (BITMEX PICTURE)
    BuySell(inc) {
        this.setState({ BSstate: inc });
        toast.info('Buy/Sell Filter set to ' + inc, {
            position: "bottom-center",
            autoClose: 2500,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: false,
        });
    }




    //Displays the table data. 
    BoxColour(inc) {
        try {
            return (
                <div class="row text-center">
                    <div class="col-3">
                        <p class="h6">{this.replace(inc)} {this.state.items[inc].symbol.toUpperCase()} </p>
                    </div>
                    <div class="col-1">
                        <p class="h6">{this.replaceExchange(inc)} </p>
                    </div>
                    <div class="col-2">
                        <p class="h6">{this.displayValue(this.state.items[inc])} </p>
                    </div>
                    <div class="col-1">
                        <p class="h6">{this.state.items[inc].size} </p>
                    </div>
                    <div class="col-2">
                        <p class="h6">{this.covertCurrency(this.state.items[inc])} </p>
                    </div>
                    <div class="col-1">
                        {this.state.items[inc].side.toUpperCase() === "BUY".toUpperCase() ?
                            <p class="h6 text-success">{this.state.items[inc].side.toUpperCase()}  </p>
                            : <p class="h6 text-danger">{this.state.items[inc].side.toUpperCase()} </p>}
                    </div>
                    <div class="col-1">
                        <p class="h6">{
                            // new Date(this.state.items[inc].timestamp).getUTCDate() + "/" + new Date(this.state.items[inc].timestamp).getMonth() + "/" + new Date(this.state.items[inc].timestamp).getFullYear() + " " +
                            new Date(this.state.items[inc].timestamp).getHours() + ":" + new Date(this.state.items[inc].timestamp).getMinutes() + ":" + new Date(this.state.items[inc].timestamp).getSeconds()}</p>
                    </div>
                </div>
            )
        } catch (error) {
            return (
                <div class="row text-center">
                    <div class="col-3">
                        <p class="h6">{"Loading..."}</p>
                    </div>
                    <div class="col-1 ">
                        <p class="h6">{"Loading..."}</p>
                    </div>
                    <div class="col-2">
                        <p class="h6">{"Loading..."}</p>
                    </div>
                    <div class="col-1">
                        <p class="h6">{"Loading..."}</p>
                    </div>
                    <div class="col-2">
                        <p class="h6">{"Loading..."}</p>
                    </div>
                    <div class="col-1">
                        <p class="h6">{"Loading..."}</p>
                    </div>
                    <div class="col-1">
                        <p class="h6">{"Loading..."}</p>
                    </div>
                </div>
            )
        }
    }

    render() {
        return (
            <div class="bgColour py-3 no-gutters">
                <div class="container  no-gutters">
                    <p class="h6 text-center">
                        Real time sales of market
                    </p>
                    <div class="row text-center py-1">
                        <div class="col-3">
                            <p class="h6">Symbol</p>
                        </div>
                        <div class="col-1">
                            <p class="h6">Exchange</p>
                        </div>
                        <div class="col-2">
                            <p class="h6">Value of Tranaction</p>
                        </div>
                        <div class="col-1">
                            <p class="h6">Size of Coin</p>
                        </div>
                        <div class="col-2">
                            <p class="h6">Value</p>
                        </div>
                        <div class="col-1">
                            <p class="h6">Side</p>
                        </div>
                        <div class="col-1">
                            <p class="h6">Time</p>
                        </div>
                    </div>

                    {this.BoxColour(0)}
                    {this.BoxColour(1)}
                    {this.BoxColour(2)}
                    {this.BoxColour(3)}
                    {this.BoxColour(4)}
                    {this.BoxColour(5)}
                    {this.BoxColour(6)}
                    {this.BoxColour(7)}
                    {this.BoxColour(8)}
                    {this.BoxColour(9)}
                    {this.BoxColour(10)}


                    <div class="text-center py-3  no-gutters">
                        <Dropdown className="d-inline mx-2">
                            <Dropdown.Toggle
                                id="dropdown-autoclose-true"
                                variant="secondary"
                                menuVariant="dark">
                                Show trades in market only
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                <Dropdown.Item onClick={() => this.setCurrency("all")}>All</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCurrency("usd")}>USA (USDT,USD, ETC)</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCurrency("eur")}>EUR</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCurrency("gbp")}>GBP</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown className="d-inline mx-2">
                            <Dropdown.Toggle
                                id="dropdown-autoclose-true"
                                variant="secondary"
                                menuVariant="dark">
                                Currency Convert
                            </Dropdown.Toggle>
                            <Dropdown.Menu>
                                <Dropdown.Item onClick={() => this.setCurrencyConver("all")}>All Currency</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCurrencyConver("usd")}>USD</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCurrencyConver("eur")}>EUR</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCurrencyConver("gbp")}>GBP</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown className="d-inline mx-2" >
                            <Dropdown.Toggle
                                variant="secondary"
                                menuVariant="dark"
                                id="dropdown-autoclose-true">
                                Exchange Filter
                            </Dropdown.Toggle>
                            <Dropdown.Menu >
                                <Dropdown.Item onClick={() => this.setExchange("ALL")}>No Filter</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setExchange("Coinbase Pro")}><p1><img src={coinbase} alt="HeadImage" className="btcImage" />Coinbase</p1></Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setExchange("Binance")}><p1><img src={binance} alt="HeadImage" className="btcImage" />Binance</p1></Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setExchange("Bitmex")}><p1><img src={bitmex} alt="HeadImage" className="btcImage" />Bitmex</p1></Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown className="d-inline mx-2" >
                            <Dropdown.Toggle
                                variant="secondary"
                                menuVariant="dark"
                                id="dropdown-autoclose-true">
                                Cypto Filter
                            </Dropdown.Toggle>
                            <Dropdown.Menu >
                                <Dropdown.Item onClick={() => this.setCoinFilter("ALL")}>No Filter</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCoinFilter("BTC")}><p1>BTC</p1></Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCoinFilter("LTC")}><p1>LTC</p1></Dropdown.Item>
                                <Dropdown.Item onClick={() => this.setCoinFilter("ETH")}><p1>ETH</p1></Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>

                        <Dropdown className="d-inline mx-2" >
                            <Dropdown.Toggle
                                variant="secondary"
                                menuVariant="dark"
                                id="dropdown-autoclose-true">
                                Buy/Sell Filter
                            </Dropdown.Toggle>
                            <Dropdown.Menu >
                                <Dropdown.Item onClick={() => this.BuySell("ALL")}>No Filter</Dropdown.Item>
                                <Dropdown.Item onClick={() => this.BuySell("BUY")}><p1>Buy</p1></Dropdown.Item>
                                <Dropdown.Item onClick={() => this.BuySell("SELL")}><p1>Sell</p1></Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                    </div>

                    <div class="row no-gutters">
                        <div class="col-4" />
                        <div class="col-2 text-center">
                            <div class="form-outline">
                                <label class="form-label" for="form12">Filter currency value over:</label>
                                <input
                                    type="number"
                                    id="coinValue"
                                    class="form-control"
                                    placeholder="0"
                                />
                            </div>
                        </div>

                        <div class="col-2 text-center .mt-1 ">
                            <div class="form-outline">
                                <label
                                    class="form-label"
                                    for="form2"
                                >Filter coin size over:</label>
                                <input type="number" id="coinSize" class="form-control" placeholder="0" />
                            </div>
                        </div>
                        <div class="col-12 text-center  mt-3">
                            <button type="button" class="btn btn-secondary text" onClick={() => this.setAmoutFilterByCyptoSize()}>Submit</button>
                        </div>
                    </div>
                </div>
                <ToastContainer />
            </div>
        );
    }
}



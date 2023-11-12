import React, { Component } from 'react';
import '../App.css';
import TVGraph from '../Components/TVGraph/TVGraph.js';
import Nav2 from '../Components/NavigationBar/bar.js';
import Footer from '../Components/Footer/footer.js';
import BTCPrices from '../Components/BTCPrices/BTCPrices.js';
import Banner from '../Components/BannerSpot/Banner.js';
import BTCRealTimePrices from '../Components/BTCRealTimePrices/BTCRealTimePrices.js';
import BuySellBar from '../Components/BuySellBar/BuySellBar';
import server from "../Funuctions/DBconnects/Server.js"

class PostloginHomepage extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    //Error handling to do with github deployment
    // async componentDidMount() {
    //     //This is an error check (same thing in post login)
    //     if (localStorage.getItem('SessionId') == null)
    //         window.location.href = server;
    // }

    // displayes the user post-login home page (the page with the stats)
    render() {
        return (
            <div className="App">
                <Nav2 currency={this.props.currency} />
                <TVGraph />
                <BuySellBar />
                <BTCRealTimePrices />
                <BTCPrices />
                <Banner />
                <Footer />
            </div>
        );
    }
}

export default PostloginHomepage;

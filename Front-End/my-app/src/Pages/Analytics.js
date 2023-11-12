import React, { Component } from 'react';
import '../App.css';
import Nav2 from '../Components/NavigationBar/bar.js';
import Footer from '../Components/Footer/footer.js';
import Map from '../Components/Analytics/Analytics.js';
import axios, * as others from 'axios';
import AnalyticsLink from "../Funuctions/DBconnects/Anaylitics.js"


class AnalyticsPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    //redirect.
    async AuthUser() {
        const res = await axios.get(AnalyticsLink + '/AnalyticsService/AnaylticsRedirect/' + localStorage.getItem('SessionId'));
        let val = await res.data;
        if (val != "a")
            window.location.href = val;
    }

    componentDidMount() {
        this.AuthUser();
    }

    // displayes the Analytics info page 
    render() {
        return (
            <div className="App">
                <Nav2 currency={this.props.currency} />
                <Map />
                <Footer />
            </div>
        );
    }
}

export default AnalyticsPage;

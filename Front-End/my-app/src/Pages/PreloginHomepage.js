import React, { Component } from 'react';
import '../App.css';
import TVGraph from '../Components/TVGraph/TVGraph.js';
import Text from '../Components/Text/text.js';
import Nav from '../Components/NavigationBar/bar.js';
import Footer from '../Components/Footer/footer.js';
import Banner from '../Components/BannerSpot/Banner.js';
import 'react-pro-sidebar/dist/css/styles.css';
import Q from '../Components/Quote/Quote.js';

class PreloginHomepage extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    //Error handling to do with github deployment
    // async componentDidMount() {
    //     //This is an error check (same thing in post login)
    //     // if (localStorage.getItem('SessionId') == null) {
    //     // } else {
    //     //     window.location.href = "http://localhost:3000/";
    //     // }
    // }

    // displayes the user prelogin home page (the page without the stats)
    render() {
        return (
            <div className="App">
                <Nav currency={this.props.currency} />
                <TVGraph />
                <Q />
                <Text />
                <Banner />
                <Footer />
            </div>
        );
    }
}

export default PreloginHomepage;
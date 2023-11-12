import React, { Component } from 'react';
import '../App.css';
import Information from '../Components/Information/information.js';
import Nav2 from '../Components/NavigationBar/bar.js';
import Footer from '../Components/Footer/footer.js';

class Info extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }


    // displayes the user info page (wall of text)
    render() {
        return (
            <div className="App">
                <Nav2 currency={this.props.currency} />
                <Information />
                <Footer />
            </div>
        );
    }
}

export default Info;

import React, { Component } from 'react';
import Nav from '../Components/NavigationBar/bar.js';
import Suggestion from '../Components/Suggestion/Suggestion.js';
import Footer from '../Components/Footer/footer2.js';

class Feedback extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    // displayes the feedback info page 
    render() {
        return (
            <div className="App">
                <Nav/>
                <Suggestion/>
                <Footer/>
            </div>
        );
    }
}

export default Feedback;

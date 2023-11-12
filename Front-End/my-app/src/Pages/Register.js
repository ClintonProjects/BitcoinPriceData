import React, { Component } from 'react';
import '../App.css';
import RegisterPage from '../Components/Loginpage/Register.js';
import Footer from '../Components/Footer/footer2.js';
import Nav from '../Components/NavigationBar/bar.js';

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };

    }

    // displayes the user registery page
    render() {
        return (
            <div className="App">
                <Nav currency={this.props.currency} />
                <RegisterPage />
                <Footer />
            </div>
        );
    }
}

export default Register;


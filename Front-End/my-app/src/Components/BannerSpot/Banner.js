import React, { Component, PropTypes } from 'react';
import './Banner.css';
import banner from './BTCInfoBannerv2.gif';

export default class Banner extends Component {

    constructor(props) {
        super(props);
        this.state = {
            userActive: false,
        };
        this.isVerifyUserSignedIn = this.isVerifyUserSignedIn.bind(this);
    }

    isVerifyUserSignedIn() {
        //Handles weather user logged in or not this is used for sign up text.
        if (localStorage.getItem('SessionId') != null || localStorage.getItem('SessionId'))
            this.setState({ userActive: true });
        else
            this.setState({ userActive: false });
    }

    componentDidMount() {
        this.isVerifyUserSignedIn();
    }


    render() {
        return (
            <div class="BannerSpotHomePage no-gutters">
                <div class="container">
                    <div class="row py-1">
                        <div class="col-12  no-gutters">
                            <img class="mx-auto d-block" src={banner} width={"50%"} />
                            {this.state.userActive ?
                                "" :
                                <h1 class="textUnderBanner">Sign up today for fastest realtime data</h1>}
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}



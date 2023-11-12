import './App.css';
import getIP from './Funuctions/InformationGather/Ipgrab.js';
import React, { Component, PropTypes, useEffect, useState } from 'react';
import { Route, BrowserRouter as Router, Switch } from "react-router-dom";
import PreHomepage from "./Pages/PreloginHomepage.js";
import PostHomepage from "./Pages/PostloginHomepage.js";
import Login from "./Pages/Login.js";
import AdminPannel from "./Pages/AdminPannel.js";
import Register from "./Pages/Register.js";
import AnalyticsPage from "./Pages/Analytics.js";
import info from "./Pages/Info.js";
import Feedback from "./Pages/Feedback.js";

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      userActive: false,
    };

    this.isVerifyUserSignedIn = this.isVerifyUserSignedIn.bind(this);
  }

  componentDidMount() {
    //adds the livechat to webpage.
    this.livechat();
    //get the user ip for Analytics data
    getIP();
    //Checks if the user signed in
    this.isVerifyUserSignedIn();
  }

  handleStatusChange(satus) {
    this.isVerifyUserSignedIn();
  }

  livechat() {
    //Get the live chat from the API
    var Tawk_API = Tawk_API || {}, Tawk_LoadStart = new Date();
    (function () {
      var s1 = document.createElement("script"), s0 = document.getElementsByTagName("script")[0];
      s1.async = true;
      s1.src = 'https://embed.tawk.to/613c1a4925797d7a89fe609b/1ff9cdcfv';
      s1.charset = 'UTF-8';
      s1.setAttribute('crossorigin', '*');
      s0.parentNode.insertBefore(s1, s0);
    })();;
  }

  isVerifyUserSignedIn() {
    //Verifies weather the user is logged in via the local storage
    if (!localStorage.getItem('SessionId') || localStorage.getItem('SessionId').length === 0)
      this.setState({ userActive: true });
    else
      this.setState({ userActive: false });
  }



  render() {
    return (
      //Redirects the user based on what link they used for the webpage.
      <Router>
        <div>
          <Switch>
            <Route path="/login" component={Login} />
            {this.state.userActive ?
              <Route exact path="/" render={(props) => <PreHomepage userActive={this.state.userActive} {...props} />} />
              : <Route exact path="/" render={(props) => <PostHomepage userActive={this.state.userActive} {...props} />} />}
            <Route path="/admin" exact component={AdminPannel} />
            <Route path="/register" exact component={Register} />
            <Route path="/info" exact component={info} />
            <Route path="/analytics" exact component={AnalyticsPage} />
            <Route path="/feedback" exact component={Feedback} />
          </Switch>
        </div>
      </Router>


    );
  }
}

export default App;

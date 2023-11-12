import React, { Component } from 'react';
import './AdminPannelSideBar.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCog,faDollarSign } from '@fortawesome/free-solid-svg-icons';

export default class AdminPannelSideBar extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };

        this.button = this.button.bind(this);
    }


    button(icon, text) {
        return <div className="button"><h1><FontAwesomeIcon icon={icon}/></h1><h2>{text}</h2></div>
    }

    render() {
        return (
            <div className="AdminPannelSideBar no-gutters">
                {this.button(faCog, "Dashborad")}
                {this.button(faDollarSign, "Accounting")}
            </div>
        );
    }
}


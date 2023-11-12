import React, { Component } from 'react';
import './bar.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faUser, faTicketAlt, faSignInAlt, faAngleDown, faComment, faDatabase, faStickyNote, faHouseUser } from '@fortawesome/free-solid-svg-icons';
import pikachu from './coin.png';
import eu from './eu.png';
import usa from './usa.png';
import globe from './globe.png';
import { Navbar, Nav, NavDropdown, Form, FormControl, Button, Container, Dropdown, DropdownButton } from 'react-bootstrap'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import axios, * as others from 'axios';
import server from "../../Funuctions/DBconnects/Server.js"
import AnalyticsLink from '../../Funuctions/DBconnects/Anaylitics.js'

class Bar extends Component {

    constructor(props) {
        super(props);
        this.state = {
            flag: 'All',
            userActive: false,
            nav: false,
        };
        this.displayFlagOnNav = this.displayFlagOnNav.bind(this);
        this.toggleFlag = this.toggleFlag.bind(this);
        this.signOut = this.signOut.bind(this);
        this.isVerifyUserSignedIn = this.isVerifyUserSignedIn.bind(this);
        this.redriectToPage = this.redriectToPage.bind(this);
        this.buttonSymbol = this.buttonSymbol.bind(this);
        this.redriectToPageNoAuth = this.redriectToPageNoAuth.bind(this);
    }


    //Displays a flag next in nav bar based on what user choices to display (THIS IS NOT USED IN FINAL VERSION)
    displayFlagOnNav(flag) {
        console.log('Display flag = ' + flag);
        if (flag === "All") {
            return (<a href="javascript:void(0)" onClick={this.toggleFlag('All')}><img src={globe} alt="HeadImage" class="euflag" /> All</a>);
        } else if (flag === "EU") {
            return (<a href="javascript:void(0)" onClick={this.toggleFlag('EU')}  ><img src={eu} alt="HeadImage" class="euflag" /> Euro</a>);
        } else if (flag === "USA")
            return (<a href="javascript:void(0)" onClick={this.toggleFlag('USA')}><img src={usa} alt="HeadImage" class="euflag" /> USD</a>);
    }

    //Allows the user to change there currency (THIS IS NOT USED IN FINAL VERSION)
    toggleFlag(flag) {
        console.log('Update flag = ' + flag);
        this.state.flag = flag;
    }

    //Allows the user to sign out of the application
    signOut() {
        localStorage.removeItem('SessionId');
        toast.error('You have successfully signed out!', {
            position: "bottom-center",
            autoClose: 1500,
            hideProgressBar: true,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: false,
        });
        setTimeout(function () {
            window.location.href = server;
        }, 2000);
    }

    //Redirect the user to a page with auth
    redriectToPage(link) {
        if (!this.state.userActive)
            window.location.href = server + link;
    }

    //Redirect the user to a page (doesn't check auth)
    redriectToPageNoAuth(link) {
        window.location.href = server + link;
    }

    //verfies if the user is signed into the application.
    isVerifyUserSignedIn() {
        console.log("[NAV BAR] id is checked " + this.state.userActive + " " + localStorage.getItem('SessionId'));
        // || localStorage.getItem('SessionId').length === 0
        if (localStorage.getItem('SessionId') != null || localStorage.getItem('SessionId'))
            this.setState({ userActive: true });
        else
            this.setState({ userActive: false });
    }

    //.............
    async getAnaylticDataSat() {
        const res = await axios.get(AnalyticsLink + '/AnalyticsService/AnaylticsRedirect/' + localStorage.getItem('SessionId'));
        let val = await res.data;
        if (val == 'a')
            this.setState({ nav: true });
    }

    //handles process at that need to be handled at the start of the app
    componentDidMount() {
        this.isVerifyUserSignedIn();
        this.getAnaylticDataSat();
    }


    //displays the button in nav
    buttonSymbol() {
        return (
            <a><FontAwesomeIcon icon={faUser} /> Navigation</a>);
    }


    //Wasn't planning on commenting any html but this one basically checks if user signe din 
    render() {
        const isVerifyUserSignedIn = this.state.isVerifyUserSignedIn;
        return (
            <header class="nav-colour no-gutters">
                <div class="container py-2 no-gutters">
                    <a class="navbar-brand text-white" href="#" onClick={(e) => this.redriectToPageNoAuth('')}>
                        <img src={pikachu} alt="HeadImage" height={"30px"} width={"30px"} />
                        <b>BTCInfo</b>
                    </a>
                    <div class="float-end">
                        <DropdownButton id="dropdown-button-dark-example2" class="no-gutter" variant="secondary" menuvariant="dark"
                            title={this.buttonSymbol()}>

                            <NavDropdown.Item onClick={(e) => this.redriectToPageNoAuth('/')}>
                                <FontAwesomeIcon icon={faHouseUser} />
                                &nbsp; HomePage
                            </NavDropdown.Item>

                            <NavDropdown.Item onClick={(e) => this.redriectToPageNoAuth('/info')}>
                                <FontAwesomeIcon icon={faComment} />
                                &nbsp; Info/FAQ
                            </NavDropdown.Item>

                            {
                                this.state.nav == true ? <NavDropdown.Item onClick={(e) => this.redriectToPageNoAuth('/analytics')}>
                                    <FontAwesomeIcon icon={faDatabase} />
                                    &nbsp; Analytics
                                </NavDropdown.Item> : ""
                            }


                            {this.state.userActive ?
                                <NavDropdown.Item onClick={(e) => this.redriectToPageNoAuth('/feedback')}>
                                    <FontAwesomeIcon icon={faStickyNote} />
                                    &nbsp; Feedback/Suggestion
                                </NavDropdown.Item>
                                : ""}

                            {!this.state.userActive ?
                                <NavDropdown.Item onClick={(e) => this.redriectToPage('/Register')}><FontAwesomeIcon icon={faUser} />&nbsp; Register </NavDropdown.Item> : ''}
                            {!this.state.userActive ?
                                <NavDropdown.Item onClick={(e) => this.redriectToPage('/login')}><FontAwesomeIcon icon={faSignInAlt} />&nbsp; Sign In</NavDropdown.Item>
                                :
                                <NavDropdown.Item onClick={(e) => this.signOut()}><FontAwesomeIcon icon={faSignInAlt} />&nbsp; Sign Out</NavDropdown.Item>}

                        </DropdownButton>
                    </div>
                </div>
                <ToastContainer />
            </header >
        )
    }
}




export default Bar;
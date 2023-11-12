import React, { Component } from 'react';
import './Login.css';
import { Route, BrowserRouter as Router, Switch, Link, Redirect } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import LoginServerLink from '../../Funuctions/DBconnects/LoginServer.js'
import server from "../../Funuctions/DBconnects/Server.js"

var sessionId;
class loginpage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rememberMe: false,
        };
        this.Login = this.Login.bind(this);
        this.rememberMeOnChange = this.rememberMeOnChange.bind(this);
        this.userEmail = this.userEmail(this);
        // this.guest = this.guest(this);
    }


    //remembers the user login details
    rememberMeOnChange() {
        this.setState({ rememberMe: !this.state.rememberMe });
    }


    //remembers the user login details
    userEmail() {
        return localStorage.getItem('Login email');
    }

    //lets the user login as a guess
    guest() {
        localStorage.setItem('SessionId', "guest");
        window.location.href = server;
    };



    //Sends the request to the login server, and set the user sesion 
    async Login() {
        sessionId = "";
        const requestOptions = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                email: document.getElementById('email').value,
                password: document.getElementById('pass').value
                // password:document.getElementById('pass').value,
            })
        };
        await fetch(LoginServerLink +  '/login/login', requestOptions)
            .then((response) => response.json())
            .then((messages) => {
                localStorage.setItem('SessionId', messages.id);
            });
        if (localStorage.getItem('SessionId') !== "") {
            if (this.state.rememberMe)
                localStorage.setItem('Login email', document.getElementById('email').value);
            else localStorage.removeItem('Login email');
            window.location.href = server;
        } else {
            toast.error('Incorrect username or password!', {
                position: "bottom-center",
                autoClose: 2500,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: false,
            });
        }
    }

    render() {
        return (
            <div class="loginbody center1 no-gutters">
                <div class="mainbody no-gutters">
                    <div class="Loginbox">
                        <div class="classiclogin">
                            <div class="loginform ">

                                <h4><b>Welcome back!</b></h4>
                                <p>We're so excited to see you again!</p>
                                <div class="form-group">
                                    <label class="InputEmail"><b>Email address</b></label>
                                    <input value={this.userEmail} type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Email" />
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputPassword1" class="passwordtexts"><b>Password</b></label>
                                    <input type="password" class="form-control" id='pass' placeholder="Password" />
                                    {/* <p1>Forgot your password?</p1> */}
                                </div>
                                <label>
                                    <div> <input type="checkbox" onClick={this.rememberMeOnChange} />
                                        Remember my email</div>
                                </label>
                                <button type="submit" class="btn btn-primary LoginButton" onClick={this.Login}>Login</button>
                                <Link onClick={this.guest}>click here to login as guest</Link>
                                <Link to="/Register">
                                    <p>Register</p>
                                </Link>
                                <p>

                                </p>

                            </div>
                        </div>
                    </div>
                </div>
                <ToastContainer />
                {/* <div class="SideSpacer" /> */}
            </div>



        );
    }
}

export default loginpage;
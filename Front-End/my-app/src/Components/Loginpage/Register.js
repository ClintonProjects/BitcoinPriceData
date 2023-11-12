import React, { Component } from 'react';
import './Login.css';
import { Route, BrowserRouter as Router, Switch, Link } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import LoginServerLink from '../../Funuctions/DBconnects/LoginServer.js'
import server from "../../Funuctions/DBconnects/Server.js"

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            attempts: 0,
        };
        this.Register = this.Register.bind(this);
    }

    //This is basically copy andpaste of login page, just for register, I didn't want make the login super messy with alot of turner statements

    //lets the user sign in as a guess
    guest() {
        localStorage.setItem('SessionId', "guest");
        window.location.href = server;
    };

    //Allows the register the accounts, sends there details to back in and signs them in
    async Register() {
        var sessionId = "";
        const requestOptions = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*",
            },

            body: JSON.stringify({
                email: document.getElementById('email').value,
                password: document.getElementById('pass').value
            })
        };

        await fetch(LoginServerLink + '/login/Register', requestOptions)
            .then((response) => response.json())
            .then((messages) => {
                localStorage.setItem('SessionId', messages.id);
                window.location.href = server;
            }).catch(error => {
                console.error('There was an error!', error);
                this.setState({ attempts: this.state.attempts + 1 });
                console.log(this.state.attempts);
                toast.error('username already been taken!', {
                    position: "bottom-center",
                    autoClose: 2500,
                    hideProgressBar: true,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: false,
                });
            });

        console.log("ran");
    }

    render() {
        return (
            <div class="loginbody center1 no-gutters">
                <div class="mainbody no-gutters">
                    <div class="Loginbox">
                        <div class="classiclogin">
                            <div class="loginform ">
                                <h4><b>Welcome back!</b></h4>
                                <p>We're so excited for you to sign up!</p>
                                <div class="form-group">
                                    <label class="InputEmail"><b>Email address</b></label>
                                    <input type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Email" />
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1" class="passwordtexts"><b>Password</b></label>
                                    <input type="password" class="form-control" id='pass' placeholder="Password" />
                                </div>
                                {this.state.attempts > 0 ? "User found or password or email is already found" : ""}
                                <button type="submit" class="btn btn-primary LoginButton" onClick={this.Register}>Login</button>
                                <Link onClick={this.guest}>click here to login as guest</Link>
                                <Link to="/Login">
                                    <p>Sign in</p>
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
                <ToastContainer />
            </div>
        );
    }
}

export default Register;
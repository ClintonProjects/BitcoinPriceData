import React, { Component, PropTypes } from 'react';
import './Suggestion.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngry, faGrin, faGrinAlt, faMeh, faSkull } from '@fortawesome/free-solid-svg-icons';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import server from "../../Funuctions/DBconnects/Server.js"
import AnalyticsLink from '../../Funuctions/DBconnects/Anaylitics.js'

var result;
class Suggestion extends Component {
    constructor(props) {
        super(props);
        this.state = {
            RadioButton: [false, false, false, false, false],
            RadioResults: ["Very Unhappy", "Unhappy", "Ok", "Good", "Very Good"],
            clicked: 50,
        };
        this.hoverColour = this.hoverColour.bind(this);
        this.inputExperinceButtons = this.inputExperinceButtons.bind(this);
        this.textForFeedback = this.textForFeedback.bind(this);
        this.SubmitDocument = this.SubmitDocument.bind(this);
    }

    //Lets the user set select saficiation rate about using the app
    inputExperinceButtons(i) {
        var booleanList = [false, false, false, false, false];

        if (i === null) {
            this.setState({ RadioButton: booleanList });
            return
        }

        booleanList[i] = true;
        this.setState({ RadioButton: booleanList })
    }

    //set the colour when the user hovers
    hoverColour(i) {
        if (this.state.RadioButton[i]) {
            return 'blue';
        }
        if (this.state.clicked != 51 && this.state.clicked == i) {
            return 'green';
        }

        return 'white';
    }

    //displays how the user feels about app in text form (under satification rate)
    textForFeedback() {
        var boolList = this.state.RadioButton;
        var pos = 100;
        var stringResult = this.state.RadioResults;

        if (this.state.clicked != 50)
            return stringResult[this.state.clicked];

        for (let i = 0; i < boolList.length; i++) {
            if (boolList[i] === true)
                pos = i;
        }

        if (pos == 100)
            return " ";

        return stringResult[pos];
    }

    //Displays a prompt when the user puts how they feel about the app and sends it to the backend of the app
    async SubmitDocument() {
        if (this.state.clicked == 50 || document.getElementById('comment').value == "") {
            toast.error('You are missing required entry', {
                position: "bottom-center",
                autoClose: 2500,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: false,

            });
            return;
        }
        if (document.getElementById('comment').value.length > 200) {
            toast.error('You cant enter more then 150 charaters', {
                position: "bottom-center",
                autoClose: 2500,
                hideProgressBar: true,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: false,

            });
            return;
        }

        const requestOptions = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({
                rating: this.state.RadioResults[this.state.clicked],
                type: document.getElementById('inlineRadio1').value == "option1" ? "Suggestion" : "Feedback",
                text: document.getElementById('comment').value,
            })
        };
        await fetch(AnalyticsLink + '/AnalyticsService/SaveFeedback', requestOptions)
            .then((response) => response.status)
            .then((messages) => {
                toast.success('Your feedback has been noted!', {
                    position: "bottom-center",
                    autoClose: 2500,
                    hideProgressBar: true,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: false,
                });

                setTimeout(function () {
                    window.location.href = server;
                }, 3000);
            });

    }

    render() {
        return (
            <div class="SuggestionBody center1 no-gutters">
                <div class="container center1 no-gutters">
                    <div class="col-6 justify-content-center SuggestionBackgroundColour py-5">
                        <div class="center1 text-center ">
                            <h1 class="center1 pt-1">Suggestion/Feedback</h1>
                        </div>
                        <div class="center1 text-center pt-1">
                            <p class="pt-2">
                                How was your experince?
                            </p>
                        </div>
                        <div class="center1 text-center pt-2">
                            <p class="pt-2">
                                {/* color={this.hoverColour(0)} */}
                                <FontAwesomeIcon icon={faSkull}

                                    size='3x'
                                    color={this.hoverColour(0)}
                                    onMouseOver={() => this.inputExperinceButtons(0)}
                                    onMouseOut={() => this.inputExperinceButtons(null)}
                                    onClick={() => this.setState({ clicked: 0 })}
                                />

                                <FontAwesomeIcon icon={faAngry} size='3x'
                                    color={this.hoverColour(1)}
                                    onMouseOver={() => this.inputExperinceButtons(1)}
                                    onMouseOut={() => this.inputExperinceButtons(null)}
                                    onClick={() => this.setState({ clicked: 1 })}
                                />

                                <FontAwesomeIcon icon={faMeh} size='3x'
                                    color={this.hoverColour(2)}
                                    onMouseOver={() => this.inputExperinceButtons(2)}
                                    onMouseOut={() => this.inputExperinceButtons(null)}
                                    onClick={() => this.setState({ clicked: 2 })} />

                                <FontAwesomeIcon icon={faGrin} size='3x'
                                    color={this.hoverColour(3)}
                                    onMouseOver={() => this.inputExperinceButtons(3)}
                                    onMouseOut={() => this.inputExperinceButtons(null)}
                                    onClick={() => this.setState({ clicked: 3 })} />

                                <FontAwesomeIcon icon={faGrinAlt} size='3x'
                                    color={this.hoverColour(4)}
                                    onMouseOver={() => this.inputExperinceButtons(4)}
                                    onMouseOut={() => this.inputExperinceButtons(null)}
                                    onClick={() => this.setState({ clicked: 4 })}
                                />
                            </p>
                        </div>
                        <div class="center1 text-center">
                            <p>
                                {this.textForFeedback()}
                            </p>
                        </div>
                        <div class="center1 text-center pt-2">
                            <p class="pt-2">
                                Is this a suggestion or feedback?
                            </p>
                        </div>
                        <div class="center1 text-center pt-2">
                        </div>
                        <div class=" center1 text-center pt-2">
                            <p class="pt-2">
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1" checked />
                                    <label class="form-check-label" for="inlineRadio1">Suggestion</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2" />
                                    <label class="form-check-label" for="inlineRadio2">Feedback</label>
                                </div>
                            </p>
                        </div>
                        <div class="center1 text-center pt-2 ">
                            <p class="pt-2">
                                Please let us know more about your experince?

                                <div class="form-group">
                                    <label for="comment"></label>
                                    <textarea class="form-control" rows="5" id="comment" placeholder="Please post your suggestions and feedback here"></textarea>
                                </div>
                            </p>
                        </div>
                        <div class="center1 text-center pt-2 ">
                            <button
                                onClick={() => this.SubmitDocument()}
                                type="submit"
                                class="btn btn-primary LoginButton">
                                Submit
                            </button>
                        </div>
                    </div>
                </div>
                <ToastContainer />
            </div>
        );
    }
}

export default Suggestion;


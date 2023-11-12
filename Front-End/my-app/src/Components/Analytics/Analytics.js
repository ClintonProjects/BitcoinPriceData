import React, { Component } from 'react';
import './information.css';
import Map from './Map.js';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAngry, faGrin, faGrinAlt, faMeh, faSkull } from '@fortawesome/free-solid-svg-icons';
import { Button } from 'react-bootstrap'
import axios, * as others from 'axios';
import AnalyticsLink from '../../Funuctions/DBconnects/Anaylitics.js'

class Analytics extends Component {

    constructor(props) {
        super(props);
        this.state = {
            RadioButton: [false, false, false, false, false],
            RadioResults: ["Very Unhappy", "Unhappy", "Ok", "Good", "Very Good"],
            clicked: 1,
            serverResponse: '',
            currentPosForFeedback: 1,
            previousPosForFeedback: 1,
            satificationData: [],
            saficationColour: 0,
            visitedData: [],
            visitedDataCount: [],
        };
        this.FirstTable = this.FirstTable.bind(this);
        this.textForFeedback = this.textForFeedback.bind(this);
        this.pageRoll = this.pageRoll.bind(this);
        this.colourForIndFeedback = this.colourForIndFeedback.bind(this);
        this.getIndexColourForSatRate = this.getIndexColourForSatRate.bind(this);
        this.countryData = this.countryData.bind(this);
    }

    FirstTable(d1, d2) {
        return (
            <div class="row text-center">
                <div class="col-6">
                    <p>{d1}</p>
                </div>
                <div class="col-6">
                    <p>{d2}</p>
                </div>
            </div>
        )
    }

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


    colourForIndFeedback(i) {
        var RadioResults = ["Very Unhappy", "Unhappy", "Ok", "Good", "Very Good"];
        var temp = 0;
        for (let value of RadioResults) {
            if (value == this.state.serverResponse.rating && temp == i)
                return 'yellow';
            temp++;
        }
        return 'white';
    }

    getIndexColourForSatRate() {
        var RadioResults = ["Very Unhappy", "Unhappy", "Ok", "Good", "Very Good"];
        let p = this.state.satificationData["MostCommon"];
        var i = 0;
        for (let value of RadioResults) {
            if (value == p)
                this.setState({ saficationColour: i });
            i++;
        }
    }

    componentDidMount() {
        this.getData(this.state.currentPosForFeedback);
        this.getAnaylticDataSat();
        this.countryData(false);
    }

    async countryData(key) {
        const res = await axios.get(AnalyticsLink + '/AnalyticsService/getAllUniqueCountryVisted/' + localStorage.getItem('SessionId'));
        let val = await res.data;

        let index = 0;
        let country = [];
        let count = [];

        for (var prop in val) {
            country[index] = prop;
            count[index] = val[prop];
            index++;

            if (index == 5)
                break;
        }

        this.setState({ visitedData: country });
        this.setState({ visitedDataCount: count });

    }


    pageRoll = (jump) => {
        if (this.state.currentPosForFeedback + jump < 1)
            return;

        this.setState({ previousPosForFeedback: this.state.currentPosForFeedback });
        if (this.getData(this.state.currentPosForFeedback + jump) == null) return
        this.setState({ currentPosForFeedback: this.state.currentPosForFeedback + jump });
        this.getData(this.state.currentPosForFeedback);
    }

    async getAnaylticDataSat() {
        const res = await axios.get(AnalyticsLink + '/AnalyticsService/getSatifcationRate/' + localStorage.getItem('SessionId'));
        let val = await res.data;
        this.setState({ satificationData: val });
        this.getIndexColourForSatRate();
    }



    async getData(pos) {
        if (pos < 1) return
        const res = await axios.get(AnalyticsLink + '/AnalyticsService/getFeedback/' + localStorage.getItem('SessionId') + '/' + pos);
        let val = await res.data[0];

        if (val.text == "" && val.rating == "" && val.text == "") {
            this.setState({ currentPosForFeedback: this.state.previousPosForFeedback });
            return;
        }

        this.setState({ serverResponse: await res.data[0] })
    }



    render() {
        return (
            <div class="infoColour">
                <div class="container py-2 infoColour no-gutters">
                    <Map class="py-5" />
                    <div class="row text-center py-4">
                        <div class="col-12">
                            <h4>Analytics: Countries visted by</h4>
                        </div>
                    </div>
                    <div class="row text-center ">
                        <div class="col-2 " />
                        <div class="col-8 feedback ">
                            {this.FirstTable(this.state.visitedData[0], this.state.visitedDataCount[0])}
                            {this.FirstTable(this.state.visitedData[1], this.state.visitedDataCount[1])}
                            {this.FirstTable(this.state.visitedData[2], this.state.visitedDataCount[2])}
                            {this.FirstTable(this.state.visitedData[3], this.state.visitedDataCount[3])}
                            {this.FirstTable(this.state.visitedData[4], this.state.visitedDataCount[4])}
                        </div>
                    </div>
                    <div class="row text-center py-4">
                        <div class="col-12">
                            <h4>Average user satisfaction rate:</h4>
                        </div>
                    </div>
                    <div class="row text-center ">
                        <div class="col-2 " />
                        <div class="col-8 feedback py-3 ">
                            <div class="col-4 feedback mx-auto py-3">
                                <p class="h-50 remove_overflow ">
                                    <FontAwesomeIcon icon={faSkull} size='3x' color={this.state.saficationColour == 1 ? "yellow" : "white"} />
                                    <FontAwesomeIcon icon={faAngry} size='3x' color={this.state.saficationColour == 2 ? "yellow" : "white"} />
                                    <FontAwesomeIcon icon={faMeh} size='3x' color={this.state.saficationColour == 3 ? "yellow" : "white"} />
                                    <FontAwesomeIcon icon={faGrin} size='3x' color={this.state.saficationColour == 4 ? "yellow" : "white"} />
                                    <FontAwesomeIcon icon={faGrinAlt} size='3x ' color={this.state.saficationColour == 5 ? "yellow" : "white"} />
                                </p>
                            </div>
                            <div class="col-4 feedback mx-auto">
                                <p>Weighting Of Each Feedback</p>
                            </div>
                            <div class="col-12 feedback mx-auto">
                                <p>{this.state.satificationData["Very Unhappy"]}% Very Unhappy
                                    &ensp; {this.state.satificationData["Unhappy"]}% Unhappy
                                    &ensp; {this.state.satificationData["Ok"]}% Ok
                                    &ensp; {this.state.satificationData["Good"]}% Good
                                    &ensp; {this.state.satificationData["Very Good"]}% Very Good </p>
                            </div>
                        </div>
                    </div>
                    <div class="row text-center py-2">
                        <div class="col-2 " />
                    </div>
                    <div class="row text-center py-4">
                        <div class="col-2 " />
                        <div class="col-8">
                            <h4>Feedback/Suggestions:</h4>
                        </div>
                    </div>
                    <div class="row text-center BodyFeedbackText ">
                        <div class="col-2 " />
                        <div class="col-8 feedback py-3 ">
                            <div class="col-8 feedback mx-auto py-1">
                                <h4>index #{this.state.currentPosForFeedback}</h4>
                            </div>
                            <div class="col-6 feedback mx-auto h-50">
                                <p class="h-100 remove_overflow">
                                    {this.state.serverResponse.text}
                                </p>
                            </div>
                            <div class="col-4 feedback mx-auto py-">
                                <p class="h-100 remove_overflow">
                                    <FontAwesomeIcon icon={faSkull} size='3x' color={this.colourForIndFeedback(0)} />
                                    <FontAwesomeIcon icon={faAngry} size='3x' color={this.colourForIndFeedback(1)} />
                                    <FontAwesomeIcon icon={faMeh} size='3x' color={this.colourForIndFeedback(2)} />
                                    <FontAwesomeIcon icon={faGrin} size='3x' color={this.colourForIndFeedback(3)} />
                                    <FontAwesomeIcon icon={faGrinAlt} size='3x' color={this.colourForIndFeedback(4)} />
                                </p>
                                <p>Type: {this.state.serverResponse.type}</p>
                                <p></p>
                            </div>
                        </div>
                    </div>
                    <div class="row text-center">
                        <div class="col-2" />
                        <div class="col-4 feedback pb-3">
                            <Button variant="primary" size="sm"
                                onClick={() => this.pageRoll(-1)}
                            >
                                Previous Feedback
                            </Button>
                        </div>
                        <div class="col-4 feedback pb-3">
                            <Button variant="primary" size="sm"
                                onClick={() => this.pageRoll(1)}>
                                Next Feedback</Button>
                        </div>
                    </div>
                    <div class="py-3" />
                </div >
            </div >
        )
    }
}


export default Analytics;


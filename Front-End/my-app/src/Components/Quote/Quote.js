import React, { Component, PropTypes } from 'react';
import './Quote.css';

export default class Quote extends Component {
    constructor(props) {
        super(props);
        this.state = {
        };
    }

    render() {
        return (
            <div className="Quote">
                <div className="spacer" />
                <div className="VerticalSpace">
                    <div className="textcontainer">
                        <h1><b>Need help, Join are live chat?</b></h1>
                    </div>
                </div>
                <div className="spacer2" />
                <div className="VerticalSpace2">
                    <a href="javascript:void(Tawk_API.toggle())" type="button" class="btn btn-primary button rounded-pill border-primary"><span>&#160; Join the livechat &#160;</span></a>
                </div>
            </div>
        );
    }
}

import React, { Component } from 'react';
import { GoogleMap, LoadScript, Marker } from '@react-google-maps/api';

const containerStyle = {
    width: '67vw',
    height: '400px',
};

const center = {
    lat: 53.3498,
    lng: 6.2603
};

class Map extends Component {
    render() {
        return (
            <LoadScript
                googleMapsApiKey="AIzaSyDZ2j9FrWJFL8Stbb-Qo8Q43cgKk0sau1s"
            >
                <GoogleMap
                    mapContainerStyle={containerStyle}
                    center={center}
                    zoom={10}

                >

                    <Marker 
                    
                    />


                    { /* Child components, such as markers, info windows, etc. */}
                    <></>
                </GoogleMap>
            </LoadScript>
        )
    }
}

export default Map;
import AnalyticsLink from '../DBconnects/Anaylitics.js';

class Ipgrab {
}
//Grabs the user ip for the Analytics

const publicIp = require('public-ip');

//Get the user ip for the anaylitcs service
function getIP() {
    (async () => {
        let ipv4;
        let ipv6;
        try { ipv4 = await publicIp.v4(); } catch (e) { ipv4 = ""; }
        try { ipv6 = await publicIp.v6(); } catch (e) { ipv6 = ""; }

        const requestOptions = {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                "Access-Control-Allow-Origin": "*",
            },
            body: JSON.stringify({ "ipv4": ipv4, "ipv6": ipv6 })
        };

        fetch(AnalyticsLink + '/AnalyticsService/AnalyticsData', requestOptions)
            .then(async response => {
                const data = await response.json();
                if (!response.ok) {
                    const error = (data && data.message) || response.statusText;
                    return Promise.reject(error);
                }
            })
            .catch(error => {
            });
    })();
}



export default getIP;
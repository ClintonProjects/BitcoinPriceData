class VerifyUserLogin {
}

var result = false;

VerifyUserLogin.verify = function () {
    const requestOptions = {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            "Access-Control-Allow-Origin": "*",
        },
    };
        fetch('http://localhost:8085/login/session/' + localStorage.getItem('SessionId'), requestOptions)
            .then((response) => response.json())
            .then((messages) => {
                result = messages.response;
            }).catch(error => {
            });;
            return result;
}


export default VerifyUserLogin;

// module.exports = {
//     functions: VerifyUserLogin 
// };


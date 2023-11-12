// import React from 'react';
// import { Client } from '@stomp/stompjs';

// const SOCKET_URL = 'ws://localhost:8081/ws-message';

// class App_Stomp extends React.Component {
//   constructor() {
//     super();
//     this.state = {
//       messages: 'You server message here.',
//     };
//   };

//   componentDidMount() {
//     let currentComponent = this;
//     let onConnected = () => {
//       console.log("Connected!!")
//       client.subscribe('/topic/message', function (msg) {
//         if (msg.body) {
//           var jsonBody = JSON.parse(msg.body);
//           console.log(jsonBody);
//           if (jsonBody.message) {
//             currentComponent.setState({ messages: jsonBody.message })
//             console.log(jsonBody);
//           }
//         }
//       });
//       console.log("done?");
//     }

//     let onDisconnected = () => {
//       console.log("Disconnected!!")
//     }

//     const client = new Client({
//       brokerURL: SOCKET_URL,
//       reconnectDelay: 5000,
//       heartbeatIncoming: 4000,
//       heartbeatOutgoing: 4000,
//       onConnect: onConnected,
//       onDisconnect: onDisconnected
//     });

//     client.activate();
//   };

//   render() {
//     return (
//       <div>
//         <div>{this.state.messages}</div>
//       </div>
//     );
//   }

// }

// export default App_Stomp;
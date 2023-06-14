import axios from "axios";
import {Client} from "@stomp/stompjs";
import {boot} from "quasar/wrappers";


export const api = axios.create({
  baseURL: 'https://plant-controller.deyanix.eu/'
  // baseURL: 'http://localhost:8080/'
});

export const wsClient = new Client({
  brokerURL: 'wss://plant-controller.deyanix.eu/ws'
  // brokerURL: 'ws://localhost:8080/ws'
});

export default boot(() => {
  wsClient.activate();
})

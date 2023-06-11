import axios from "axios";
import {Client} from "@stomp/stompjs";
import {boot} from "quasar/wrappers";


export const api = axios.create({
  baseURL: 'https://plant-controller.deyanix.eu/'
});

export const wsClient = new Client({
  brokerURL: 'wss://plant-controller.deyanix.eu/ws'
});

export default boot(() => {
  wsClient.activate();
})

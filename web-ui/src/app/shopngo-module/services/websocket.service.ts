import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { AUTH_TOKEN_NAME } from "../constants/app.config";

export class WebsocketService {
  webSocketEndPoint: string = '/ws';
  topic: string;
  stompClient: any;
  messageHandlerFunction: Function;

  constructor(topic: string, messageHandlerFunction: Function){
    this.topic = topic;
    this.messageHandlerFunction = messageHandlerFunction;
  }

  _connect() {
    console.log("Initialize WebSocket Connection");
    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);
    const that = this;
    this.stompClient.connect({"X-Authorization": "Bearer " + localStorage.getItem(AUTH_TOKEN_NAME)}, function (frame) {
      that.stompClient.subscribe(that.topic, function (message) {
        that.onMessageReceived(message);
      });
    }, this.errorCallBack);
  };

  _disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
    console.log("Disconnected");
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error) {
    console.log("errorCallBack -> " + error)
    setTimeout(() => {
      this._connect();
    }, 5000);
  }

  /**
   * Send message to sever via web socket
   * @param {*} message
   */
  _send(message) {
    console.log("calling logout api via web socket");
    this.stompClient.send("/app/hello", {}, JSON.stringify(message));
  }

  onMessageReceived(message) {
    console.log("Message Recieved from Server :: " + message);
    this.messageHandlerFunction.apply(this, [message.body]);
  }
}

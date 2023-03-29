#include <WiFi.h>

const char* ssid = "deyanix-Guest";
const char* password = "S0CH!rum16";

unsigned long previousMillis = 0;
unsigned long interval = 30000;

const char* host = "calapi.inadiutorium.cz";
const int httpPort = 80;

void initWiFi() {  
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi ..");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print('.');
    delay(1000);
      Serial.println(WiFi.localIP());
  }
  /*
  while (WiFi.status() == WL_CONNECTED) {
    delay(15000);
      Serial.print(" connected to: ");
      Serial.println(WiFi.localIP());
  }    
  */
}

void reconToWiFi() {
  unsigned long currentMillis = millis();
  if ((WiFi.status() != WL_CONNECTED) && (currentMillis - previousMillis >=interval)) {
    Serial.print(millis());
    Serial.println("Reconnecting to WiFi...");
    WiFi.disconnect();
    WiFi.reconnect();
      initWiFi();
        Serial.print("RRSI: ");
        Serial.println(WiFi.RSSI());
    previousMillis = currentMillis;
  }
}

void readResponse(WiFiClient *client){
  unsigned long timeout = millis();
  while(client->available() == 0){
    if(millis() - timeout > 5000){
      Serial.println(">>> Client Timeout !");
      client->stop();
      return;
    }
  }

  while(client->available()) {
    String line = client->readStringUntil('\r');
    Serial.print(line);
  }

  Serial.printf("\nClosing connection\n\n");
}

void setup() {
  Serial.begin(115200);
  while (!Serial) {
    delay(100);
  }
  initWiFi();
    Serial.print("RRSI: ");
    Serial.println(WiFi.RSSI());
//Od razu po zbadaniu mocy wifi, rozłącza się z siecią
  //WiFi.disconnect();
}

void loop() {
  //int value = analogRead(34);
  //Serial.println(value);
  //delay(2000);
  reconToWiFi();


  WiFiClient client;

  if (!client.connect(host, httpPort)) {
    return;
  }

  client.print("GET /api/v0/en/calendars/general-en/tomorrow HTTP/1.1\r\nHost: calapi.inadiutorium.cz\r\nConnection: close\r\n\r\n");
  readResponse(&client);
    delay(30000);
}



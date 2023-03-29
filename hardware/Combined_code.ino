#include <WiFi.h>
#include<Wire.h>

#define Addr 0x48

unsigned int count;
float vtg;

const char* ssid = "deyanix-Guest";
const char* password = "S0CH!rum16";
// const char* ssid = "NajlepszeTrucki";
// const char* password = "Scania420";

unsigned long previousMillis = 0;
unsigned long interval = 30000;

// const char* host = "calapi.inadiutorium.cz";
// const int httpPort = 80;
const char* host = "192.168.28.107";
const int httpPort = 8080;
WiFiClient client;

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
void initI2C(){
      // Initialise I2C communication as MASTER
      Wire.begin();
      // Start I2C Transmission
      Wire.beginTransmission(Addr);
      // Send configuration command
      // Continuous conversion mode, 16-bit resolution
      Wire.write(0x01);
      // Stop I2C Transmission
      Wire.endTransmission();
      Wire.beginTransmission(Addr);
      Wire.write(0x01);
      Wire.write(0x04);
      Wire.write(0x88);
      Wire.endTransmission();
      Wire.end();
}
void wireRead(){
      Wire.begin();
      Wire.beginTransmission(Addr);
      Wire.write(0x00);
      Wire.endTransmission();
      Wire.requestFrom(Addr, 2);
      while(Wire.available()<2);
      count = Wire.read();
      count <<= 8;
      count += Wire.read();
      Wire.flush();
      Wire.end();
      Serial.println(count);
}
void reconToWiFi() {
  unsigned long currentMillis = millis();
  if ((WiFi.status() != WL_CONNECTED) && (currentMillis - previousMillis >=interval)) {
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
void sendValue(){
      // String queryString = String("?value=") + String(count);
      // client.print("POST /api/v0/en/calendars/general-en/tomorrow HTTP/1.1\r\nHost: calapi.inadiutorium.cz\r\nConnection: close\r\n\r\n");
      // client.println(queryString);
      // client.print("GET /api/v0/en/calendars/general-en/tomorrow queryString HTTP/1.1\r\nHost: calapi.inadiutorium.cz\r\nConnection: close\r\n\r\n");
      String text = "{\"value\": "+String(count)+"}";
      client.println("POST /sensors/1/measurements HTTP/1.1");
      client.println("Content-Type: application/json");
      client.println("Host: 192.168.28.107:8080");
      client.println("Accept: */*");
      client.println("Connection: Close");
      client.println("Content-Length: " + String(text.length()));
      client.println();
      client.print(text);
      readResponse(&client);
}
void setup() {
  Serial.begin(115200);
  initI2C();
  delay(100);

  while (!Serial) {
    delay(100);
  }
  initWiFi();
    Serial.print("RRSI: ");
    Serial.println(WiFi.RSSI());
}

void loop() {
  reconToWiFi();

  if (client.connect(host, httpPort)) {
      wireRead();
      sendValue();
  }
  delay(10000);
}



#include <WiFi.h>
#include<Wire.h>

#define Addr 0x48

unsigned int count;

const char* ssid = "deyanix-Guest";
const char* password = "S0CH!rum16";

unsigned long previousMillis = 0;
unsigned long interval = 30000;

const char* host = "192.168.28.107";
const int httpPort = 8080;
WiFiClient client;

void initWiFi() {  
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
  }
}
void initI2C(){
  Wire.begin();
  Wire.beginTransmission(Addr);
  Wire.write(0x01);
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
    WiFi.disconnect();
    WiFi.reconnect();
    initWiFi();
    previousMillis = currentMillis;
  }
}
void readResponse(WiFiClient *client){
  unsigned long timeout = millis();
  while(client->available() == 0){
    if(millis() - timeout > 5000){
      client->stop();
      return;
    }
  }
  while(client->available()) {
    String line = client->readStringUntil('\r');
    Serial.print(line);
  }
}
void sendValue(){
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
  initI2C();
  delay(100);
  while (!Serial) {
    delay(100);
  }
  initWiFi();
}

void loop() {
  reconToWiFi();
  if (client.connect(host, httpPort)) {
      wireRead();
      sendValue();
  }
  delay(10000);
}



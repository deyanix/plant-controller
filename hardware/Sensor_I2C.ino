#include<Wire.h>

#define Addr 0x48
unsigned int count;
float vtg;

void setup()
{
//Wire.setClock 6(400000);
// Start serial communication and set baud rate = 9600
Serial.begin(9600);
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
delay(100);

}

void loop()
{
unsigned int data[2];

// Start I2C Transmission
Wire.beginTransmission(Addr);
// Select data register
Wire.write(0x00);
// Stop I2C Transmission
Wire.endTransmission();
// Request 2 bytes of data
Wire.requestFrom(Addr, 2);
// Read 2 bytes of data
// raw_adc msb, raw_adc lsb
while(Wire.available())
{
data[0] = Wire.read();
data[1] = Wire.read();
}
// Serial.print("DATA[0] : ");
// Serial.println(data[0]);
// Serial.print("DATA[1] : ");
// Serial.println(data[1]);
count=data[0];
count=(count<<8)+data[1];
// Serial.print("COUNT : ");
Serial.println(count);
delay(2000);
}
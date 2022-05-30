#include <OneWire.h>
#include <DallasTemperature.h>
#include <Wire.h>


 
const byte  pinDatosDQ = 6;
int outValue = 0;
int ledPIN = 9;
int timeout = 0;
int lectura = 0;

OneWire oneWireObjeto(pinDatosDQ);
DallasTemperature sensorDS18B20(&oneWireObjeto);
// setup routine runs once when you press reset
void setup() {
  Serial.begin(9600);
  sensorDS18B20.begin();
  pinMode(ledPIN , OUTPUT);

 
 
  
}
// loop routine runs over and over again forever
void loop() {
 Serial.println("Send 'S' to start");
 lectura = Serial.read();
 delay(200);
 if(lectura == 'S'){
  while(timeout < 600 && lectura != 'E'){
    lectura = Serial.read();
    outValue = analogRead(A0);
    sensorDS18B20.requestTemperatures();
    Serial.println("Temperatura: ");
    Serial.println(sensorDS18B20.getTempCByIndex(0));
    Serial.println("Presión");
    Serial.println(outValue);
     
    if(lectura == '1'){
      Serial.println("Encendiendo LED");
      digitalWrite(ledPIN, HIGH);
    }
    if(lectura == '2'){
      Serial.println("Apagando LED");
      digitalWrite(ledPIN, LOW);
    }
    delay(200);    
  }
 }
 if(lectura == 'E'){
  Serial.println("Parando mediciones, pulse 'S' para empezar de nuevo");  
 }
 delay(200);
 

}

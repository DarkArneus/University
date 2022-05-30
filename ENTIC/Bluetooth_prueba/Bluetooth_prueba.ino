  #include <SoftwareSerial.h>   // Incluimos la librería  SoftwareSerial  
SoftwareSerial BT(10,11);    // Definimos los pines RX y TX del Arduino conectados al Bluetooth

int r=0;

// Motor 1 (adalt)
int dirPin = 5;
int speedPin = 6;

//Motor 2 (de la dreta)
int dirPin2 = 8;
int speedPin2= 9;

//Motor 3 (de l'esquerra)
int dirPin3 = 12;
int speedPin3 = 13;

void setup()
{
  pinMode(dirPin, OUTPUT);
  pinMode(speedPin, OUTPUT);
  pinMode(dirPin2, OUTPUT);
  pinMode(speedPin2, OUTPUT);
  
  pinMode(dirPin3, OUTPUT);
  pinMode(speedPin3, OUTPUT);
  BT.begin(9600);       // Inicializamos el puerto serie BT (Para Modo AT 2)
  Serial.begin(9600);   // Inicializamos  el puerto serie  
}
 
void loop()
{
  r=BT.read();  
  // Hundirse
  if (r == 'D'){
    // Motor 1
    Serial.println("HUNDIENDOSE");
    digitalWrite(dirPin, LOW);
    digitalWrite(speedPin, HIGH);
  }
  if (r == 'd'){
    Serial.print("HUNDIENDOSEN'T");
    digitalWrite(dirPin, LOW);
    digitalWrite(speedPin, LOW);
  }
  // Salir a flote
  if (r == 'U'){
    Serial.print("SALIENDO A FLOTE");
    digitalWrite(dirPin, HIGH);
    digitalWrite(speedPin, HIGH);
  }
  if (r == 'u'){
    Serial.print("SALIENDON'T");
    digitalWrite(dirPin, LOW);
    digitalWrite(speedPin, LOW);
  }
  // Adelante (Forward)
    if(r == 'F' ){
      // Motor 2
      Serial.print("ADELANTE");
      digitalWrite(dirPin2, HIGH);
      digitalWrite(speedPin2, HIGH); //mod
      delay(50);

      // Motor 3;
      digitalWrite(dirPin3, HIGH);
      digitalWrite(speedPin3, HIGH);//mod
    }
      // Apagado de ir adelante (forwardn't)

     if(r == 'f'){
     Serial.print("ADELANTEN'T");

      // Motor 2 
      digitalWrite(dirPin2, LOW);
      digitalWrite(speedPin2, LOW);
      delay(50);

      // Motor 3
      digitalWrite(dirPin3, LOW);
      digitalWrite(speedPin3, LOW);
     }

    // Atras (Backwards)
     if(r == 'B'){
      
     Serial.print("ATRAS");

      // Motor 2
      digitalWrite(dirPin2, LOW);
      digitalWrite(speedPin2 , HIGH); //mod
      delay(50);

      // Motor 3
      digitalWrite(dirPin3, LOW);
      digitalWrite(speedPin3, HIGH);//mod
     }

     // Apagado de ir hacia atràs
     if(r == 'b'){
    Serial.print("ATRASN'T");
      
      // Motor 2
      digitalWrite(dirPin2,LOW);
      digitalWrite(speedPin2, LOW);
      delay(50);
      // Motor 3
      digitalWrite(dirPin3,LOW);
      digitalWrite(speedPin3, LOW);
     }
     // Girar hacia la izq
     if(r == 'L'){
      // Motor 2
      digitalWrite(dirPin2, HIGH);
      digitalWrite(speedPin2, HIGH);//mod

      // Motor 3
      digitalWrite(dirPin3, LOW);
      digitalWrite(speedPin3, HIGH); //mod
     }
     // Apagado de girar hacia la izq
     if ( r == 'l'){
      Serial.print("d");

      // Motor 2
      digitalWrite(dirPin2, LOW);
      digitalWrite(speedPin2, LOW);

      // Motor 3
      digitalWrite(dirPin3, LOW);
      digitalWrite(speedPin3, LOW);
     }
     
     // Girar hacia la derecha
     if (r == 'R'){
      Serial.print("d");
      // Motor 2
      digitalWrite(dirPin2, LOW);
      digitalWrite(speedPin2, HIGH);

      // Motor 3
      digitalWrite(dirPin3, HIGH);
      digitalWrite(speedPin3, HIGH);
     }
     // Apagado de girar hacia la derecha
     if(r == 'r'){

      // Motor 2
      digitalWrite(dirPin2, LOW);
      digitalWrite(speedPin2, LOW);

      // Motor 3
      digitalWrite(dirPin3, LOW);
      digitalWrite(speedPin3, LOW);
     }
    
  
}

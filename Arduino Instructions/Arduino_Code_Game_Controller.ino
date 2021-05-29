//set button pins
const int goLeft = 3;
const int goRight = 2;

//create global timekeeping variable
unsigned long prevTime = 0;

void setup()
{
  //set pinmodes
  pinMode(goLeft, INPUT);
  pinMode(goRight, INPUT);
  
  //start serial connection
  Serial.begin(9600);
}

void loop()
{
  //read current timestamp
  unsigned long curTime = millis();
  
  //read potentiometer
  int speedCount = analogRead(A0);
  String speed = String(speedCount);
    
  //check for pressing of goLeft button
  if(digitalRead(goLeft) == HIGH) 
  {
    Serial.println("left");
    delay(100);
  }
  
  //check for pressing of goRight button
  if(digitalRead(goRight) == HIGH) 
  {
    Serial.println("right");
    delay(100);
  }
  
  //update game speed on set intervals (scrapped idea, couldnt finish in time)
  
  if ((unsigned long)(curTime - prevTime) > 4999)
  {
    //send calculations here
    Serial.println(speed + ";");
    prevTime = curTime;
  }
}

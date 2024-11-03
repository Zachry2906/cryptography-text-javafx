
LiquidCrystal_I2C lcd(32, 16, 2);

#define LED_R 5
#define LED_G 7
#define LED_B 6

#define BTN 2
#define BUZZ 4

#define TRIGGER 9
#define ECHO 3

long jarak = 0;
float time = 0;
int mode = 0;
int saveMode = 0;
int nama = 0;
char inputan;

void setup()
{
  Serial.begin(9600);
  pinMode(LED_R, OUTPUT);
  pinMode(LED_G, OUTPUT);
  pinMode(LED_B, OUTPUT);
  
  pinMode(BUZZ, OUTPUT);
  
  pinMode(TRIGGER, OUTPUT);
  pinMode(ECHO, INPUT);
  
  pinMode(BTN, INPUT);
  
  // Init LCD
  lcd.init();
  lcd.display();
  
  attachInterrupt(digitalPinToInterrupt(BTN), changeMode, RISING);
}

void loop()
{
   
  lcd.backlight();
  if (nama == 0){
  lcd.setCursor(0,0);
  lcd.print("Ahmad Zakaria");
  lcd.setCursor(0,1);
  lcd.print("123220077");
  delay(3000);
    clearLine();
    nama = 1;
  }
  
  if (nama != 0)
  {  jarak = countDistance();
   lcd.setCursor(0,0);
   lcd.print("Jarak : ");
      lcd.setCursor(0,1);
    lcd.print(jarak);
  }
    
  switch(mode) {
    case 0:
      mode0();
      break;
    case 1:
      mode1();
      break;
  }
}

void changeMode() {
  Serial.println(mode);
 // lcd.clear();
  if (mode == 1) {
    mode = 0;
  } else {
    ++mode;
  }
}

void mode0() {
  if (saveMode != 0) {
    lcd.clear();
    saveMode = 0;
  }
    digitalWrite(LED_R, LOW);
  digitalWrite(LED_G, LOW);
  digitalWrite(LED_B, LOW);
  digitalWrite(BUZZ, LOW);
}

void mode1() {
  if (saveMode != 1) {
    lcd.clear();
    saveMode = 1;
  }
  
  if (jarak < 50) {
  digitalWrite(LED_R, LOW);
  digitalWrite(LED_G, HIGH);
  digitalWrite(LED_B, LOW);
  digitalWrite(BUZZ, LOW);
  } else if (jarak >= 50 && jarak < 100) {
  digitalWrite(LED_R, LOW);
  digitalWrite(LED_G, HIGH);
  digitalWrite(LED_B, HIGH);
  digitalWrite(BUZZ, LOW);
  } else {
      digitalWrite(LED_R, HIGH);
  digitalWrite(LED_G, LOW);
  digitalWrite(LED_B, LOW);
  digitalWrite(BUZZ, HIGH);
  }
}

long countDistance() {
  digitalWrite(TRIGGER, HIGH);
  delayMicroseconds(2);
  digitalWrite(TRIGGER, LOW);
  time = pulseIn(ECHO, HIGH);
  return (time * 0.0343) / 2;
}

void clearLine() {
  lcd.setCursor(0, 1);
  for(int i = 0; i < 16; i++) {
    lcd.print(" ");
  }
  lcd.setCursor(0, 0);
  for(int i = 0; i < 16; i++) {
    lcd.print(" ");
  }
}
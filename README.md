[![Build Status](https://travis-ci.com/megoRU/Plesk_Auto_Update_Trial_Key.svg?branch=master)](https://travis-ci.com/megoRU/Plesk_Auto_Update_Trial_Key) 
![GitHub All Releases](https://img.shields.io/github/downloads/megoRU/Plesk_Auto_Update_Trial_Key/total) 
[![GitHub stars](https://img.shields.io/github/stars/megoRU/Plesk_Auto_Update_Trial_Key)](https://github.com/megoRU/Plesk_Auto_Update_Trial_Key/stargazers)
[![GitHub license](https://img.shields.io/github/license/megoRU/Plesk_Auto_Update_Trial_Key)](https://github.com/megoRU/Plesk_Auto_Update_Trial_Key/blob/master/LICENSE)

## Disclaimer
<b>I am against the abuse of free licenses. This code is presented for informational purposes only. Using it can and probably violates Plesk rules. The author is not responsible for any material losses incurred by Plesk.</b>

# Plesk auto-update trial key
Plesk trial key auto update

## Create table in MySQL:

```
CREATE TABLE `Plesk` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `text` (`text`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```
```
INSERT INTO Plesk (text) values ("Your Key");
INSERT INTO Plesk (text) values ("Your Key2");
INSERT INTO Plesk (text) values ("Your Key3");
```

##  Run on Windows:
bat file<br>
java -jar Plesk_Auto_Update_Trial_Key.jar "ip address MySQL" "user MySQL" "pass MySQL" "ip address SSH" "login SSH" "pass SSH" "database name mysql"

## Run on Linux (Debian/Ubuntu):
sudo apt-get install screen <br>
apt-get update && apt-get install -y gnupg2 -y <br>
sudo apt install software-properties-common -y <br>
sudo apt-get install apt-transport-https ca-certificates curl gnupg lsb-release -y <br>
sudo add-apt-repository ppa:linuxuprising/java <br>
sudo apt install oracle-java16-jdk <br>

screen -dmS pleskKey bash -c "java -jar Plesk_Auto_Update_Trial_Key.jar "ip address MySQL" "user MySQL" "pass MySQL" "ip address SSH" "login SSH" "pass SSH" "database name mysql" "

## Get free key:
https://www.plesk.com/plesk-free-download/ <br>
https://temp-mail.org/ru/ <br>

## Ideological inspirer

[yashodhank](https://github.com/yashodhank)

## FAQ & Troubleshooting

• On Windows/MacOS, install Oracle Java from [here](https://www.oracle.com/java/technologies/javase-downloads.html) or OpenJDK from [here](https://adoptopenjdk.net/).

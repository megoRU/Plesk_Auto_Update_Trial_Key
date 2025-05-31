# Plesk Auto Update Trial Key

![GitHub All Releases](https://img.shields.io/github/downloads/megoRU/Plesk_Auto_Update_Trial_Key/total)
[![GitHub stars](https://img.shields.io/github/stars/megoRU/Plesk_Auto_Update_Trial_Key)](https://github.com/megoRU/Plesk_Auto_Update_Trial_Key/stargazers)
[![GitHub license](https://img.shields.io/github/license/megoRU/Plesk_Auto_Update_Trial_Key)](https://github.com/megoRU/Plesk_Auto_Update_Trial_Key/blob/master/LICENSE)

## ⚠️ Disclaimer

**I am against the abuse of free licenses. This code is provided for informational purposes only. Using it likely violates Plesk's terms. The author is not responsible for any consequences or material losses.**

---

## 📌 Description

A tool that automatically updates trial keys for Plesk using SSH and MySQL.

---

## 🗄️ MySQL Table Structure

```sql
CREATE TABLE `Plesk` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `text` (`text`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

### Sample data:

```sql
INSERT INTO Plesk (text) VALUES ("Your Key");
INSERT INTO Plesk (text) VALUES ("Your Key2");
INSERT INTO Plesk (text) VALUES ("Your Key3");
```

---

## ▶️ Usage

### On Windows

Create a `.bat` file:

```bat
java -jar Plesk_Auto_Update_Trial_Key.jar "mysql_ip" "mysql_user" "mysql_pass" "ssh_ip" "ssh_user" "ssh_pass" "mysql_db"
```

### On Linux (Debian/Ubuntu)

```bash
sudo apt-get update
sudo apt-get install -y screen gnupg2 software-properties-common \
    apt-transport-https ca-certificates curl lsb-release

sudo add-apt-repository ppa:linuxuprising/java
sudo apt install oracle-java16-jdk -y

screen -dmS pleskKey bash -c \
"java -jar Plesk_Auto_Update_Trial_Key.jar 'mysql_ip' 'mysql_user' 'mysql_pass' 'ssh_ip' 'ssh_user' 'ssh_pass' 'mysql_db'"
```

---

## 🔑 Get Free Trial Key

* [Official Plesk Trial](https://www.plesk.com/plesk-free-download/)
* [Temporary Email](https://temp-mail.org/)

---

## 💡 Inspired by

[@yashodhank](https://github.com/yashodhank)

---

## ❓ FAQ & Troubleshooting

* Install Java:

  * [Oracle Java](https://www.oracle.com/java/technologies/javase-downloads.html)
  * [OpenJDK](https://adoptopenjdk.net/)

---

## 📝 License

[MIT License](LICENSE)

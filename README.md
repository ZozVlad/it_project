# SendUp Mailer
SendUp is an business application which allows you to automate the process of sending letters and followups. It saves the time and increase your productivity. Can be used in small and medium businesses for marketing, business development, SMM, etc.

Estimated time - 200 hours.

# Installation

1) Open and execute script "campaign_collection.sql". It will create main database.
`Note : change login and password for MySQL in 'Constants' class`.
2) Install Maven. Download Binary zip archive "apache-maven-3.6.3-bin.zip".
Link: https://maven.apache.org/download.cgi
`Note : add \bin directory to system variable "Path"`.
3) Install OpenJDK 11.
4) Download JavaFX 11.0.2.
5) Additional libraries: 
    * Apache POI
    * Java Activation Framework
    * Java Mail
    * Mysql connector 
    * AnimateFX
    * TrayNotification
6) Usind command line go into project directory and write 
```sh
$ mvn compile
```
7) Run application
```sh
mvn exec:java -Dexec.mainClass="main.java.Main"
```
# The project was made by
 - Zozulya Vladislav - Team Lead / UI designer
 - Kabak Oleksandr - Developer / Tester
 - Klishchov Bogdan - Developer / Tester
 - Kulyk Daniil - Developer / Tester
 - Osetrov Andrey - Developer / Tester

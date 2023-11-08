# CinemaHall


Это приложение можно использовать для заказа билетов в кинотеатре.
## Используемые технологии:
![java](https://img.shields.io/badge/Java--17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot--2.7.3-F2F4F9?style=for-the-badge&logo=spring-boot)
![Bootstrap](https://img.shields.io/badge/Bootstrap--5.2.2-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)
![PostgresSQL](https://img.shields.io/badge/PostgreSQL--14-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-1.18.24-green?style=for-the-badge&logo=lombok&logoColor=white)
![Liquibase](https://img.shields.io/badge/Liquibase-4.9.1-red?style=for-the-badge&logo=liquibase&logoColor=white)



Перед запуском установите:
- PostgreSQL 14
- Java 17
- Apache Maven 3.x

## Запуск приложения

1. Создайте базу данных cinemasb:
```sql
create database cinemasb;
```

2. Запуск приложения производится с использованием maven.
   Перейдите в корневой каталог проекта и в командной строке
   выполните команды:
```
    mvn clean install
    mvn spring-boot:run
```
### Описание:
Пользователю предлагается сначала зарегистрироваться
![reg page](images/reg.jpg)

Если такого пользователя еще нет и регистрация прошла успешно
![regTrue page](images/regtrue.jpg)

Если пользователь с такой почтой уже был зарегистрирован
![regFalse page](images/regfalse.jpg)

После регистрации пользователь будет перенаправлен на главную страницу
![index page](images/index.jpg)

Ему надо будет пройти авторизацию
![login page](images/login.jpg)

Если авторизация прошла неудачно, пользователь будет проинформирован
![loginFalse page](images/loginfalse.jpg)

После успешной авторизации пользователь может выбрать сеанс
![films page](images/enter.jpg)

Можно выбрать свободное место
![choice page](images/choice.jpg)

И подтвердить покупку
![buy page](images/buy.jpg)

Если покупка прошла успешно, то купленное им место будет отображаться как занятое
![buyTrue page](images/buytrue.jpg)

Если же кто-то опередил пользователя и место оказалось уже выкуплено, то появится сообщение
![byeFalse page](images/buyfalse.jpg)

Связаться со мной можно по электронной почте a_esipov_it@list.ru
или в телеграм  @Alex46volokno


<div id="socials" align="center">
    <!-- <a href="linkedin-url">
    <img src="https://img.shields.io/badge/LinkedIn-blue?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
  </a> -->

  <a href="https://t.me/alex46volokno">
    <img src="https://img.shields.io/badge/Telegram-blue?style=for-the-badge&logo=telegram&logoColor=white" alt="Telegram"/>
  </a>
</div>

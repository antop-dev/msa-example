# msa-example

![Imgur](https://i.imgur.com/9mqE4XN.png)

사내 교육 MSA

`JDK`나 `IntelliJ` 관련 설정은 건너뛴다. 예제가 `Lombok`을 사용하므로 `IntelliJ + Lombok` 은 알아서 설정하자.

## Import Project

Clone project

![Imgur](https://i.imgur.com/my8XlrD.png)

Yes!

![Imgur](https://i.imgur.com/jIPNJvu.png)

`Create project from existing sources` 선택

![Imgur](https://i.imgur.com/5HbxD6a.png)

Next

![Imgur](https://i.imgur.com/y70JBtg.png)

아무것도 선택하지 않는다.

![Imgur](https://i.imgur.com/vqCIlDe.png)

메인 디렉토리만 `Import`된 상태

![Imgur](https://i.imgur.com/Ke1s0Cy.png)

모듈을 하나하나 추가 해준다.

![Imgur](https://i.imgur.com/FzZE8vz.png)

하위 디렉토리에 있는 프로젝트를 하나 선택한다.

![Imgur](https://i.imgur.com/QrSPOPL.png)

`Gradle` 선택

![Imgur](https://i.imgur.com/TC6W2Oo.png)

대충... Finish

![Imgur](https://i.imgur.com/onl9b21.png)

모든 프로젝트를 추가해준다.

![Imgur](https://i.imgur.com/fK3BI3n.png)

`Project Structure`에서 `Project SDK` 설정

![Imgur](https://i.imgur.com/liLiTI2.png)

모듈 구조가 아래와 같이 딱딱 떨어져야 정상!

![Imgur](https://i.imgur.com/8c4anFt.png)

## Install RabbitMQ

`Windows`에서 `RabbitMQ`를 사용하기 위해서는 `Erlang`이 필요하다.

참조
* [Downloading and Installing RabbitMQ](https://www.rabbitmq.com/download.html)
* [Erlang Programming Language](https://www.erlang.org/downloads)
* [RabbitMQ Erlang Version Requirements](https://www.rabbitmq.com/which-erlang.html)

환경 변수 설정

![Imgur](https://i.imgur.com/mB0lqvB.png)

![Imgur](https://i.imgur.com/ORQw125.png)

실행

```bash
> rabbitmq-server.bat

  ##  ##
  ##  ##      RabbitMQ 3.7.15. Copyright (C) 2007-2019 Pivotal Software, Inc.
  ##########  Licensed under the MPL.  See https://www.rabbitmq.com/
  ######  ##
  ##########  Logs: C:/Users/antop/AppData/Roaming/RabbitMQ/log/RABBIT~1.LOG
                    C:/Users/antop/AppData/Roaming/RabbitMQ/log/rabbit@ANTOP-GRAM_upgrade.log

              Starting broker...
 completed with 3 plugins.
```

사용자 추가, Virtual Host 추가, 권한 추가 (**서버를 먼저 띄워야 한다**)

```bash
> rabbitmqctl add_user config config
Adding user "config" ...

> rabbitmqctl set_user_tags config administrator
Setting tags for user "config" to [administrator] ...

> rabbitmqctl add_vhost /config
Adding vhost "/config" ...

> rabbitmqctl set_permissions -p /config config "." "." ".*"
Setting permissions for user "config" in vhost "/config" ...
```

GUI Console 사용하기

```bash
> rabbitmq-plugins enable rabbitmq_management
```
http://localhost:15672 접속하여 `config`/`config`

![Imgur](https://i.imgur.com/RzSGBvd.png)

로그인 성공하면 아래와 같은 `Overview`를 볼 수 있다.

![Imgur](https://i.imgur.com/IgFQCOw.png)


## 실행 순서
U
※ 아래 모든 프로젝트는 개발(`dev`) 프로파일로 실행한다.

![Imgur](https://i.imgur.com/XMcLKv9.png)

1. Cloudconfig
2. Service-discovery: [http://localhost:9000](http://localhost:9000)
3. Api-gateway
4. Api-user / Api-product / Api-order / Api-delivery
5. [Hystrix-dashboard](http://localhost:8998/hystrix/monitor?stream=http://localhost:8998/turbine.stream) (이건 딱히 순서 상관 없는 듯?)

전부 실행된 모습 (`Api-delivery`는 3개 띄워봄...)

![Imgur](https://i.imgur.com/Lae0xFq.png)

Eureka

![Imgur](https://i.imgur.com/Ms1Fx1z.png)

이제 예제를 즐길 준비가 되었다. ㅠㅠ

## Web GUI

* Eureka: [http://localhost:9000](http://localhost:9000)
* Hystrix Dashboard: [http:&#47;&#47;localhost:8998&#47;hystrix&#47;monitor&#63;stream=http:&#47;&#47;localhost:8998&#47;turbine.stream](http://localhost:8998/hystrix/monitor?stream=http://localhost:8998/turbine.stream)
* RabbitMQ: [http://localhost:15672](http://localhost:9000)

API test
* User API: `http://localhost:8081/v1/user/{id}`
* Product API: `http://localhost:8082/v1/product/{id}`
* Order API: `http://localhost:8083/v1/order/{id}`
* Delivery API: `http://localhost:8084/v1/delivery/{id}`
* API Gateway: `http://localhost:8000/{서비스도메인}/{API Endpoint}`
  - `http://localhost:8000/user/v1/user/u001`
  - `http://localhost:8000/product/v1/product/0001`
  - `http://localhost:8000/order/v1/order?userId=u0002`
  - `http://localhost:8000/delivery/v1/delivery/0001`
  
## 수정사항

Order 서비스에서 각각 User, Product 서비스한테 요청 후 객체로 변환 하다가 에러가 난다.

원인은 `Jackson`에서 변환을 하려면 해당 클래는 빈 생성자가 필요하지만 `Lombok`의 `@Builder`를 사용했다.

https://www.thecuriousdev.org/lombok-builder-with-jackson/
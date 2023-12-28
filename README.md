# Лабораторная работа #2

![GitHub Classroom Workflow](../../workflows/GitHub%20Classroom%20Workflow/badge.svg?branch=master)

## Microservices

### Формулировка

В рамках второй лабораторной работы _по вариантам_ требуется реализовать систему, состоящую из нескольких
взаимодействующих друг с другом сервисов.

### Требования

1. Каждый сервис имеет свое собственное хранилище, если оно ему нужно. Для учебных целей можно использовать один
   instance базы данных, но каждый сервис работает _только_ со своей логической базой. Запросы между базами _запрещены_.

2. Для межсервисного взаимодействия использовать HTTP (придерживаться RESTful). Допускается использовать и другие
   протоколы, например grpc, но это требуется согласовать с преподавателем.

3. Выделить **Gateway Service** как единую точку входа и межсервисной коммуникации. Горизонтальные запросы между
   сервисами делать _нельзя_.

4. На каждом сервисе сделать специальный endpoint `GET /manage/health`, отдающий 200 ОК, он будет использоваться для
   проверки доступности сервиса (в [Github Actions](.github/workflows/classroom.yml) в скрипте проверки готовности всех
   сервисов [wait-script.sh](scripts/wait-script.sh).

   ```shell
   "$path"/wait-for.sh -t 120 "http://localhost:$port/manage/health" -- echo "Host localhost:$port is active"
   ```

5. Код хранить на Github, для сборки использовать Github Actions.

6. Gateway Service должен запускаться на порту 8080, остальные сервисы запускать на портах 8050, 8060, 8070.

7. Каждый сервис должен быть завернут в docker.

8. В [docker-compose.yml](docker-compose.yml) прописать сборку и запуск docker контейнеров.

9. В [classroom.yml](.github/workflows/classroom.yml) дописать шаги на сборку и прогон unit-тестов.

10. Для автоматических прогонов тестов в файле [autograding.json](.github/classroom/autograding.json)
    и [classroom.yml](.github/workflows/classroom.yml) заменить `<variant>` на ваш вариант.

### Пояснения

1. Для разработки можно использовать Postgres в docker, для этого нужно запустить docker compose up -d, поднимется
   контейнер с Postgres 13, и будут созданы соответствующие вашему варианту (описанные в
   файлах [schema-$VARIANT](postgres/scripts)) базы данных и пользователь `program`:`test`.
2. Для создания базы нужно прописать в [20-create-schemas.sh](postgres/20-create-databases.sh) свой вариант задания в
3. Docker Compose позволяет выполнять сборку образа, для этого нужно прописать
   блок [`build`](https://docs.docker.com/compose/compose-file/build/).
4. Горизонтальную коммуникацию между сервисами делать нельзя.
5. Интеграционные тесты можно проверить локально, для этого нужно импортировать в Postman
   коллекцию `<variant>/postman/collection.json`) и `<variant>/postman/environment.json`.

![Services](images/services.png)

Предположим, у нас сервисы `UserService`, `OrderService`, `WarehouseService` и `Gateway`:

* На `Gateway` от пользователя `Alex` приходит запрос `Купить товар с productName: 'Lego Technic 42129`.
* `Gateway` -> `UserService` проверяем что пользователь существует и получаем `userUid` пользователя по `login: Alex`.
* `Gateway` -> `WarehouseService` получаем `itemUid` товара по `productName` и резервируем его для заказа.
* `Gateway` -> `OrderService` с `userUid` и `itemUid` и создаем заказ с `orderUid`.
* `Gateway` -> `WarehouseService` с `orderUid` и переводим товар `itemUid` из статуса `Зарезервировано` в
  статус `Заказан` и прописываем ссылку на `orderUid`.

### Прием задания

1. При получении задания у вас создается fork этого репозитория для вашего пользователя.
2. После того как все тесты успешно завершатся, в Github Classroom на Dashboard будет отмечено успешное выполнение
   тестов.

### Варианты заданий

Варианты заданий берутся исходя из формулы:
(номер в [списке группы](https://docs.google.com/spreadsheets/d/1BT5iLgERiWUPPn4gtOQk4KfHjVOTQbUS7ragAJrl6-Q)-1) % 4)+1.

1. [Flight Booking System](v1/README.md)
1. [Hotels Booking System](v2/README.md)
1. [Car Rental System](v3/README.md)
1. [Library System](v4/README.md)



实验室#2
##微服务

### 措辞

作为第二个实验室工作的一部分_通过选项_需要实施一个由多个组成的系统
服务相互交互。

＃＃＃ 要求

1. 每个服务都有自己的存储（如果需要的话）。出于教育目的，您可以使用一个
   实例数据库，但每个服务仅使用其自己的逻辑数据库。 _禁止_数据库之间的请求。
2. 使用 HTTP 进行服务间通信（坚持 RESTful）。其他也可以用
   协议，例如 grpc，但这必须得到老师的同意。
3. 选择**网关服务**作为单点入口和服务间通信。之间的水平请求
   你无法通过服务来做到这一点。
   4.在每个服务上，创建一个返回 200 OK 的特殊端点“GET /manage/health”，它将用于
   检查脚本中的服务可用性（在 [Github Actions](.github/workflows/classroom.yml) 中），用于检查所有服务的准备情况
   服务[wait-script.sh](scripts/wait-script.sh)。
   "$path"/wait-for.sh -t 120 "http://localhost:$port/manage/health" -- echo "Host localhost:$port is active"

4. 将代码存储在Github上，使用Github Actions进行构建。
5. 网关服务应在8080端口启动，其他服务应在8050、8060、8070端口启动。
   7.每个服务都必须用docker包装起来。
6. 在 [docker-compose.yml](docker-compose.yml) 中注册 docker 容器的组装和启动。
7. 在 [classroom.yml](.github/workflows/classroom.yml) 中添加组装和运行单元测试的步骤。
8. 对于自动测试运行在文件 [autograding.json](.github/classroom/autograding.json)
     并 [classroom.yml](.github/workflows/classroom.yml) 将 `<variant>` 替换为您的变体。

### 说明

1.开发时可以在docker中使用Postgres，为此你需要运行docker compose up -d，它会上升
   带有 Postgres 13 的容器，并且将创建与您的选项相对应的容器（在
   [schema-$VARIANT](postgres/scripts)) 数据库文件和用户 `program`:`test`。

2. 要创建数据库，您需要在 [20-create-schemas.sh](postgres/20-create-databases.sh) 中注册您的任务版本
3. Docker Compose 允许您构建镜像；为此您需要注册
   块[`build`](https://docs.docker.com/compose/compose-file/build/)。
   4、服务之间无法进行横向通信。
   5.集成测试可以在本地进行测试；为此，您需要将它们导入Postman
   集合 `<variant>/postman/collection.json`) 和 `<variant>/postman/environment.json`。

假设我们有服务“UserService”、“OrderService”、“WarehouseService”和“Gateway”：

* 在“Gateway”上，用户“Alex”收到请求“购买产品名称为“Lego Technic 42129”的产品。

* `Gateway` -> `UserService` 检查用户是否存在，并通过 `login: Alex` 获取用户的 `userUid`。

* `Gateway` -> `WarehouseService` 通过 `productName` 获取产品的 `itemUid` 并为订单保留。

* `Gateway` -> `OrderService` 与 `userUid` 和 `itemUid` 并使用 `orderUid` 创建订单。

* `Gateway` -> `WarehouseService` 和 `orderUid` 并将项目 `itemUid` 从状态 `Reserved` 转移到
  状态“已订购”并输入“orderUid”的链接。

  

### 接受任务

1. 当您收到任务时，您为您的用户创建此存储库的分支。
2. 所有测试成功完成后，Github Classroom Dashboard 中将标记成功
   测试。

### 任务选项

任务选项根据以下公式进行：
（[群组列表](https://docs.google.com/spreadsheets/d/1BT5iLgERiWUPPn4gtOQk4KfHjVOTQbUS7ragAJrl6-Q)-1中的数字) % 4)+1。

1.【航班预订系统】(v1/README.md)

2. [酒店预订系统](v2/README.md)
3.【租车系统】(v3/README.md)
4.【图书馆系统】(



##lab2-v3 Car Rental System

Система предоставляет пользователю возможность забронировать автомобиль на выбранные даты.

### Структура Базы Данных

#### Cars Service

Сервис запускается на порту 8070.

```sql
CREATE TABLE cars
(
    id                  SERIAL PRIMARY KEY,
    car_uid             uuid UNIQUE NOT NULL,
    brand               VARCHAR(80) NOT NULL,
    model               VARCHAR(80) NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    power               INT,
    price               INT         NOT NULL,
    type                VARCHAR(20)
        CHECK (type IN ('SEDAN', 'SUV', 'MINIVAN', 'ROADSTER')),
    availability        BOOLEAN     NOT NULL
);
```

#### Rental Service

Сервис запускается на порту 8060.

```sql
CREATE TABLE rental
(
    id          SERIAL PRIMARY KEY,
    rental_uid  uuid UNIQUE              NOT NULL,
    username    VARCHAR(80)              NOT NULL,
    payment_uid uuid                     NOT NULL,
    car_uid     uuid                     NOT NULL,
    date_from   TIMESTAMP WITH TIME ZONE NOT NULL,
    date_to     TIMESTAMP WITH TIME ZONE NOT NULL,
    status      VARCHAR(20)              NOT NULL
        CHECK (status IN ('IN_PROGRESS', 'FINISHED', 'CANCELED'))
);
```

#### Payment Service

Сервис запускается на порту 8050.

```sql
CREATE TABLE payment
(
    id          SERIAL PRIMARY KEY,
    payment_uid uuid        NOT NULL,
    status      VARCHAR(20) NOT NULL
        CHECK (status IN ('PAID', 'CANCELED')),
    price       INT         NOT NULL
);
```

### Описание API

#### Получить список всех доступных для бронирования автомобилей

Если передан флаг `showAll = true`, то выводить автомобили в резерве (`availability = false`).

```http request
GET {{baseUrl}}/api/v1/cars&page={{page}}&size={{size}}
```

#### Получить информацию о всех арендах пользователя

```http request
GET {{baseUrl}}/api/v1/rental
X-User-Name: {{username}}
```

#### Информация по конкретной аренде пользователя

При запросе требуется проверить, что аренда принадлежит пользователю.

```http request
GET {{baseUrl}}/api/v1/rental/{{rentalUid}}
X-User-Name: {{username}}
```

#### Забронировать автомобиль

Пользователь вызывает метод `GET {{baseUrl}}/api/v1/cars` и выбирает нужный автомобиль и в запросе на аренду передает:

* `carUid` (UUID автомобиля) – берется из запроса `/cars`;
* `dateFrom` и `dateTo` (дата начала и конца аренды) – задается пользователем.

Система проверяет, что автомобиль с таким `carUid` существует и резервирует его (флаг `availability = false`). При
повторном вызове `GET {{baseUrl}}/api/v1/cars` этот автомобиль будет скрыт в выдаче результатов пока не будет передан
флаг `showAll = true`.

Считается количество дней аренды (`dateFrom` – `dateTo`), вычисляется общая сумма бронирования, выполняется запрос в
Payment Service и создается новая запись об оплате. В сервисе Rental Service создается запись с информацией о
бронировании.

```http request
POST {{baseUrl}}/api/v1/rental
Content-Type: application/json
X-User-Name: {{username}}

{
  "carUid": "109b42f3-198d-4c89-9276-a7520a7120ab",
  "dateFrom": "2021-10-08",
  "dateTo": "2021-10-11"
}
```

#### Завершение аренды автомобиля

* С автомобиля снимается резерв.
* В Rental Service аренда помечается завершенной (статус `FINISHED`).

```http request
POST {{baseUrl}}/api/v1/rental/{{rentalUid}}/finish
X-User-Name: {{username}}
```

#### Отмена аренды автомобиля

* С автомобиля снимается резерв.
* В Rental Service аренда помечается отмененной (статус `CANCELED`).
* В Payment Service запись об оплате помечается отмененной (статус `CANCELED`).

```http request
DELETE {{baseUrl}}/api/v1/rental/{{rentalUid}}
X-User-Name: {{username}}
```

Описание в формате [OpenAPI](%5Binst%5D%5Bv3%5D%20Car%20Rental%20System.yml).

### Данные для тестов

Создать данные для тестов:

```yaml
cars:
  – id: 1
    car_uid: "109b42f3-198d-4c89-9276-a7520a7120ab"
    brand: "Mercedes Benz"
    model: "GLA 250"
    registration_number: "ЛО777Х799"
    power: 249
    type: "SEDAN"
    price: 3500
    available: true
```





## 租车系统

该系统为用户提供了在选定日期预订汽车的机会。

### 数据库结构

#### 汽车服务

该服务在端口 8070 上启动。
CREATE TABLE cars
(
    id                  SERIAL PRIMARY KEY,
    car_uid             uuid UNIQUE NOT NULL,
    brand               VARCHAR(80) NOT NULL,
    model               VARCHAR(80) NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    power               INT,
    price               INT         NOT NULL,
    type                VARCHAR(20)
        CHECK (type IN ('SEDAN', 'SUV', 'MINIVAN', 'ROADSTER')),
    availability        BOOLEAN     NOT NULL
);



#### 租赁服务

该服务在端口 8060 上启动。

CREATE TABLE rental
(
    id          SERIAL PRIMARY KEY,
    rental_uid  uuid UNIQUE              NOT NULL,
    username    VARCHAR(80)              NOT NULL,
    payment_uid uuid                     NOT NULL,
    car_uid     uuid                     NOT NULL,
    date_from   TIMESTAMP WITH TIME ZONE NOT NULL,
    date_to     TIMESTAMP WITH TIME ZONE NOT NULL,
    status      VARCHAR(20)              NOT NULL
        CHECK (status IN ('IN_PROGRESS', 'FINISHED', 'CANCELED'))
);


#### 支付服务

该服务在端口 8050 上启动。
CREATE TABLE payment
(
    id          SERIAL PRIMARY KEY,
    payment_uid uuid        NOT NULL,
    status      VARCHAR(20) NOT NULL
        CHECK (status IN ('PAID', 'CANCELED')),
    price       INT         NOT NULL
);




### API说明

#### 获取所有可供预订的汽车列表

如果传递标志“showAll = true”，则显示备用车辆（“availability = false”）。
<!-- GET {{baseUrl}}/api/v1/cars&page={{page}}&size={{size}} -->



获取所有用户的租赁信息
GET {{baseUrl}}/api/v1/rental
X-User-Name: {{username}}



#### 用户特定的租赁信息

出现提示时，需要验证租约是否属于用户。

GET {{baseUrl}}/api/v1/rental/{{rentalUid}}
X-User-Name: {{username}}



#### 预订汽车

用户调用 GET {{baseUrl}}/api/v1/cars 方法并选择所需的汽车并发送租赁请求：

* `carUid` (汽车 UUID) – 取自 `/cars` 请求；
* `dateFrom` 和 `dateTo` （租约的开始和结束日期） – 由用户指定。

系统检查具有此“carUid”的汽车是否存在并保留它（标志“availability = false”）。 在
再次调用`GET {{baseUrl}}/api/v1/cars`，这辆车将隐藏在结果中，直到它被转移
标志“showAll = true”。


计算租赁天数（`dateFrom` – `dateTo`），计算预订总金额，执行请求
付款服务并创建新的付款记录。 在租赁服务中创建一条记录，其中包含以下信息
预订。


POST {{baseUrl}}/api/v1/rental
Content-Type: application/json
X-User-Name: {{username}}

{
  "carUid": "109b42f3-198d-4c89-9276-a7520a7120ab",
  "dateFrom": "2021-10-08",
  "dateTo": "2021-10-11"
}



#### 租车结束

* 储备已从车辆上移除。
* 在租赁服务中，租赁被标记为已完成（状态“已完成”）。
  POST {{baseUrl}}/api/v1/rental/{{rentalUid}}/finish
  X-User-Name: {{username}}



#### 取消租车

<!-- * 储备已从车辆上移除。 -->
<!-- * 在租赁服务中，租赁被标记为已取消（状态“已取消”）。 -->
<!-- * 在支付服务中，支付记录被标记为已取消（状态“已取消”）。 -->
<!-- DELETE {{baseUrl}}/api/v1/rental/{{rentalUid}} -->
<!-- X-User-Name: {{username}} -->


格式为 [OpenAPI](%5Binst%5D%5Bv3%5D%20Car%20Rental%20System.yml) 的描述。

＃＃＃ 测试数据

创建测试数据：

cars:
  – id: 1
    car_uid: "109b42f3-198d-4c89-9276-a7520a7120ab"
    brand: "Mercedes Benz"
    model: "GLA 250"
    registration_number: "ЛО777Х799"
    power: 249
    type: "SEDAN"
    price: 3500
    available: true

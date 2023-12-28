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


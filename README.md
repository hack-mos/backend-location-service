# location-service

Сервис, отвечающий за хранение информации о такси, расписании и причалах

## Общая инструкция

Для успешного запуска приложения необходимо иметь предустановленный и запущенный Docker

### Порядок действий

- Скачать и разархивировать `hack-docker.zip`
- Внутри папки выполнить команду `docker compose up -d`
- Открыть SwaggerUI соответствующих
  сервисов: [Location-Service](http://localhost:8080/q/swagger-ui), [Route Service](http://localhost:8081/q/swagger-ui/)
- **Важно!** Для большинства запросов требуется аутентификация пользователя. Пользователи в Keycloak уже созданы, однако
  необходимо получить `access_token` и передавать его в запрос
- Доступ к Keycloak можно получить по `http://localhost:8543/admin/master/console/`

**Примечания:**
В Keycloak заведены три пользователя (далее - логин/пароль):

1. boss/boss (права Администратора)
2. client/client (права Клиента)
3. driver/driver (права Перевозчика)

Для получения токена необходимо:

- Отправить запрос на получение токена:

```curl
curl --request POST \
  --url http://localhost:8543/realms/master/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data grant_type=password \
  --data client_id=hack \
  --data username=boss \
  --data password=boss \
  --data client_secret=A2DR0KSSncEBlUYUykWOJq5VArcCkmaT
```

где `username` - имя пользователя (client / admin / driver), `password` - пароль пользователя (client / admin / driver)

Пример запроса по получению всех причалов (с пагинацией):

```curl
curl --request GET \
  --url 'http://localhost:8080/api/v1/place?page=0&size=10' \
  --header 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDWDBlZWVVMnRKRERkZjFQOGRMbWd5Q3dhVUlrOGJvTnVRb2dFaDZ3Rl9FIn0.eyJleHAiOjE3MzMwNDc0MDAsImlhdCI6MTczMzAxMTQwMCwianRpIjoiMzU0MTk1ZWQtMzAwNC00MDA5LWE3NzItZDUxMTNlNGY4Y2U1IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4NTQzL3JlYWxtcy9tYXN0ZXIiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiYzNkZjU5NTItM2UxMi00NmFiLWI1ZWMtMTNhYzlmYzhiNjJjIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiaGFjayIsInNpZCI6IjNiMDQzYzhhLWIzYjItNGNhOC1iYTFjLTJlMzM5YzQyZjAyYSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtbWFzdGVyIiwib2ZmbGluZV9hY2Nlc3MiLCJIQUNLX0FETUlOIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IlFXZXdxZSBRV0V3cWVxd2UiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJib3NzIiwiZ2l2ZW5fbmFtZSI6IlFXZXdxZSIsImZhbWlseV9uYW1lIjoiUVdFd3FlcXdlIn0.XyImNiGbvCKSmZzQEG3ssYoh25pwN6cj1tbqEmJXzoRYsruXyN9bvRZuAUjYstiYdK3oOr290WtIFzTgDBEYm9wNzOf_IUoJG2khsq8CsWXDxwYbt0AGXPa4EY1v0Gy0gQrrDYEKG3VSJIUEDTme3Gw71C-IXkXzYUcSdHNlCJqNVbSeNvBrl5HHuD60ZNSAmhlIhFzDwJI1KDLsZo-v7cGtxFHXu15uLS-pMUnoznT7HYQoRyt2nchTdv-2Qg5aBx4CW7yr5ODITOjTXn-jXUNGcqJ-1-kxJfZmQUDyLu8NfPfXlBr1WVrEg1cgda01HVkS7dv0lKur5WAaMKB6fg' \
  --header 'accept: application/json'
```
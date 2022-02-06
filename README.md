## JWT authentication
* This an example how to set up jwt authentication with access and refresh token.
* Related blog post is available here: https://codingally.tech/spring-boot-jwt-authentication/

## To run this project
1. You need jre 11 and Docker
2. in `/src/test/resources` folder run `docker-compose up -d`, this starts a database in the Docker container
3. Run an application

## Api endpoints
* POST: /user/create request(email, password)
  * `curl -i -X POST "http://localhost:8080/user/create" -H "Content-Type: application/json" --data-binary "{\"email\":\"aa@gmail.com\",\"password\":\"test123\"}"
    `
* POST: /auth/login request(email, password) response(accessToken refreshToken)
  * `curl -i -X POST "http://localhost:8080/auth/login" -H "Content-Type: application/json" --data-binary "{\"email\":\"aa@gmail.com\",\"password\":\"test123\"}"
    `
* POST: /auth/refresh request(refreshToken), response(accessToken)
  * `curl -i -X POST "http://localhost:8080/auth/refresh" -H "Content-Type: application/json" --data-binary "{\"refreshToken\":\"tXr5njmMcnsiNBdkmfTKqkHOr8B0WqwS0Ew+7P0g9fviYD2l+KE+YGu6zAxk76mH4pLQehYtiBGpy0qgdqCCWg==\"}"`
* GET: /logout
  * `curl -i -X GET "http://localhost:8080/logout" -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIklEIjoiYzczY2EwZWMtZDg5MS00YzJmLWJiYmEtMzU5MzJkNjE1ODE5IiwiRU1BSUwiOiJhYUBnbWFpbC5jb20iLCJleHAiOjE2NDQxMDQyODZ9.Xyu3D9JFW-z-9CFc3FuvFMaRVNU-n8PMsA3-WCCwOOA"`
* GET: /user
  * `curl -i -X GET "http://localhost:8080/user" -H "Content-Type: application/json" -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsIklEIjoiYzczY2EwZWMtZDg5MS00YzJmLWJiYmEtMzU5MzJkNjE1ODE5IiwiRU1BSUwiOiJhYUBnbWFpbC5jb20iLCJleHAiOjE2NDQxMDQyODZ9.Xyu3D9JFW-z-9CFc3FuvFMaRVNU-n8PMsA3-WCCwOOA"`

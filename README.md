# url-shortener

## Requirements
we would need to have:

- JDK 16
- Gradle 7.0.2
- Docker 20.10.5
- Docker compose version 1.29.0

## How to Run it
### Manually 
We need to run the next two command 
```bash
$ ./gradlew bootBuildImage

$ docker-compose up -d
```
### with the run.sh Script 
running the bash script called run.sh
```bash
$ ./run.sh
```

## Example of the requests
To encode the URL we would need to run the next request
```bash
$ curl --location --request POST 'http://localhost:8080/shortener/v1/create' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url":"https://start.spring.io/"
}'

{"id":1,"original":"https://start.spring.io/","shorter":"E1D7C9C56323A1549174427196722149","created":"2021-06-24T20:43:37.428+00:00"}%                                                                                                                                                     

```

To decode and redirect we would use the next one
```bash
$ curl --location --request GET 'http://localhost:8080/shortener/v1/E1D7C9C56323A1549174427196722149'
```

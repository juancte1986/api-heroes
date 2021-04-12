## Environment:
- Ubuntu 20
- Java version: 11
- Maven version: 3.*
- Spring Boot version: 2.2.1.RELEASE`

## Requirements:
Desarrollar una API que permita hacer un mantenimiento de súper
héroes.


`POST` request to `/login` :
* Retorna un token para autorizar el acceso a toda la api de heroes
* Se debe enviar el json data {"username":"admin", "password":"1234"}

`POST` request to `/heroes` :
* En el Authorization del encabezado de la solictud HTTP agregar el token. Sintaxis Authorization: Bearer eyJhbGciOiJIUzI1NiIXVCJ9TJV.. 
* Crea un nuevo registro en h2
* Se debe enviar el json data {"name":"superman"}
* El codigo de respuesta es 201 y en el body de la respuesta se retorna el registro creado, incluido el id auto-incremental 

`PUT` request to `/heroes` :
* En el Authorization del encabezado de la solictud HTTP agregar el token. Sintaxis Authorization: Bearer eyJhbGciOiJIUzI1NiIXVCJ9TJV.. 
* Actualiza el registro en h2
* Se debe enviar el json data {"id":1,"name":"superman 2"}
* El codigo de respuesta es 200 y en el body de la respuesta se retorna el registro actualizado 


`DELETE` request to `/heroes/<id>` :
* En el Authorization del encabezado de la solictud HTTP agregar el token. Sintaxis Authorization: Bearer eyJhbGciOiJIUzI1NiIXVCJ9TJV.. 
* Elimina el registro en h2 
* El codigo de respuesta es 200 y si el registro no existe es 404


`GET` request to `/heroes` :
* En el Authorization del encabezado de la solictud HTTP agregar el token. Sintaxis Authorization: Bearer eyJhbGciOiJIUzI1NiIXVCJ9TJV.. 
* El body de la respuesta es una lista de todos los super heroes.
* El codigo de respuesta es 200


`GET` request to `/heroes/<id>` :
* En el Authorization del encabezado de la solictud HTTP agregar el token. Sintaxis Authorization: Bearer eyJhbGciOiJIUzI1NiIXVCJ9TJV.. 
* El body de la respuesta es el unico super heroe por id
* El codigo de respuesta es 200

`GET` request to `/heroes/findByName?searchText=su` :
* En el Authorization del encabezado de la solictud HTTP agregar el token. Sintaxis Authorization: Bearer eyJhbGciOiJIUzI1NiIXVCJ9TJV.. 
* El body de la respuesta es una lista de super heroes que contiene el valor del parametro enviado en la peticion.
* El codigo de respuesta es 200


## Commands 
- run: 
mvn clean package; java -jar target/api-heroes-1.0.jar

- install: 
mvn clean install
- test: 
mvn clean test

- dokerizar:
mvn clean package dockerfile:build

- run docker:
docker-compose up -d
```

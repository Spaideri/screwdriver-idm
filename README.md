# screwdriver-idm

Identity management micro service designed for screwdriver

## API

##Authenticate
Method for authenticating user

#### Authenticate Request
```sh
Path: /authenticate
Method: HTTP POST
Content-Type: application/json
Body: {"username":"sampleusername","password":"samplepassword"}
```
#### Authenticate Success Response
```sh
HTTP Status Code: 200
Content-Type: application/json
Response Body: {"token":"<Base64 encoded authentication token>"}
```
#### Authenticate Failure Response
```sh
HTTP Status Code: 401
Content-Type: application/json
```

###Validate
Method for validating given authentication token

#### Validate Request
```sh
Path: /validate?token=<Base64 encoded authentication token to be validated>
HTTP Method: GET
```
#### Validate Success Response
```sh
HTTP Status Code: 200
Content-Type: application/json
Response Body: {"valid":true}
```
#### Validate Failure Response
```sh
HTTP Status Code: 401
Content-Type: application/json
Response Body: {"valid":false}
```
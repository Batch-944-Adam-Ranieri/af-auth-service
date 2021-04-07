# af-auth-service
Microservice responsible for authenticating jwts and for the creation of new users.

## Environment Variables
Need to add a `.env` file in this directory or set in you system environment variables if being done locally.
It should include: 
`
- AF_SECRET=[INSERT YOUR OWN SECRET HERE]
- AF_EMAIL_SECRET=[PASSWORD FOR THE EMAIL TO BE USED]
- AssignForceAuth=[DATABASE URL HERE]
- AssignForceSalt=[YOUR KEY WORD HERE FOR PASSWORD HASHING]
`
## Routes, Requests, and Responses
### User Registration
uri: `/register`

Expected body:
```json
{
  "email": "email"
}
```
This route creates a new user in the database with the status of "pending" from the request body.
Returns a JSON with a user in the format of:
```json
{
    "userId": id,
    "email": "email",
    "password": null,
    "status": "status",
    "role": "role"
}
```
### Resolve
uri: `/resolve`

This route will retrive all users with the status of "pending".
Returns a JSON with a set of users in the format of:
```json
{
    "userId": id,
    "email": "email",
    "password": null,
    "status": "status",
    "role": "role"
}
```

### Approve/Deny
uri: `/resolve/{userId}`

Expected body:
```json
{
  "userId":id,
  "email":"email",
  "status":"status",
  "role": "role"}
}
```
This route will change the user's status accordingly.
Returns a user JSON in the format of:
```json
{
    "userId": id,
    "email": "email",
    "password": "hashed password",
    "status": "status",
    "role": "role"
}
```
### Set password
uri: `/password`

Expected body:
```json
{
    "userId": id,
    "email": "email",
    "password": "password that they wish to set",
}
```
This route will set set the user's password.
Returns a user JSON in the format of:
```json
{
    "userId": id,
    "email": "email",
    "password": "hashed password",
    "status": "status",
    "role": "role"
}
```
### Login
uri: `/login`

Expected body:
```json
{
  "email":"email",
  "password":"password"
}
```
This route will return a JWT if the email and password are correct.
Returns a JWT in the form of a string.

### Verify
uri: `/verify`

A JWT string is expected in the body.

This is simply to verify if the provied JWT is valid.
Returns a user JSON in the format of:
```json
{
    "id": id,
    "email": "email",
    "role": "role"
}
```

## Contributors
- [Aristotle](https://github.com/StotTot)
- [Lcarrico](https://github.com/Lcarrico)
- [Dhurba](https://github.com/dhurba212)

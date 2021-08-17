# Recipes

## About
A Spring application that allows to create user accounts and store recipes.

### Features
* allows to login, register and delete an account
* allows users to create their recipes, update and delete them and browse other users recipes
* stores user accounts and recipes in a database

### Technology
* Java
* Spring framework
* H2 database stored in a file (via Spring Data JPA)
* Spring Security
---
API Reference
---
## User
### Register a user
````
GET http://localhost:8881/api/register
````
Query parameter:
* email
* password

Example:
````
{
"email": "author@gmail.com",
"password": "password"
}
````

### Delete a user
```
DELETE http://localhost:8881/api/delete-account
```
Authorization: basic, with email and password.

When the user deletes his account, all the recipes he has created are deleted as well.

## Recipe
### Add a recipe
```
POST http://localhost:8881/api/recipe/new
```
Query parameter:
* name
* category
* description
* ingredients
* directions

Example:
````
{
  "name": "Fresh Mint Tea",
  "category": "beverage",
  "description": "Light, aromatic and refreshing beverage, ...",
  "ingredients": ["boiled water", "honey", "fresh mint leaves"],
  "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
````

### Get recipe by id
```
GET http://localhost:8881/api/recipe/{id}
```
Authorization: basic, with email and password


### Get recipes by category
```
GET http://localhost:8881/api/recipe/search?category=beverage
```
Authorization: basic, with email and password

### Get recipes by name
```
GET http://localhost:8881/api/recipe/search?name=Tea
```
Authorization: basic, with email and password

### Update a recipe
```
PUT http://localhost:8881/api/recipe/{id}
```
Query parameter:
* name
* category
* description
* ingredients
* directions

Example:
````
{
  "name": "Fresh Mint Tea Updated",
  "category": "beverage",
  "description": "Light, aromatic and refreshing beverage, ...",
  "ingredients": ["boiled water", "honey", "fresh mint leaves"],
  "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
````
Authorization: basic, with email and password

### Delete a recipe
```
DELETE http://localhost:8881/api/recipe/{id}
```
Authorization: basic, with email and password
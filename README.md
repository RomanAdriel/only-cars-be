# Backend
Currently, this application supports creating a product, finding a product in the database, and listing all products


## Endpoints

### Create
http://localhost:8080/productos

In order to create a product, you'll need 3 attributes in your request
- [ ] name -> String
- [ ] description -> String
- [ ] files -> Array

In addition, you can add this attributes
- [ ] car_brand -> String
- [ ] price -> double
- [ ] vehicule_type -> String
- [ ] passengers -> int
- [ ] air_conditioning -> boolean
- [ ] manual -> boolean

It's important to note that you can send none, one o multiples images to create a product, but you MUST declare the "files" key in your request, even if it's an empty array.
### Find by ID

http://localhost:8080/productos/{id}

This will bring you an existing object in the database.

You don't need anything other than the endpoint and an existing id for this request.

### Find all

http://localhost:8080/productos

This will bring all existing products in the database


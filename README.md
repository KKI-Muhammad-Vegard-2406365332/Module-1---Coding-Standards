# eShop Application

## Overview
This project is a simple eShop application built using Spring Boot.
It demonstrates basic CRUD features and applies MVC architecture and clean coding practices.

---

## Branches
The project uses a feature-branch workflow:

- `main`  
  Contains stable features after merging `list-product`.

- `list-product`  
  Implements create and list product features.

- `edit-product`  
  Implements the Edit Product feature.

- `delete-product`  
  Implements the Delete Product feature.

The `edit-product` and `delete-product` branches are not merged into `main`
as required by the exercise.

---

## Architecture
The application follows the MVC pattern:
- Model represents product data
- Repository handles data storage
- Service contains business logic
- Controller handles client requests

Additional repository methods such as `findById` and `deleteById`
are used to support Edit and Delete Product features while keeping
data access logic inside the repository layer.

---

## Reflection
In this module, I implemented Edit and Delete Product features using Spring Boot.
I applied clean code principles by separating responsibilities between controller,
service, and repository layers.

To support these features, I added methods such as `findById` and `deleteById`
to the repository and service layers, ensuring that controllers do not directly
manipulate data structures.

I also used feature branches to ensure new features do not affect the stable main branch.
One possible improvement is adding input validation and using a database instead of
an in-memory list.

---

## Testing
Unit tests are added to verify the model and repository implementation using JUnit 5.

---

## Changelog / Notes
### v0.1.0
- Implemented Create and List Product features in the `list-product` branch.
- Merged the `list-product` branch into `main` after verification.
- Added Edit Product feature using `findById` in the `edit-product` branch.
- Added Delete Product feature using `deleteById` in the `delete-product` branch.
- Configured Gradle for JUnit 5 and separated unit and functional tests.
- Added unit tests for model and repository layers.

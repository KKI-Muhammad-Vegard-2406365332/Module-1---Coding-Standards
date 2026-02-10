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

## Reflection 1
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

### Reflection 2
## 1. Unit Testing and Code Coverage

After writing the unit tests, I felt more confident about the correctness of my code. Testing both positive cases (valid edit and delete) and negative cases (editing or deleting non-existing products) helped me understand how the system behaves in different situations.

There is no fixed number of unit tests required for a class. What matters is that each important method is tested with different scenarios, including edge cases. Code coverage is useful to see which parts of the code are tested, but having 100% coverage does not mean the code is completely free of bugs. Good assertions and meaningful test cases are more important than coverage percentage alone.

During this process, I encountered several issues related to dependency mismatches between JUnit, Selenium, and Gradle, which caused tests to fail initially. Fixing these problems taught me how important proper dependency alignment is when working with automated tests.

## 2. Functional Test Cleanliness

If I were asked to create another functional test similar to CreateProductFunctionalTest, reusing the same setup code could reduce code cleanliness. Duplicating WebDriver setup and configuration in multiple test classes makes the code harder to maintain.

To improve code quality, common setup logic should be reused using shared setup methods or a base test class. This keeps functional tests focused on their purpose and makes the test code cleaner and easier to maintain.

## Additional Challenges

I also faced Git merging issues, including accidentally merging the main branch into a feature branch. This required reverting the merge and fixing the branch history. From this, I learned to be more careful with merge directions and to verify branch status before merging.

## Changelog / Notes
### v0.1.0
- Implemented Create and List Product features in the `list-product` branch.
- Merged the `list-product` branch into `main` after verification.
- Added Edit Product feature using `findById` in the `edit-product` branch.
- Added Delete Product feature using `deleteById` in the `delete-product` branch.

## v0.2.0 – Testing and Stability Improvements
Added unit tests for:
- Product model
- Product repository
- Product service (Edit and Delete, including positive and negative cases)
- Created a dedicated unit-test branch and merged verified tests into main.
- Configured Gradle to properly separate unit tests and functional tests.
- Resolved JUnit, Selenium, and Gradle dependency mismatches that initially caused test execution failures.
- Added functional tests to simulate user interactions (Create Product flow).
- Improved test reliability by fixing failing edge cases.

## v0.2.1 – Maintenance and Lessons Learned
- Fixed accidental branch merge issues (e.g. merging main into feature branches).
- Reverted unintended merges and cleaned up branch history.
- Learned to verify branch direction and merge targets before merging.
- Improved commit discipline and branch workflow awareness.
- Configured Gradle for JUnit 5 and separated unit and functional tests.
- Added unit tests for model and repository layers.

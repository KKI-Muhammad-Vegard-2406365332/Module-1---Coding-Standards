# eShop Application

<a href="https://religious-diane-marie-kki-muhammad-vegard-2406365332-9ed40735.koyeb.app/" target="_blank">Live Application (Koyeb)</a>

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=KKI-Muhammad-Vegard-2406365332_Module-1---Coding-Standards&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=KKI-Muhammad-Vegard-2406365332_Module-1---Coding-Standards)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=KKI-Muhammad-Vegard-2406365332_Module-1---Coding-Standards&metric=coverage)](https://sonarcloud.io/summary/new_code?id=KKI-Muhammad-Vegard-2406365332_Module-1---Coding-Standards)

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

## Reflection 2
### 1. Unit Testing and Code Coverage

After writing the unit tests, I felt more confident about the correctness of my code. Testing both positive cases (valid edit and delete) and negative cases (editing or deleting non-existing products) helped me understand how the system behaves in different situations.

There is no fixed number of unit tests required for a class. What matters is that each important method is tested with different scenarios, including edge cases. Code coverage is useful to see which parts of the code are tested, but having 100% coverage does not mean the code is completely free of bugs. Good assertions and meaningful test cases are more important than coverage percentage alone.

During this process, I encountered several issues related to dependency mismatches between JUnit, Selenium, and Gradle, which caused tests to fail initially. Fixing these problems taught me how important proper dependency alignment is when working with automated tests.

### 2. Functional Test Cleanliness

If I were asked to create another functional test similar to CreateProductFunctionalTest, reusing the same setup code could reduce code cleanliness. Duplicating WebDriver setup and configuration in multiple test classes makes the code harder to maintain.

To improve code quality, common setup logic should be reused using shared setup methods or a base test class. This keeps functional tests focused on their purpose and makes the test code cleaner and easier to maintain.

### Additional Challenges

I also faced Git merging issues, including accidentally merging the main branch into a feature branch. This required reverting the merge and fixing the branch history. From this, I learned to be more careful with merge directions and to verify branch status before merging.

---
## Reflection 3 - Module 2
**1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.**
During the exercise, I encountered and fixed several code quality issues (Code Smells) flagged by SonarCloud:
* **Wildcard Imports:** SonarCloud flagged the use of `import org.springframework.web.bind.annotation.*;` in my `ProductController`. My strategy to fix this was to replace the wildcard with explicit, individual imports (e.g., `@GetMapping`, `@PostMapping`). This is a clean code practice that prevents namespace pollution and reduces the risk of class name conflicts.
* **Field Injection:** SonarCloud flagged the use of `@Autowired` directly on the `ProductService` field. My strategy was to refactor this to use **Constructor Injection**. I changed the field to `private final ProductService service;` and created an `@Autowired` constructor. This is a Spring Boot best practice because it allows the class state to be immutable, makes the code easier to unit test, and ensures the dependency is never null upon initialization.

**2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!**
Yes, I believe the current implementation fully meets the definitions of both Continuous Integration (CI) and Continuous Deployment (CD). 
For **Continuous Integration**, our GitHub Actions workflows (`ci.yml` and `sonarcloud.yml`) automatically trigger on every push and pull request. These workflows compile the code, run the full suite of unit and functional tests, measure code coverage using JaCoCo, and perform static code analysis via SonarCloud to ensure that only secure, high-quality code gets integrated. 
For **Continuous Deployment**, the repository is integrated with Koyeb using a pull-based approach. Whenever code passes the CI pipeline and is pushed to the `main` branch, Koyeb automatically detects the changes, builds the application using the multi-stage `Dockerfile`, and deploys the new version directly to the live production server without requiring any manual intervention.

## Changelog / Notes
### v0.1.0
- Implemented Create and List Product features in the `list-product` branch.
- Merged the `list-product` branch into `main` after verification.
- Added Edit Product feature using `findById` in the `edit-product` branch.
- Added Delete Product feature using `deleteById` in the `delete-product` branch.

### v0.2.0 – Testing and Stability Improvements
Added unit tests for:
- Product model
- Product repository
- Product service (Edit and Delete, including positive and negative cases)
- Created a dedicated unit-test branch and merged verified tests into main.
- Configured Gradle to properly separate unit tests and functional tests.
- Resolved JUnit, Selenium, and Gradle dependency mismatches that initially caused test execution failures.
- Added functional tests to simulate user interactions (Create Product flow).
- Improved test reliability by fixing failing edge cases.

### v0.2.1 – Maintenance and Lessons Learned
- Fixed accidental branch merge issues (e.g. merging main into feature branches).
- Reverted unintended merges and cleaned up branch history.
- Learned to verify branch direction and merge targets before merging.
- Improved commit discipline and branch workflow awareness.
- Configured Gradle for JUnit 5 and separated unit and functional tests.
- Added unit tests for model and repository layers.

### v0.3.0 – CI/CD, Code Quality, and PaaS Deployment (Module 2)
- **Continuous Integration (CI):** Implemented GitHub Actions workflows (`ci.yml` and `sonarcloud.yml`) to automatically run tests and security scans on every push and pull request.
- **Code Coverage:** Added missing unit tests for the application's main method, service layer, and repository to achieve a **100% JaCoCo test coverage** score.
- **Static Code Analysis:** Integrated **SonarCloud** to continuously monitor code quality. Resolved several "Code Smells," including replacing wildcard imports with explicit imports and refactoring field injection to constructor injection.
- **Continuous Deployment (CD):** Created a multi-stage `Dockerfile` to containerize the Spring Boot application and successfully automated deployment to **Koyeb** using a pull-based strategy.
- **Documentation:** Added dynamic SonarCloud Quality Gate and Coverage badges to the repository README.

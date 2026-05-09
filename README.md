# REST Assured Test Suite

A comprehensive REST API testing framework built with REST Assured and TestNG for testing JSON APIs.

## 📋 Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Project Structure](#project-structure)
- [Examples](#examples)
- [License](#license)

## ✨ Features

- **REST API Testing** - Built with REST Assured for seamless HTTP request testing
- **TestNG Integration** - Powerful test framework with flexible test configuration
- **Configuration Management** - Dynamic configuration from properties, environment variables, and system properties
- **Logging** - Comprehensive logging using SLF4J with Lombok
- **Record-based Models** - Type-safe request/response models using Java Records
- **Organized Structure** - Clean package organization (config, models, tests)

## 📦 Prerequisites

- Java 17 or higher
- Gradle 7.x or higher
- Git

## 🚀 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd rest-assured/tests
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

## ⚙️ Configuration

### Config File
Configuration is managed through `src/test/resources/config.properties`:
```properties
baseUrl=https://jsonplaceholder.typicode.com
```

### Priority Order
Configuration values are resolved in the following priority:

1. **System Property** (-D flag)
   ```bash
   gradle test -DbaseUrl=https://staging.api.com
   ```

2. **Environment Variable**
   ```powershell
   $env:baseUrl = "https://prod.api.com"
   gradle test
   ```

3. **config.properties file** (default fallback)

## 🧪 Running Tests

### Run all tests
```bash
./gradlew test
```

### Run with custom baseUrl (system property)
```bash
./gradlew test -DbaseUrl=https://custom.api.com
```

### Run with environment variable
**PowerShell:**
```powershell
$env:baseUrl = "https://custom.api.com"
./gradlew test
```

**Bash:**
```bash
export baseUrl=https://custom.api.com
./gradlew test
```

### View test reports
After running tests, reports are available at:
```
build/reports/tests/test/index.html
```

## 📁 Project Structure

```
rest-assured/tests/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       ├── java/
│       │   └── org/example/
│       │       ├── SampleTest.java          # Test class
│       │       ├── config/
│       │       │   └── ConfigLoader.java    # Configuration utility
│       │       └── models/
│       │           └── PostRequest.java     # Request model (Record)
│       └── resources/
│           ├── config.properties            # Configuration file
│           └── testng.xml                   # TestNG configuration
├── build.gradle                             # Gradle build file
├── .gitignore                               # Git ignore rules
├── README.md                                # This file
└── LICENSE                                  # Apache License 2.0
```

## 💡 Examples

### Example 1: GET Request
```java
@Test
public void test1() {
    RestAssured.given()
            .when()
            .get("/posts/1")
            .then()
            .log().all()
            .statusCode(200);
}
```

### Example 2: POST Request with Record Model
```java
@Test
public void testCreatePost() {
    PostRequest postRequest = new PostRequest("foo", "bar", 1);

    RestAssured.given()
            .header("Content-Type", "application/json")
            .body(postRequest)
            .when()
            .post("/posts")
            .then()
            .log().all()
            .statusCode(201);
}
```

### Example 3: Using ConfigLoader
```java
@BeforeClass
public void setup() {
    RestAssured.baseURI = ConfigLoader.getBaseUrl();
    log.info("Base URL set to: {}", RestAssured.baseURI);
}

// Or get any custom config
String apiKey = ConfigLoader.getConfig("apiKey");
```

## 📚 Dependencies

- **io.rest-assured** (5.5.7) - REST API testing
- **org.testng** (7.12.0) - Testing framework
- **org.slf4j** (2.0.17) - Logging
- **org.projectlombok** (1.18.46) - Boilerplate reduction
- **com.fasterxml.jackson** - JSON serialization

## 📝 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📧 Support

For questions or support, please open an issue in the repository.


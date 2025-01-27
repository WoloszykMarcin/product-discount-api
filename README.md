# product-discount-api

### Overview

This project is a simple product discount API built with Spring Boot. It includes functionality for managing carts and products, applying discounts, and calculating total prices based on given strategies.

### Building and Running the Application

### Requirements

- Java 14
- Maven
- Docker

### Building and Running the Application

1. **Ensure Java is Installed**

   Make sure you have Java 14 installed. You can download it from [here](https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html).

2. **Build the Application**

   Ensure you have Maven installed or use the provided Maven wrapper script:

    ```sh
    ./mvnw clean package
    ```

   This command will compile the project, run tests, and package the application into a JAR file.
#### Building and Running the Application with Docker

1. **Build and run the Docker container:**

    ```sh
    docker-compose up --build
    ```

2. **Access the application:**

   The application will be running on `http://localhost:8080`.
3. ### Accessing the H2 Database Console

1. **URL:** You can access the H2 navigating to [http://localhost:8080/h2-console](http://localhost:8080/h2-console).

2. **JDBC URL:** `jdbc:h2:mem:testdb`
3. **Username:** `sa`
4. **Password:** `password`

### Actuator Endpoints

- **Access Actuator Endpoints:** [http://localhost:8080/actuator](http://localhost:8080/actuator)
- **Health Check Endpoint:** [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

### Cart Endpoints

- **Create a new cart**
    - **URL:** `/api/carts`
    - **Method:** `POST`
    - **Response:** Created cart object

- **Get a cart by ID**
    - **URL:** `/api/carts/{id}`
    - **Method:** `GET`
    - **Response:** Cart object

- **Add a product to the cart**
    - **URL:** `/api/carts/{cartId}/products`
    - **Method:** `POST`
    - **Request Params:** `productId` (UUID), `quantity` (int)
    - **Response:** Updated cart object

- **Remove a product from the cart**
    - **URL:** `/api/carts/{cartId}/products`
    - **Method:** `DELETE`
    - **Request Params:** `productId` (UUID)
    - **Response:** Updated cart object

- **Get the total price of the cart**
    - **URL:** `/api/carts/{cartId}/totalPrice`
    - **Method:** `GET`
    - **Request Params:** `discountType` (String)
    - **Response:** Total price (BigDecimal)

### Product Endpoints

- **Create a new product**
  - **URL:** `/api/products`
  - **Method:** `POST`
  - **Request Body:** `{ "name": "Product Name", "price": 100.00 }`
  - **Response:** Created product object

- **Get all products**
  - **URL:** `/api/products`
  - **Method:** `GET`
  - **Response:** List of products

- **Get a product by ID**
  - **URL:** `/api/products/{id}`
  - **Method:** `GET`
  - **Response:** Product object

### Running Tests

1. **Unit and Integration Tests**

   The project includes both unit and integration tests. You can run them using Maven:

    ```sh
    ./mvnw test
    ```
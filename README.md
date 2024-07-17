# product-discount-api

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

#### Product Endpoints

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
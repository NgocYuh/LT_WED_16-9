# Product & Category CRUD

Spring Boot 3.1.5 project implementing CRUD APIs for `Category` and `Product`, file upload, Swagger 3 and a jQuery AJAX screen.

Run with `mvn spring-boot:run`, then open:

- `http://localhost:8080/` - AJAX CRUD page
- `http://localhost:8080/swagger-ui.html` - Swagger 3
- `http://localhost:8080/h2-console` - local H2 database

The API uses RESTful routes: `GET/POST /api/categories`, `GET/PUT/DELETE /api/categories/{id}`, with the equivalent routes under `/api/products`. Create/update requests use `multipart/form-data`; the image fields are optional.

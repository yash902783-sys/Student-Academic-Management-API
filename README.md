# KIET Student Academic Management REST API

A Spring Boot REST API matching the MSE2 sample assessment. It uses an in-memory `List<Student>` and does not require a database.

## Requirements
- JDK 17 or newer
- IntelliJ IDEA
- Internet access on first Maven import to download dependencies

## Run in IntelliJ IDEA
1. Extract/open this folder in IntelliJ IDEA.
2. Wait for Maven dependencies to load.
3. Make sure Project SDK is JDK 17 or newer.
4. Run `src/main/java/com/kiet/student/StudentApplication.java`.
5. The API runs at `http://localhost:8080`.

## Endpoints
- `GET http://localhost:8080/students`
- `GET http://localhost:8080/students/101`
- `POST http://localhost:8080/students` with JSON body
- `PUT http://localhost:8080/students/101` with JSON body
- `DELETE http://localhost:8080/students/105`
- `GET http://localhost:8080/students/search?branch=CSE&year=2`
- `GET http://localhost:8080/students/topper?cgpa=9.0`

POST example:
```json
{
  "studentId": 106,
  "name": "Amit Kumar",
  "email": "amit@kiet.edu",
  "branch": "CSE(AI)",
  "year": 2,
  "cgpa": 8.75
}
```

PUT example:
```json
{
  "studentId": 101,
  "name": "Rahul Sharma",
  "email": "rahul@kiet.edu",
  "branch": "CSE",
  "year": 3,
  "cgpa": 9.05
}
```

Note: data is stored in memory, so changes made with POST/PUT/DELETE are lost when the application restarts. This is expected for this assessment.

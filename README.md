# DegreePath

A course planning system for CSUEB Computer Science students that helps generate balanced semester plans based on prerequisites and unit constraints.

## 🚀 Live Demo

**[Try it here!](https://degreepath.onrender.com/test.html)**
**[Home Page](https://degreepath.onrender.com)**

## 📋 Features

- **Prerequisite Validation**: Automatically checks if students meet course requirements
- **Semester Plan Generation**: Creates optimized course schedules within unit limits
- **REST API**: JSON endpoints for integration with other applications
- **Course Database**: Stores lower-division CS courses with metadata (workload, terms offered, etc.)

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3.5
- **Data Format**: JSON
- **Deployment**: Docker, Render
- **Version Control**: Git, GitHub

## 💡 How It Works

1. **Input**: Student provides completed courses and desired unit load
2. **Processing**: Algorithm validates prerequisites and generates valid course combinations
3. **Output**: Returns semester plan with courses that satisfy all constraints

## 🏗️ Architecture
```
├── backend/
│   ├── src/main/java/com/degreepath/
│   │   ├── Course.java                    # Course data model
│   │   ├── PrerequisiteChecker.java       # Validation logic
│   │   ├── SemesterPlanner.java           # Plan generation algorithm
│   │   └── PlannerController.java         # REST API endpoints
│   └── src/main/resources/
│       └── data/courses.json              # Course database
├── data/                                   # Original course data
└── docs/                                   # Design documentation
```

## 🔧 API Endpoints

### GET `/api/courses`
Returns all available courses

### POST `/api/plan`
Generate a semester plan

**Request:**
```json
{
  "completed": ["MATH130"],
  "maxUnits": 8
}
```

**Response:**
```json
{
  "courses": [
    {"code": "CS101", "name": "Computer Science I", "units": 4, ...},
    {"code": "CS211", "name": "Discrete Structures", "units": 4, ...}
  ],
  "totalUnits": 8
}
```

## 🚦 Running Locally

1. Clone the repository:
```bash
git clone https://github.com/BaoDoii/DegreePath.git
cd DegreePath/backend
```

2. Run with Maven:
```bash
./mvnw spring-boot:run
```

3. Visit `http://localhost:8080/test.html`

## 📈 Future Enhancements (V2)

- [ ] Minimum grade requirements (C- validation)
- [ ] Course difficulty balancing
- [ ] Multi-semester planning
- [ ] Upper-division course support
- [ ] Transfer student articulation
- [ ] Additional majors

## 👤 Author

**Brian Bao**
- GitHub: [@BaoDoii](https://github.com/BaoDoii)

## 📝 License

This project is open source and available under the MIT License.

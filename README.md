# DegreePath

[![Build Status](https://github.com/BaoDoii/DegreePath/actions/workflows/ci.yml/badge.svg)](https://github.com/BaoDoii/DegreePath/actions)

A full-stack academic course planning system for CSUEB Computer Science students. Generates intelligent multi-semester graduation paths using graph-based prerequisite validation and priority ranking algorithms.

## Live Demo
**[Try it here →](https://degreepath.onrender.com)**

## Screenshots
<img width="1114" height="920" alt="home" src="https://github.com/user-attachments/assets/cc6466bb-315c-42c6-a373-5323f253551f" />

<img width="583" height="1080" alt="planner" src="https://github.com/user-attachments/assets/4356a1c3-3582-4143-8800-bc426c5ead1b" />

<img width="650" height="954" alt="example" src="https://github.com/user-attachments/assets/b25cceaa-af30-4891-a9e9-26746fa3569d" />

## Features

- **Multi-Semester Planning** – Generates complete graduation paths across 1-8 semesters with sequential prerequisite tracking
- **Prerequisite Validation** – Graph-based algorithm validates course dependencies ensuring valid course sequences
- **Priority Ranking** – Recommends courses that unlock the most future options first using dependency graph traversal
- **GE Integration** – Supports general education placeholder courses for realistic semester planning
- **Difficulty Balancing** – Analyzes course workload and warns when 3+ Heavy courses are scheduled
- **20+ CS Courses** – Complete CSUEB CS curriculum including lower-division, upper-division, and elective courses
- **REST API** – Clean JSON endpoints for course data and multi-semester plan generation

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3.5 |
| Algorithm | Graph traversal, Constraint satisfaction, Priority ranking |
| Frontend | HTML, CSS, JavaScript |
| Data | JSON |
| Deployment | Docker, Render |
| Version Control | Git, GitHub |

## How It Works

1. Student selects completed courses
2. Sets maximum unit load per semester
3. Sets number of GE courses per semester
4. Specifies number of semesters to plan (1-8)
5. Algorithm validates prerequisites using graph traversal
6. Courses ranked by dependency unlock potential
7. Returns multi-semester graduation path with difficulty warnings

## Architecture
```
DegreePath/
├── backend/
│   ├── src/main/java/com/degreepath/
│   │   ├── Course.java                 # Data model
│   │   ├── PrerequisiteChecker.java    # Prerequisite validation
│   │   ├── SemesterPlanner.java        # Priority ranking algorithm
│   │   └── PlannerController.java      # REST API endpoints
│   └── src/main/resources/
│       ├── data/courses.json           # Course database (23 courses)
│       └── static/                     # Frontend files
└── README.md
```

## Local Setup

### Prerequisites
- Java 17+
- Maven 3.6+

### Installation

1. Clone the repository
```bash
   git clone https://github.com/BaoDoii/DegreePath.git
   cd DegreePath
```

2. Run the backend
```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
```

3. Access the application
   - Open `http://localhost:8080`
   - Frontend is served from `src/main/resources/static`

### Docker Deployment
```bash
docker build -t degreepath .
docker run -p 8080:8080 degreepath
```

### Testing
```bash
cd backend
mvn test
```

### GET `/api/courses`
Returns all available courses with metadata

### POST `/api/plan`
Generates single semester plan

**Request:**
```json
{
  "completed": ["MATH130", "CS101"],
  "maxUnits": 12,
  "numGEs": 2
}
```

### POST `/api/multiplan`
Generates multi-semester graduation path

**Request:**
```json
{
  "completed": ["MATH130"],
  "maxUnits": 12,
  "numGEs": 2,
  "numberOfSemesters": 3
}
```

**Response:**
```json
{
  "semesters": [
    {
      "semesterNumber": 1,
      "courses": [...],
      "totalUnits": 12
    },
    {
      "semesterNumber": 2,
      "courses": [...],
      "totalUnits": 11
    }
  ]
}
```

## Roadmap

- [x] Prerequisite validation algorithm
- [x] Priority-based course ranking
- [x] 20+ CSUEB CS courses
- [x] REST API with JSON endpoints
- [x] Production deployment
- [x] Multi-semester planning (1-8 semesters)
- [x] GE placeholder integration
- [x] Difficulty balancing warnings
- [ ] Min grade validation (C- checking)
- [ ] Transfer student articulation
- [ ] Additional majors
- [ ] Unit testing suite

## Author

**Brian Ha**
- GitHub: [@BaoDoii](https://github.com/BaoDoii)
- LinkedIn: [linkedin.com/in/brian-ha-a9060724a](https://www.linkedin.com/in/brian-ha-a9060724a)

## License

MIT License

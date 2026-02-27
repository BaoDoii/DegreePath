# DegreePath

A full-stack academic course planning system for CSUEB Computer Science students. Generates intelligent multi-semester graduation paths using graph-based prerequisite validation and priority ranking algorithms.

## Live Demo
**[Try it here →](https://degreepath.onrender.com)**

## Screenshots
<img width="1016" height="926" alt="home" src="https://github.com/user-attachments/assets/6972f563-e346-4cf0-b166-7eaa663d34de" />

<img width="436" height="816" alt="planner" src="https://github.com/user-attachments/assets/357cf043-7b28-4a2a-956b-45c29cb3e50d" />

<img width="604" height="857" alt="example" src="https://github.com/user-attachments/assets/7c8b9579-46a0-4906-9c8f-1a7d08a4ef43" />

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

## API Endpoints

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

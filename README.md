# DegreePath

[![Build Status](https://github.com/BaoDoii/DegreePath/actions/workflows/ci.yml/badge.svg)](https://github.com/BaoDoii/DegreePath/actions)

A full-stack course planning app for CSUEB Computer Science students. It generates multi-semester plans that respect prerequisites, using a dependency graph to schedule the courses that unlock the most future courses first.

## Live Demo
**[Try it here →](https://degreepath.onrender.com)**

## Screenshots
<img width="565" height="798" alt="Home2" src="<img width="837" height="1075" alt="HomeNew" src="https://github.com/user-attachments/assets/dbbae84b-b1d2-440e-971d-a8b3bb904a76" />
" />


<img width="1918" height="824" alt="planner2" src="<img width="837" height="1080" alt="PlannerNew" src="https://github.com/user-attachments/assets/0cb2b732-c6cd-41c2-a2b0-92eaaccc9891" />
" />


## Features

- **Multi-Semester Planning** – Generates plans across 1-8 semesters. A course is only scheduled after all of its prerequisites were completed in an earlier semester
- **Graph-Based Priority Ranking** – Builds a prerequisite graph and uses BFS to count how many courses each course unlocks (directly and indirectly), then schedules the highest-impact courses first
- **Cycle Detection** – Kahn's algorithm checks the course data for circular prerequisites
- **GE Integration** – General education placeholder courses (3 units each) count toward the semester unit limit
- **Workload Warning** – The frontend warns when a semester has 4+ heavy courses
- **23 CS Courses** – Math prerequisites, lower division, upper division core, and electives
- **REST API** – JSON endpoints for course data and plan generation

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3.5 |
| Algorithms | BFS (unlock counting), Kahn's algorithm (cycle detection), greedy unit-limited scheduling |
| Frontend | HTML, CSS, JavaScript |
| Data | JSON |
| Testing | JUnit 5 |
| Deployment | Docker, Render, GitHub Actions CI |

## How It Works

1. Student selects completed courses
2. Sets maximum units per semester
3. Sets number of GE courses per semester
4. Chooses how many semesters to plan (1-8)
5. The planner finds courses not yet taken whose prerequisites are all completed
6. It builds the prerequisite graph and runs BFS from each available course to count everything it unlocks
7. Courses are sorted by that count and added greedily until the unit limit is reached (GEs are added last)
8. The completed list is updated and the process repeats for the next semester

The unit-filling step is greedy, so plans are a good heuristic and not guaranteed to be optimal.

## Architecture
```
DegreePath/
├── backend/
│   ├── src/main/java/com/degreepath/degreepath/
│   │   ├── Course.java                 # Data model
│   │   ├── DependencyGraph.java        # Prerequisite graph: BFS unlock count, cycle detection
│   │   ├── PrerequisiteChecker.java    # Checks a course/plan against completed courses
│   │   ├── SemesterPlanner.java        # Ranks and selects courses for a semester
│   │   └── PlannerController.java      # REST API endpoints
│   ├── src/main/resources/
│   │   ├── data/courses.json           # Course database (23 courses)
│   │   └── static/                     # Frontend files
│   └── src/test/java/com/degreepath/degreepath/
│       └── PlannerGraphTest.java       # Unit tests
└── README.md
```

## Local Setup

### Prerequisites
- Java 17+
- Maven 3.6+ (or use the included `./mvnw`)

### Installation

1. Clone the repository
```bash
git clone https://github.com/BaoDoii/DegreePath.git
cd DegreePath
```

2. Run the backend
```bash
cd backend
./mvnw clean install
./mvnw spring-boot:run
```

3. Open `http://localhost:8080` (the frontend is served from `src/main/resources/static`)

### Docker
```bash
cd backend
docker build -t degreepath .
docker run -p 8080:8080 degreepath
```

### Testing
```bash
cd backend
./mvnw test
```
The tests cover indirect and shared-course unlock counting, cycle detection (including self-prerequisites), unknown prerequisites, null prerequisite lists, ranking, GE handling, unit limits, and full multi-semester plan validity. They also run in GitHub Actions on every push.

## API

### GET `/api/courses`
Returns all available courses with metadata

### POST `/api/plan`
Generates a single semester plan

**Request:**
```json
{
  "completed": ["MATH130", "CS101"],
  "maxUnits": 12,
  "numGEs": 2
}
```

### POST `/api/multiplan`
Generates a multi-semester plan

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

- [x] Prerequisite-aware semester planning
- [x] Graph-based course ranking (BFS)
- [x] Cycle detection (Kahn's algorithm)
- [x] 23 CSUEB CS courses
- [x] REST API with JSON endpoints
- [x] Production deployment
- [x] Multi-semester planning (1-8 semesters)
- [x] GE placeholder integration
- [x] Workload warnings
- [x] Unit testing suite
- [ ] Respect terms offered (Fall/Spring)
- [ ] Min grade validation (C- checking)
- [ ] Transfer student articulation
- [ ] Additional majors

## Author

**Brian Ha**
- GitHub: [@BaoDoii](https://github.com/BaoDoii)
- LinkedIn: [https://www.linkedin.com/in/brianha-baodoi]([https://www.linkedin.com/in/brian-ha-a9060724a](https://www.linkedin.com/in/brianha-baodoi))

## License

MIT License

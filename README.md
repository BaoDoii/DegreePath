# DegreePath

A full-stack academic course planning system for CSUEB Computer Science 
students. Generates intelligent semester plans using graph-based 
prerequisite validation and priority ranking algorithms.

## Live Demo
**[Try it here →](https://degreepath.onrender.com)**

## Screenshots

<img width="1223" height="928" alt="Capture" src="https://github.com/user-attachments/assets/683da9f1-a6d6-4c99-97d7-87207f306dac" />


## Features

- **Prerequisite Validation** – Graph-based algorithm validates course 
  dependencies before recommending courses
- **Priority Ranking** – Recommends courses that unlock the most future 
  options first using dependency graph traversal
- **25+ CS Courses** – Complete CSUEB CS curriculum including 
  lower-division, upper-division, and elective courses
- **Workload Metadata** – Courses rated by difficulty to support 
  balanced semester planning
- **REST API** – Clean JSON endpoints for course data and plan generation

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3.5 |
| Algorithm | Graph traversal, Constraint satisfaction |
| Frontend | HTML, CSS, JavaScript |
| Data | JSON |
| Deployment | Docker, Render |
| Version Control | Git, GitHub |

## How It Works

1. Student selects completed courses
2. Sets maximum unit load for semester
3. Algorithm validates prerequisites using graph traversal
4. Courses ranked by dependency unlock potential
5. Returns optimized semester plan

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
│       ├── data/courses.json           # Course database (25+ courses)
│       └── static/                     # Frontend files
└── README.md
```

## API Endpoints

### GET `/api/courses`
Returns all available courses with metadata

### POST `/api/plan`
Generates prioritized semester plan

## Roadmap

- [x] Prerequisite validation algorithm
- [x] Priority-based course ranking
- [x] 20+ CSUEB CS courses
- [x] REST API with JSON endpoints
- [x] Production deployment
- [ ] Multi-semester planning
- [ ] Min grade validation (C- checking)
- [ ] Difficulty balancing warnings
- [ ] Transfer student support
- [ ] Additional majors

## Author

**Brian Ha**
- GitHub: [@BaoDoii](https://github.com/BaoDoii)
- LinkedIn: [linkedin.com/in/brian-ha-a9060724a]

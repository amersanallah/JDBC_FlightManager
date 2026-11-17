# Ilya Zeldner - Software Engineering Methods – Lab Exercise 3: MySQL Normalization (3NF), AI-Driven JDBC & SQL Injection Defense

**Department of Software Engineering | Braude College of Engineering**  
**Course:** Software Engineering Methods (2026–2027)  
**Platform:** IntelliJ IDEA Ultimate 2026.2 | BellSoft Liberica Full JDK 25 LTS | MySQL 8.0+ | Git & GitHub | Continue (LM Studio)

---

## Executive Overview
In this lab exercise, student pairs collaborate on a **single shared workstation** (Driver–Navigator model) to build the **Data Access Layer (DAL)** for an airport flight arrivals management system (`JDBC_FlightManager`). Using a local, open-source AI coding assistant (**Continue** powered by **Qwen2.5-Coder**), students ingest raw landing telemetry (`arrived_flights.csv`) into MySQL, analyze functional dependencies ($F$), decompose the flat schema into **Third Normal Form (3NF)** (`FlightRoutes` and `Arrivals`), develop parameterized and batched JDBC operations, execute a live **SQL Injection** exploit against a vulnerable `Statement`, and refactor the codebase into a secure `PreparedStatement` with comprehensive Javadoc.

### Core Learning Objectives
- **Relational Normalization (3NF):** Identify transitive functional dependencies ($\text{flight} \rightarrow \text{fromC}$) and eliminate update, insertion, and deletion anomalies via Foreign Key decomposition.
- **Modern JDBC Engineering:** Master `try-with-resources` connection hygiene, `INNER JOIN` queries, and high-throughput `PreparedStatement` batch updates (`addBatch` / `executeBatch`).
- **Application Security (AppSec):** Demonstrate how raw string concatenation enables SQL Injection across an entire table and remediate it using pre-compiled parameterized queries.
- **Database Backup & Repository Hygiene:** Export full SQL dumps (`FlightDB_Dump.sql`), document architecture with Mermaid ERD diagrams, and govern binary exclusions (`lib/`) via `.gitignore`.

---

## Repository Directory Map
The repository is organized into three dedicated operational sections:

```text
braude-Engineering-Methods-Tutorial-3/
│
├── חומרי קריאה מקדימים והסברים/
│   └── מדריך_עזר_פיתוח_שכבת_נתונים_באמצעות_Java_JDBC.docx
│         └── Comprehensive Hebrew guide covering 3NF normalization, JDBC core classes,
│             try-with-resources, PreparedStatement batching, and SQL Injection anatomy.
│
├── קבצי עבודה לתרגול/
│   ├── תרגול 3.docx
│   │     └── Master laboratory instruction manual with step-by-step procedures.
│   ├── Quick_Checklist_Exercise_3.docx
│   │     └── Execution checklist for tracking task progression on the shared workstation.
│   ├── FlightManagerBase.java
│   │     └── Starter Java DAL class demonstrating JDBC connection and INNER JOIN board rendering.
│   ├── arrived_flights.csv
│   │     └── Raw dataset containing 299 airport arrival telemetry records.
│   ├── mysql-connector-j-9.5.0.jar
│   │     └── Official MySQL Connector/J Type-4 JDBC driver.
│   └── README_TEMPLATE.md
│         └── Markdown template for student repository documentation.
│
└── הגשת המטלה/
    └── ex3_submission_template.docx
          └── Official Hebrew laboratory report template with team details, 6 screenshot slots,
              Part 4 prompt log, AI quality review, and engineering reflection questions.
```

---

## Required Toolchain & Setup
Before beginning the lab, ensure the following tools are properly configured on your workstation:

| Tool | Version / Specification | Role in Lab |
| :--- | :--- | :--- |
| **IntelliJ IDEA Ultimate** | 2026.2 or newer | Core IDE & Built-in Database Console / SQL Generator |
| **Java SDK** | BellSoft Liberica Full JDK 25 LTS | Compilation & runtime platform |
| **MySQL Server** | MySQL 8.0+ (`localhost:3306`) | Relational database hosting `FlightDB` |
| **MySQL Connector/J** | `mysql-connector-j-9.5.0.jar` | JDBC Type-4 driver placed in `lib/` |
| **LM Studio** | Local Server on `http://127.0.0.1:1234/v1` | Hosts local `Qwen2.5-Coder-7B-Instruct` |
| **Continue** | IntelliJ Plugin | AI SQL & Java code generation and security refactoring |
| **Git & GitHub** | Local Git + Remote Repo | Version control & portfolio delivery |

---

## Single-Workstation Pair Programming Workflow (Driver–Navigator)

```text
Shared Workstation (Student A & Student B)
       │
       ├─► [Joint] Part 1: Start LM Studio, Verify MySQL80, Create JDBC_FlightManager + lib/
       ├─► [Joint] Part 2: Import arrived_flights.csv -> AI 3NF Migration (FlightRoutes & Arrivals)
       ├─► [Joint] Part 3.1: Add FlightManagerBase.java & Run Smoke Test (299 joined rows)
       │
       ├─► [Student A: Driver | Student B: Navigator]
       │     └─► Part 3.2: Build SimpleFlightUpdater.java (Statement) & test flight 'LX354'
       │
       ├─► [Student B: Driver | Student A: Navigator]
       │     └─► Part 3.3: Build AdvancedFlightDispatcher.java (Batch PreparedStatement: IDs 1, 3, 5)
       │
       ├─► [Joint] Part 4: Independent Prompt Challenge -> Build FlightViewer.java & Capture Output
       ├─► [Joint] Part 5: Live SQL Injection Exploit -> AI Refactoring to PreparedStatement
       └─► [Joint] Part 6–7: Export FlightDB_Dump.sql, Write README.md, Push to GitHub & Zip G<XX>_Ex3.zip
```

---

## Verification & Smoke Test Protocol
Execute `src/FlightManagerBase.java` after Part 2 normalization (and after Part 5 remediation) to verify database connectivity and relational integrity:

### Expected Console Output Structure:
```text
Please enter your MySQL root password: ********
Successfully connected to FlightDB!

 --- Flight Arrivals Board ---
ID    | Flight     | From            | Scheduled  | Status               | Terminal  
--------------------------------------------------------------------------------------
1     | LX354      | Geneva          | 00:05:00   | ...                  | 3         
...
--------------------------------------------------------------------------------------
Total flights displayed: 299
```

---

## Essential Troubleshooting Tips

- **Public Key Retrieval Error:** Ensure `allowPublicKeyRetrieval=true` is included in your JDBC URL and in the IntelliJ Data Source `Advanced` tab.
- **Connection Refused (`localhost:3306`):** Open Windows Services (`Win + R` -> `services.msc`), locate `MySQL80`, and click **Start**.
- **Partial Query Execution Error:** When running the Part 2 migration script in Query Console, make sure **no text is highlighted** before pressing `Ctrl + Enter`.
- **Foreign Key Drop Order (Part 6.1 Reset):** Always drop the child table `Arrivals` before the parent table `FlightRoutes`:
  ```sql
  DROP TABLE IF EXISTS Arrivals;
  DROP TABLE IF EXISTS FlightRoutes;
  ```

---

## Academic Integrity & Licensing
This laboratory package is developed for instructional use in the Department of Software Engineering 
at Braude College of Engineering. 
Distribution, publishing, or sharing outside the official course boundary 
is strictly prohibited under the **Braude College Academic License**.
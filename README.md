# Grade Tracker Application
## Term Project Proposal

**What will the application do?**
The application will calculate grades based on the user's input. It will allow users to:
- Enter weightings and grade categories such as assignments, tests, labs, and participation.
- Organize grades by timelines (current term, any semester, or any year).
- Classify courses into categories, such as required (e.g., CS major courses) or electives.
- Store class information, including course codes (e.g., CPSC, MATH) and levels (e.g., CPSC 110, CPSC 210).
- Allow multiple users to create different profiles within the same application .

**Who will use it?**
This application is designed for *students* who want to track and analyze their academic performance.

**Why is this project of interest to me?**
I am personally interested in this project because I enjoy keeping track of my grades. Instead of relying on spreadsheets and manual calculations, I want to build an application that manages everything in one place, making it easier to monitor academic progress efficiently.

## User Stories

- **Add and remove items to collections:**
As a user, I want to be able to add/remove an assignment (or other graded item) to a given class, or add/remove a class to a term (e.g., Term 1 or Term 2), a year (e.g., first year, second year), or a specific category (e.g., CPSC, MATH).

- **View lists of items:**
As a user, I want to be able to view the list of assignments in a class or view a list of classes within a term or a year.

- **Track performance:**
As a user, I want to be able to see my performance (e.g., GPA) for a specific class, term, or overall year, displayed as a percentage, table, or graph.

- **Compare core vs. elective performance:**
As a user, I want to be able to view my performance in core classes versus electives, either separately or together.

- **Apply weightings and credits:**
As a user, I want to be able to apply the given weightings for assignments, as well as the credits for classes, so that these factors are taken into account when calculating grades.

- **Save grade tracker to file:**
As a user, I want to have the option to save my entire grade tracker — including all terms, courses, and assignments — to a file so that I can access my progress later.

- **Load grade tracker from file:**
As a user, I want to have the option to load a previously saved grade tracker file so that I can continue tracking my grades from where I left off.

## Instructions for End User

- You can view the panel that displays the Xs that have already been added to the Y by launching the Grade Tracker application and looking at the main list in the center of the window. This list shows all **courses (Xs)** that have been added to the **currently selected term (Y)**. You can change the term using the **"Select Term"** drop-down in the top-left; the course list will update for that term.

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by **adding a course to a term**. To do this, click the **"Add Course"** button in the bottom button panel, then enter the course code, credits, and whether it is a core course. The new course (X) will be added to the current term (Y) and appear in the course list.

- You can generate the second required action related to the user story "adding multiple Xs to a Y" by **filtering which courses in a term are shown**. Click the **"Filter Courses"** button in the bottom button panel, then choose **"All"**, **"Core"**, or **"Elective"** in the dialog that appears. This filters the list of courses (Xs) shown for the current term (Y).

- You can locate my visual component by looking at the **top area of the window**, just below the term header. There is a **Term Average progress bar** labeled with text like `Term Average: 0.0%` that visually shows your current term’s average grade as a bar that fills from 0 to 100%.

- You can save the state of my application by going to the menu bar at the top, clicking **"File"**, and then selecting **"Save"**. This writes all of your terms, courses, and assignments to `./data/my-grade-tracker.json`.

- You can reload the state of my application by going to the menu bar at the top, clicking **"File"**, and then selecting **"Load"**. This reads your previously saved terms, courses, and assignments from `./data/my-grade-tracker.json` and repopulates the GUI.

## Phase 4: Task 2

===== Event Log =====

Mon Nov 24 20:51:12 PST 2025
Course added: CPSC110 to Term 1 (2025)

Mon Nov 24 20:51:22 PST 2025
Assignment added: HW1 to CPSC110 (weight = 0.10, grade = 95.0)

Mon Nov 24 20:51:35 PST 2025
Assignment removed: HW1 from CPSC110
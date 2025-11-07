package ui;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;
import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

/*
 * Represents the main entry point for the Grade Tracker application
 * Handles user interaction through a console-based interface
 */

@ExcludeFromJacocoGeneratedReport
public class GradeTrackerApp {
    private Scanner input;
    private List<Term> terms;
    private static final String DATA_FILE = "./data/my-grade-tracker.json";

    public GradeTrackerApp() {
        input = new Scanner(System.in);
        terms = new ArrayList<>();
        runApp();
    }

    // EFFECTS: runs the GradeTracker application
    private void runApp() {
        boolean isRunning = true;
        while (isRunning) {
            displayMenu();
            String command = input.nextLine();
            if (!processCommand(command)) {
                break;
            }
        }
    }


    // EFFECTS: handles a single menu command; returns false to quit, true to continue
    private boolean processCommand(String commandRaw) {
        String c = commandRaw.trim().toLowerCase();
        switch (c) {
            case "1": 
                return run(this::addTerm);
            case "2": 
                return run(this::addCourseToTerm);
            case "3": 
                return run(this::addAssignmentToCourse);
            case "4": 
                return run(this::viewAverages);
            case "5": 
                return run(this::viewItems);
            default:
                return processSecondaryCommand(c);
        }
    }

    // EFFECTS: handles remaining menu commands; returns false to quit, true to continue
    private boolean processSecondaryCommand(String c) {
        switch (c) {
            case "6": 
                return quit();
            case "7": 
                return run(this::saveToFile);
            case "8": 
                return run(this::loadFromFile);
            case "9": 
                return run(this::removeCourseFromTerm);
            case "10": 
                return run(this::removeAssignmentFromCourse);
            default: 
                return invalid();
        }
    }

    // EFFECTS: runs the action and continues the app
    private boolean run(Runnable action) {
        action.run();
        return true;
    }

    // EFFECTS: prints goodbye and stops the app
    private boolean quit() {
        System.out.println("Goodbye!");
        return false;
    }

    // EFFECTS: prints invalid selection and continues the app
    private boolean invalid() {
        System.out.println("Invalid selection.");
        return true;
    }

    // EFFECTS: prompts user with what action they would like to perform with
    // the GradeTracker application
    private void displayMenu() {
        System.out.println("\nGrade Tracker Menu:");
        System.out.println("1 --> Add new term");
        System.out.println("2 --> Add course to term");
        System.out.println("3 --> Add assignment to course");
        System.out.println("4 --> View averages");
        System.out.println("5 --> View items");
        System.out.println("6 --> Quit");
        System.out.println("7 --> Save to file");
        System.out.println("8 --> Load from file");
        System.out.println("9 --> Remove course from term");
        System.out.println("10 --> Remove assignment from course");
        System.out.println("Select option: ");
    }

    // MODIFIES: this
    // EFFECTS: prompts user for term info and adds to list
    private void addTerm() {
        System.out.println("Enter term name: ");
        String name = input.nextLine();
        System.out.println("Enter year: ");
        int year = Integer.parseInt(input.nextLine());
        Term t = new Term(name, year);
        terms.add(t);
        System.out.println("Added " + name + " (" + year + ")");
    }

    // MODIFIES: this
    // EFFECTS: adds a course to a chosen term
    private void addCourseToTerm() {
        if (terms.isEmpty()) {
            System.out.println("No terms available. Please add one first.");
            return;
        }

        System.out.println("Select term:");
        for (int i = 0; i < terms.size(); i++) {
            System.out.println((i + 1) + ": " + terms.get(i).getTermName());
        }

        int termChoice = Integer.parseInt(input.nextLine()) - 1;
        Term selected = terms.get(termChoice);

        System.out.println("Enter course code (XXXX_000): ");
        String code = input.nextLine();
        System.out.println("Enter credits: ");
        int credits = Integer.parseInt(input.nextLine());
        System.out.println("Is this a core course? (y/n): ");
        boolean isCore = input.nextLine().equalsIgnoreCase("y");

        Course c = new Course(code, credits, isCore);
        selected.addCourse(c);
        System.out.println("Added course " + code + " to " + selected.getTermName());
    }

    // MODIFIES: this
    // EFFECTS: removes a course from a chosen term
    private void removeCourseFromTerm() {
        if (terms.isEmpty()) {
            System.out.println("No terms available. Please add one first.");
            return;
        }

        int termIndex = chooseTermIndex();
        if (termIndex == -1) {
            return;
        }

        Term term = terms.get(termIndex);
        if (term.getCourses().isEmpty()) {
            System.out.println("No courses in this term.");
            return;
        }

        int courseIndex = chooseCourseIndex(term);
        if (courseIndex == -1) {
            return;
        }

        Course toRemove = term.getCourses().get(courseIndex);
        term.removeCourse(toRemove);
        System.out.println("Removed course " + toRemove.getCourseCode() + " from " + term.getTermName() + ".");
    }

    // MODIFIES: this
    // EFFECTS: lets user pick a term, then a course, then add an assignment to that course
    private void addAssignmentToCourse() {
        if (terms.isEmpty()) {
            System.out.println("No terms available. Please add a term first.");
            return;
        }

        int termIndex = chooseTermIndex();
        if (termIndex == -1) {
            return;
        }
        Term term = terms.get(termIndex);

        if (term.getCourses().isEmpty()) {
            System.out.println("No courses in this term. Please add a course first.");
            return;
        }

        int courseIndex = chooseCourseIndex(term);
        if (courseIndex == -1) {
            return;
        }
        Course course = term.getCourses().get(courseIndex);

        Assignment a = promptAssignment();
        course.addAssignment(a);
        System.out.println("Added assignment \"" + a.getName() + "\" to " + course.getCourseCode());
    }

    // MODIFIES: this
    // EFFECTS: removes an assignment chosen by the user from a course in a term
    private void removeAssignmentFromCourse() {
        Course course = chooseCourseForModification();
        if (course == null) {
            return;
        }
        
        int assignmentIndex = chooseAssignmentIndex(course);
        if (assignmentIndex == -1) {
            return;
        }

        Assignment toRemove = course.getAssignments().get(assignmentIndex);
        course.removeAssignment(toRemove);
        System.out.println("Removed assignment \"" + toRemove.getName() + "\" from " + course.getCourseCode() + ".");
    }

    // MODIFIES: this
    // EFFECTS: prompts user to choose a course from a term;
    // if valid, returns the course, otherwise prints error and returns null
    private Course chooseCourseForModification() {
        if (terms.isEmpty()) {
            System.out.println("No terms available. Please add a term first.");
            return null;
        }

        int termIndex = chooseTermIndex();
        if (termIndex == -1) {
            return null;
        }
        
        Term term = terms.get(termIndex);
        if (term.getCourses().isEmpty()) {
            System.out.println("No courses in this term.");
            return null;
        }

        int courseIndex = chooseCourseIndex(term);
        if (courseIndex == -1) {
            return null;
        }

        return term.getCourses().get(courseIndex);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to choose an assignment from the given course;
    // if valid, returns 0-based index, otherwise prints error
    private int chooseAssignmentIndex(Course course) {
        if (course.getAssignments().isEmpty()) {
            System.out.println("No assignments in this course.");
            return -1;
        }

        System.out.println("Select an assignment to remove:");
        for (int i = 0; i < course.getAssignments().size(); i++) {
            System.out.println((i + 1) + ": " + course.getAssignments().get(i).getName());
        }

        return readIndex(course.getAssignments().size(), "Invalid assignment selection.");
    }

    // MODIFIES: this
    // EFFECTS: prints a numbered list of terms; if valid, returns 0-based index, otherwise prints error
    private int chooseTermIndex() {
        System.out.println("Select a term:");
        for (int i = 0; i < terms.size(); i++) {
            System.out.println((i + 1) + ": " + terms.get(i).getTermName());
        }
        return readIndex(terms.size(), "Invalid term selection.");
    }

    // MODIFIES: this
    // EFFECTS: print a numbered list of the term's courses; if valid, 
    // returns 0-based index, otherwise prints error
    private int chooseCourseIndex(Term term) {
        System.out.println("Select a course to add an assignment to: ");
        for (int i = 0; i < term.getCourses().size(); i++) {
            System.out.println((i + 1) + ": " + term.getCourses().get(i).getCourseCode());
        }
        return readIndex(term.getCourses().size(), "Invalid course selection.");
    }

    // REQUIRES: max >= 0
    // MODIFIES: this
    // EFFECTS: reads a 1-based integer selection and converts it to a 0-based index
    private int readIndex(int max, String onErrorMsg) {
        try {
            int index = Integer.parseInt(input.nextLine()) - 1;
            if (index < 0 || index >= max) {
                System.out.println(onErrorMsg);
                return -1;
            }
            return index;
        } catch (NumberFormatException e) {
            System.out.println(onErrorMsg);
            return -1;
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts for assignment name, weight, and grade,
    // constructs and returns a new assignment with those values
    private Assignment promptAssignment() {
        System.out.println("Enter assignment name: ");
        String name = input.nextLine();
        System.out.println("Enter assignment weight (0-1): ");
        double weight = Double.parseDouble(input.nextLine());
        System.out.println("Enter grade received (0-100): ");
        double grade = Double.parseDouble(input.nextLine());
        return new Assignment(name, weight, grade);
    }

    // EFFECTS: allows user to choose to view average by year or by category
    private void viewAverages() {
        System.out.println("View average by: ");
        System.out.println("1 --> Year");
        System.out.println("2 --> Term");
        System.out.println("3 --> Category (core/elective)");
        System.out.println("Select option: ");
        String choice = input.nextLine();

        switch (choice) {
            case "1":
                viewAverageByYear();
                break;
            case "2":
                viewTermAverages();
                break;
            case "3":
                viewAverageByCategory();
                break;
            default:
                System.out.println("Invalid selection.");
        }
    }

    // EFFECTS: allows user to choose to view courses in a term or assignments in a course
    private void viewItems() {
        System.out.println("view by: ");
        System.out.println("1 --> Courses in a term");
        System.out.println("2 --> Assignments in a course");
        System.out.println("Select option: ");
        String choice = input.nextLine();

        switch (choice) {
            case "1":
                viewCoursesInTerm();
                break;
            case "2": 
                viewAssignmentsInCourse();
                break;
            default: 
                System.out.println("Invalid selection.");
        }
    }

    // EFFECTS: displays the average grade across all terms in a given year
    private void viewAverageByYear() {
        System.out.println("Enter academic year: ");
        int year = Integer.parseInt(input.nextLine());

        double totalWeighted = 0.0;
        double totalCredits = 0.0;

        for (Term t : terms) {
            if (t.getTermYear() == year) {
                for (Course c: t.getCourses()) {
                    totalWeighted += c.getCourseAverage() * c.getCredits();
                    totalCredits += c.getCredits();
                }
            }
        }

        if (totalCredits == 0) {
            System.out.println("No courses found for that year.");
        } else {
            double avg = totalWeighted / totalCredits;
            String letter = GradeCalculator.convertToLetterGrade(avg);
            double gpa = GradeCalculator.letterToGPA(letter);
            System.out.println("Average for " + year + ": " + avg + "% (" + letter + "), GPA: " + gpa);
        }
    }

    // EFFECTS: prints each term's average grade and GPA
    private void viewTermAverages() {
        for (Term t : terms) {
            double avg = t.getTermAverage();
            String letter = GradeCalculator.convertToLetterGrade(avg);
            double gpa = GradeCalculator.letterToGPA(letter);

            System.out.println(t.getTermName() + " (" + t.getTermYear() + "):");
            System.out.println("Average: " + avg + "% " + letter + ", GPA " + gpa + ")");
        }
    }

    // EFFECTS: displays the average grade for core or elective courses across all terms
    private void viewAverageByCategory() {
        System.out.println("Enter category (core/elective): ");
        String category = input.nextLine().toLowerCase();

        double totalWeighted = 0.0;
        double totalCredits = 0.0;

        for (Term t : terms) {
            for (Course c : t.getCourses()) {
                if ((category.equals("core") && c.checkIsCore()) || (category.equals("elective") && !c.checkIsCore())) {
                    totalWeighted += c.getCourseAverage() * c.getCredits();
                    totalCredits += c.getCredits();
                }
            }
        }

        if (totalCredits == 0) {
            System.out.println("No courses found for that category.");
        } else {
            double avg = totalWeighted / totalCredits;
            String letter = GradeCalculator.convertToLetterGrade(avg);
            double gpa = GradeCalculator.letterToGPA(letter);
            System.out.println("Average for " + category + " courses: " + avg + "% (" + letter + "), GPA: " + gpa);
        }
    }

    // EFFECTS: lists all courses in a chosen term
    private void viewCoursesInTerm() {
        if (terms.isEmpty()) {
            System.out.println("No terms available.");
            return;
        }

        System.out.println("Enter term name: ");
        String termName = input.nextLine();

        for (Term t : terms) {
            if (t.getTermName().equalsIgnoreCase(termName)) {
                if (t.getCourses().isEmpty()) {
                    System.out.println("No courses in this term.");
                    return;
                }

                System.out.println("Courses in " + termName + ":");
                for (Course c : t.getCourses()) {
                    System.out.print("- " + c.getCourseCode() + " (" + c.getCredits() + " credits, ");
                    System.out.println((c.checkIsCore() ? "core" : "elective") + ")");
                }
                return;
            }
        }

        System.out.println("Term not found.");
    }

    // EFFECTS: displays all assignments in a selected course within a chosen term
    private void viewAssignmentsInCourse() {
        if (terms.isEmpty()) {
            System.out.println("No terms available.");
            return;
        }

        int termIndex = chooseTermIndex();
        if (termIndex == -1) {
            return;
        }

        Term term = terms.get(termIndex);
        if (term.getCourses().isEmpty()) {
            System.out.println("No courses in this term.");
            return;
        }

        int courseIndex = chooseCourseIndex(term);
        if (courseIndex == -1) {
            return;
        }

        Course course = term.getCourses().get(courseIndex);

        if (course.getAssignments().isEmpty()) {
            System.out.println("No assignments in this course.");
            return;
        }

        printAssignments(course);
    }

    // EFFECTS: prints all assignments for a course
    private void printAssignments(Course course) {
        System.out.println("Assignments in " + course.getCourseCode() + ":");
        for (Assignment a : course.getAssignments()) {
            System.out.println("- " + a.getName() + " (" + a.getWeight() + " weight, " + a.getGrade() + "%)");
        }
    }

    // EFFECTS: ensures ./data directory exists (so writer can create the file)
    private void ensureDataDir() {
        try {
            Path dir = Path.of("./data");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (Exception e) {
            System.out.println("Warning: couldn't ensure ./data directory: " + e.getMessage());
        }
    }

    // EFFECTS: writes current terms to DATA_FILE
    private void saveToFile() {
        ensureDataDir();
        try {
            JsonWriter writer = new JsonWriter(DATA_FILE);
            writer.open();
            writer.write(terms);
            writer.close();
            System.out.println("Saved to " + DATA_FILE);
        } catch (Exception e) {
            System.out.println("Save failed: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads terms from DATA_FILE
    private void loadFromFile() {
        try {
            JsonReader reader = new JsonReader(DATA_FILE);
            List<Term> loaded = reader.readTerms();
            this.terms = loaded;
            printLoadSummary();
        } catch (Exception e) {
            System.out.println("Load failed: " + e.getMessage());
        }
    }

    // EFFECTS: prints a brief summary of the loaded state
    private void printLoadSummary() {
        int termCount = terms.size();
        int courseCount = 0;
        int assignmentCount = 0;
        for (Term t : terms) {
            courseCount += t.getCourses().size();
            for (Course c : t.getCourses()) {
                assignmentCount += c.getAssignments().size();
            }
        }
        System.out.print("Loaded " + termCount + " term(s), ");
        System.out.print(courseCount + " course(s), " + assignmentCount + " assignment(s).");
    }
}

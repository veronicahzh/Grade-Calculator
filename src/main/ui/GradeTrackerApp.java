package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;

/*
 * Represents the main entry point for the Grade Tracker application
 * Handles user interaction through a console-based interface
 */

public class GradeTrackerApp {
    private Scanner input;
    private List<Term> terms;

    public GradeTrackerApp() {
        input = new Scanner(System.in);
        terms = new ArrayList<>();
        runApp();
    }

    // EFFECTS: runs the GradeTracker application
    @SuppressWarnings("methodlength")
    private void runApp() {
        boolean isRunning = true;
        while (isRunning) {
            displayMenu();
            String command = input.nextLine().toLowerCase();

            switch (command) {
                case "1": 
                    addTerm();
                    break;
                case "2": 
                    addCourseToTerm();
                    break;
                case "3": 
                    addAssignmentToCourse();
                    break;
                case "4": 
                    viewAverages();
                    break;
                case "5": 
                    viewItems();
                    break;
                case "6": 
                    isRunning = false;
                    System.out.println("Goodbye!");
                    break;
                default: 
                    System.out.println("Invalid selection.");
                    break;
            }
        }
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
    // EFFECTS: lets user pick a term, then a course, then add an assignment to that course
    @SuppressWarnings("methodlength")
    private void addAssignmentToCourse() {
        if (terms.isEmpty()) {
            System.out.println("No terms available. Please add a term first.");
            return;
        }
        
        System.out.println("Select a term:");
        for (int i = 0; i < terms.size(); i++) {
            System.out.println((i + 1) + ": " + terms.get(i).getTermName());
        }

        int termChoice = Integer.parseInt(input.nextLine()) - 1;
        Term selectedTerm = terms.get(termChoice);

        if (selectedTerm.getCourses().isEmpty()) {
            System.out.println("No courses in this term. Please add a course first.");
            return;
        }

        System.out.println("Select a course to add an assignment to: ");
        for (int i = 0; i < selectedTerm.getCourses().size(); i++) {
            System.out.println((i + 1) + ": " + selectedTerm.getCourses().get(i).getCourseCode());
        }

        int courseChoice = Integer.parseInt(input.nextLine()) - 1;
        Course selectedCourse = selectedTerm.getCourses().get(courseChoice);

        System.out.println("Enter assignment name: ");
        String name = input.nextLine();
        System.out.println("Enter assignment weight (0-1): ");
        double weight = Double.parseDouble(input.nextLine());
        System.out.println("Enter grade received (0-100): ");
        double grade = Double.parseDouble(input.nextLine());

        Assignment a = new Assignment(name, weight, grade);
        selectedCourse.addAssignment(a);

        System.out.println("Added assignment \"" + name + "\" to " + selectedCourse.getCourseCode());
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
    @SuppressWarnings("methodlength")
    private void viewAssignmentsInCourse() {
        if (terms.isEmpty()) {
            System.out.println("No terms available.");
            return;
        }

        System.out.println("Select a term:");
        for (int i = 0; i < terms.size(); i++) {
            System.out.println((i + 1) + ": " + terms.get(i).getTermName());
        }

        int termChoice = Integer.parseInt(input.nextLine());
        Term selectedTerm = terms.get(termChoice);

        if (selectedTerm.getCourses().isEmpty()) {
            System.out.println("No courses in this term.");
            return;
        }

        System.out.println("Select a course:");
        for (int i = 0; i < selectedTerm.getCourses().size(); i++) {
            System.out.println((i + 1) + ": " + selectedTerm.getCourses().get(i).getCourseCode());
        }

        int courseChoice = Integer.parseInt(input.nextLine()) - 1;
        Course selectedCourse = selectedTerm.getCourses().get(courseChoice);

        if (selectedCourse.getAssignments().isEmpty()) {
            System.out.println("No assignments in this course.");
            return;
        }

        System.out.println("Assignments in " + selectedCourse.getCourseCode() + ":");
        for (Assignment a : selectedCourse.getAssignments()) {
            System.out.println("- " + a.getName() + " (" + a.getWeight() + " weight, " + a.getGrade() + "%)");
        }
    }
}

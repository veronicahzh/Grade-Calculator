package ui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import static javax.swing.JOptionPane.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.Assignment;
import model.Course;
import model.GradeCalculator;
import model.Term;
import persistence.JsonReader;
import persistence.JsonWriter;

// Represents the GUI for the Grade Tracker application
@ExcludeFromJacocoGeneratedReport
public class GradeTrackerUI extends JFrame {
    private static final String DATA_FILE = "./data/my-grade-tracker.json";
    private List<Term> terms;
    private Term currentTerm;
    private JComboBox<Term> termSelector;
    private JTextArea courseDetails;
    private JProgressBar termAverageBar;
    private JLabel weightTotalLabel;
    private JLabel header;
    private DefaultListModel<Course> courseListModel;
    private JList<Course> courseList;

    // EFFECTS: constructs the main GradeTracker window
    public GradeTrackerUI() {
        super("Grade Tracker");

        terms = new ArrayList<>();
        currentTerm = new Term("Term 1", 2025);
        terms.add(currentTerm);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        initComponents();
        initMenuBar();

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the main GUI components
    private void initComponents() {
        setLayout(new BorderLayout());

        add(createTopPanel(), BorderLayout.NORTH);

        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(courseList);
        add(scrollPane, BorderLayout.CENTER);

        courseDetails = new JTextArea();
        courseDetails.setEditable(false);
        JScrollPane detailsScrollPane = new JScrollPane(courseDetails);
        detailsScrollPane.setBorder(BorderFactory.createTitledBorder("Course Details"));

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);

        weightTotalLabel = new JLabel("Weight total: —");
        detailsPanel.add(weightTotalLabel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, detailsPanel);
        splitPane.setResizeWeight(0.5);
        courseList.addListSelectionListener(e -> updateCourseDetails());

        add(createButtonPanel(), BorderLayout.SOUTH);
        add(splitPane, BorderLayout.CENTER);

        refreshCourseList();
    }

    // MODIFIES: this
    // EFFECTS: creates the top panel with term selector and header
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel termPanel = new JPanel();
        JLabel termLabel = new JLabel("Select Term: ");
        termPanel.add(termLabel);

        termSelector = new JComboBox<>();
        termPanel.add(termSelector);

        JButton addTermButton = new JButton("Add Term");
        addTermButton.addActionListener(e -> handleAddTerm());
        termPanel.add(addTermButton);

        header = new JLabel("Courses in " + currentTerm.getTermName() + " " + currentTerm.getTermYear());
        header.setHorizontalAlignment(SwingConstants.CENTER);

        termAverageBar = new JProgressBar(0, 100);
        termAverageBar.setStringPainted(true);
        updateTermAverageBar();

        topPanel.add(termPanel, BorderLayout.WEST);
        topPanel.add(header, BorderLayout.CENTER);
        topPanel.add(termAverageBar, BorderLayout.SOUTH);

        refreshTermSelector();
        termSelector.addActionListener(e -> handleTermChanged());
        
        return topPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates the button panel with action buttons
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        addCourseButtons(buttonPanel);
        addFilterAndViewButtons(buttonPanel);
        return buttonPanel;
    }

    // MODIFIES: this
    // EFFECTS: adds add/remove course/assignment buttons to panel
    private void addCourseButtons(JPanel buttonPanel) {
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(e -> handleAddCourse());
        buttonPanel.add(addCourseButton);

        JButton addAssignmentButton = new JButton("Add Assignment");
        addAssignmentButton.addActionListener(e -> handleAddAssignment());
        buttonPanel.add(addAssignmentButton);

        JButton removeCourseButton = new JButton("Remove Course");
        removeCourseButton.addActionListener(e -> handleRemoveCourse());
        buttonPanel.add(removeCourseButton);

        JButton removeAssignmentButton = new JButton("Remove Assignment");
        removeAssignmentButton.addActionListener(e -> handleRemoveAssignment());
        buttonPanel.add(removeAssignmentButton);
    }

    // MODIFIES: this
    // EFFECTS: adds filter and view buttons to panel
    private void addFilterAndViewButtons(JPanel buttonPanel) {
        JButton filterCoursesButton = new JButton("Filter Courses");
        filterCoursesButton.addActionListener(e -> handleFilterCourses());
        buttonPanel.add(filterCoursesButton);

        JButton viewAssignmentsButton = new JButton("View Assignments");
        viewAssignmentsButton.addActionListener(e -> handleViewAssignments());
        buttonPanel.add(viewAssignmentsButton);

        JButton viewAveragesButton = new JButton("View Averages");
        viewAveragesButton.addActionListener(e -> handleViewAverages());
        buttonPanel.add(viewAveragesButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu bar with save/load items
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> handleSave());
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> handleLoad());
        fileMenu.add(loadItem);

        JMenu viewMenu = new JMenu("View");
        
        JMenuItem viewAssignmentsItem = new JMenuItem("View Assignments");
        viewAssignmentsItem.addActionListener(e -> handleViewAssignments());
        viewMenu.add(viewAssignmentsItem);

        JMenuItem viewAveragesItem = new JMenuItem("View Averages");
        viewAveragesItem.addActionListener(e -> handleViewAverages());
        viewMenu.add(viewAveragesItem);

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        
        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to add new term and makes it the current term
    private void handleAddTerm() {
        String name = showInputDialog(this, "Enter term name:");
        if (name == null || name.trim().isEmpty()) {
            return;
        }

        String yearText = showInputDialog(this, "Enter year:");
        if (yearText == null || yearText.trim().isEmpty()) {
            return;
        }

        try {
            int year = Integer.parseInt(yearText.trim());
            Term t = new Term(name.trim(), year);
            terms.add(t);
            currentTerm = t;
            refreshTermSelector();
            header.setText("Courses in " + currentTerm.getTermName() + " " + currentTerm.getTermYear());
            refreshCourseList();
            updateTermAverageBar();
        } catch (NumberFormatException e) {
            showMessageDialog(this, "Invalid year.");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates currentTerm when term selection changes
    private void handleTermChanged() {
        Term selected = (Term) termSelector.getSelectedItem();
        if (selected == null) {
            return;
        }

        currentTerm = selected;

        if (header != null) {
            header.setText("Courses in " + currentTerm.getTermName() + " " + currentTerm.getTermYear());
        }

        refreshCourseList();
        updateTermAverageBar();
        updateWeightTotal();
    }

    // MODIFIES: this
    // EFFECTS: refreshes the term selector based on terms list
    private void refreshTermSelector() {
        if (termSelector == null) {
            return;
        }

        termSelector.removeAllItems();

        for (Term t : terms) {
            termSelector.addItem(t);
        }
        termSelector.setSelectedItem(currentTerm);
    }

    // MODIFIES: this
    // EFFECTS: prompts user to add a new course and adds it to currentTerm
    private void handleAddCourse() {
        String code = JOptionPane.showInputDialog(this, "Enter Course Code (XXXX_000):");
        if (code == null || code.trim().isEmpty()) {
            return;
        }

        String creditsStr = JOptionPane.showInputDialog(this, "Enter Credits:");
        if (creditsStr == null || creditsStr.trim().isEmpty()) {
            return;
        }

        try {
            int credits = Integer.parseInt(creditsStr);
            int option = JOptionPane.showConfirmDialog(this, "Is this a core course?", "Course Type",
                    JOptionPane.YES_NO_OPTION);
            boolean isCore = (option == JOptionPane.YES_OPTION);

            Course c = new Course(code.trim(), credits, isCore);
            currentTerm.addCourse(c);
            refreshCourseList();
            updateTermAverageBar();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid credits value.");
        }
    }

    // MODIIFES: this
    // EFFECTS: prompts user to remove selected course from currentTerm
    private void handleRemoveCourse() {
        Course selected = courseList.getSelectedValue();
        if (selected == null) {
            showMessageDialog(this, "Please select a course to remove.");
            return;
        }

        int result = showConfirmDialog(this, "Remove " + selected.getCourseCode() + "?", "Remove?", YES_NO_OPTION);

        if (result == YES_OPTION) {
            currentTerm.removeCourse(selected);
            refreshCourseList();
            updateTermAverageBar();
        }
    }

    // REQUIRES: 0.0 <= grade <= 100.0,  0.0 <= weight <= 1.0
    // MODIFIES: this
    // EFFECTS: prompts user to add a new assignment to selected course
    private void handleAddAssignment() {
        Course selected = courseList.getSelectedValue();
        if (selected == null) {
            showMessageDialog(this, "Please select a course to add an assignment to.");
            return;
        }
        String name = promptNonEmpty("Enter Assignment Name:");
        String weight = promptNonEmpty("Enter Assignment Weight (0.0 - 1.0):");
        String grade = promptNonEmpty("Enter Assignment Score (0.0 - 100.0):");
        if (name == null || weight == null || grade == null) {
            return;
        }
        try {
            double w = Double.parseDouble(weight);
            double g = Double.parseDouble(grade);
            selected.addAssignment(new Assignment(name, w, g));
            refreshCourseList();
            updateTermAverageBar();
        } catch (NumberFormatException ex) {
            showMessageDialog(this, "Invalid weight or grade value.");
        }
        refreshCourseList();
        updateTermAverageBar();
        updateCourseDetails();
    }


        // MODIFIES: this
    // EFFECTS: prompts user to remove selected assignment from selected course
    private void handleRemoveAssignment() {
        Course selected = courseList.getSelectedValue();
        if (selected == null) {
            showMessageDialog(this, "Please select a course first.");
            return;
        }

        int index = chooseAssignmentIndex(selected);
        if (index < 0) {
            return;
        }

        Assignment toRemove = selected.getAssignments().get(index);
        selected.removeAssignment(toRemove);
        showMessageDialog(this, "Removed assignment: " + toRemove.getName());

        refreshCourseList();
        updateTermAverageBar();
        updateCourseDetails();
    }

    // MODIFIES: this
    // EFFECTS: updates the list to show all courses in currentTerm
    private void refreshCourseList() {
        courseListModel.clear();
        for (Course c : currentTerm.getCourses()) {
            courseListModel.addElement(c);
        }
        updateWeightTotal();
    }

    // MODIFIES: this
    // EFFECTS: filters courses shown in the list by core/elective/all status
    private void handleFilterCourses() {
        String[] options = {"All", "Core", "Elective"};
        String msg = "Which courses do you want to see?";
        String title = "Filter Courses";
        int choice = showOptionDialog(this, msg, title, DEFAULT_OPTION, QUESTION_MESSAGE, null, options, options[0]);
        courseListModel.clear();
        for (Course c : currentTerm.getCourses()) {
            if (choice == 0 || (choice == 1 && c.checkIsCore()) || (choice == 2 && !c.checkIsCore())) {
                courseListModel.addElement(c);
            }
        }
    }

    // EFFECTS: shows all assignments for selected course
    private void handleViewAssignments() {
        Course selected = courseList.getSelectedValue();
        if (selected == null) {
            showMessageDialog(this, "Please select a course first.");
            return;
        }

        List<Assignment> assignments = selected.getAssignments();
        if (assignments.isEmpty()) {
            showMessageDialog(this, "No assignments in this course.");
            return;
        }

        StringBuilder sb = new StringBuilder("Assignments in " + selected.getCourseCode() + ":\n");
        for (Assignment a : assignments) {
            String item = "- " + a.getName() + " (" + a.getWeight() + ", " + a.getGrade() + "%)\n";
            sb.append(item);
        }

        showMessageDialog(this, sb.toString());
    }


    // EFFECTS: lets user choose how to view averages and displays accordingly
    private void handleViewAverages() {
        String[] options = {"By Year", "By Term", "By Course", "By Category"};
        String msg = "How would you like to view averages?";
        String title = "View Averages";
        int choice = showOptionDialog(this, msg, title, DEFAULT_OPTION, QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            showAverageByYearDialog();
        } else if (choice == 1) {
            showAverageByTermDialog();
        } else if (choice == 2) {
            showAverageByCourseDialog();
        } else if (choice == 3) {
            showAverageByCategoryDialog();
        }
    }

    // EFFECTS: prompts for year and shows average across all terms in that year
    private void showAverageByYearDialog() {
        String yearStr = showInputDialog(this, "Enter academic year:");
        if (yearStr == null || yearStr.trim().isEmpty()) {
            return;
        }

        try {
            int year = Integer.parseInt(yearStr.trim());
            double[] totals = calculateYearTotals(year);
            double totalWeighted = totals[0];
            double totalCredits = totals[1];

            if (totalCredits == 0.0) {
                showMessageDialog(this, "No courses found for year " + year);
                return;
            }

            double avg = totalWeighted / totalCredits;
            String letter = GradeCalculator.convertToLetterGrade(avg);
            double gpa = GradeCalculator.letterToGPA(letter);
            String msg = "Average for " + year + ": " + fmt(avg) + "% (" + letter + ",) GPA: " + gpa;
            showMessageDialog(this, msg);
        } catch (NumberFormatException e) {
            showMessageDialog(this, "Invalid year.");
        }
    }

    // EFFECTS: returns [totalWeighted, totalCredits] for given year
    private double[] calculateYearTotals(int year) {
        double totalWeighted = 0.0;
        double totalCredits = 0.0;

        for (Term t : terms) {
            if (t.getTermYear() == year) {
                for (Course c : t.getCourses()) {
                    totalWeighted += c.getCourseAverage() * c.getCredits();
                    totalCredits += c.getCredits();
                }
            }
        }
        return new double[] {totalWeighted, totalCredits};
    }

    // EFFECTS: shows average and GPA for each term
    private void showAverageByTermDialog() {
        if (terms.isEmpty()) {
            showMessageDialog(this, "No terms available.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Term t : terms) {
            double avg = t.getTermAverage();
            String letter = GradeCalculator.convertToLetterGrade(avg);
            double gpa = GradeCalculator.letterToGPA(letter);

            String termLabel = t.getTermName() + " (" + t.getTermYear() + ":) ";
            String avgLabel = fmt(avg) + "% (" + letter + "), GPA: " + gpa;
            
            sb.append(termLabel).append(avgLabel).append("\n");
        }
        showMessageDialog(this, sb.toString());
    }

    // EFFECTS: shows average and GPA for each course in currentTerm
    private void showAverageByCourseDialog() {
        if (currentTerm.getCourses().isEmpty()) {
            showMessageDialog(this, "No courses in current term.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        String headerLine = "Course averages for " + currentTerm.getTermName();
        headerLine += " (" + currentTerm.getTermYear() + "):\n";
        sb.append(headerLine);

        for (Course c : currentTerm.getCourses()) {
            double avg = c.getCourseAverage();
            String letter = GradeCalculator.convertToLetterGrade(avg);
            double gpa = GradeCalculator.letterToGPA(letter);
            String courseLine = c.getCourseCode() + ": " + fmt(avg) + "% (" + letter + "), GPA: " + gpa + "\n";
            
            sb.append(courseLine);
        }

        showMessageDialog(this, sb.toString());
    }

    // EFFECTS: shows average and GPA for core/elective courses in currentTerm
    private void showAverageByCategoryDialog() {
        if (currentTerm.getCourses().isEmpty()) {
            showMessageDialog(this, "No courses in current term.");
            return;
        }

        int choice = promptCategoryChoice();
        if (choice < 0) {
            return;
        }

        double[] totals = calculateCategoryTotals(choice);
        double totalWeighted = totals[0];
        double totalCredits = totals[1];

        if (totalCredits == 0.0) {
            String cat = (choice == 0) ? "core" : "elective";
            showMessageDialog(this, "No " + cat + " courses in current term.");
            return;
        }

        showCategoryAverage(choice, totalWeighted, totalCredits);
    }

    // EFFECTS: prompts user for category
    private int promptCategoryChoice() {
        String[] opts = {"Core Courses", "Elective Courses"};
        String msg = "Which category do you want to view?";
        String t = "Average by Category";
        Object init = opts[0];
        int choice = showOptionDialog(this, msg, t, DEFAULT_OPTION, QUESTION_MESSAGE, null, opts, init);
        return choice;
    }

    // EFFECTS: returns [totalWeighted, totalCredits] for chosen category
    private double[] calculateCategoryTotals(int category) {
        double totalWeighted = 0.0;
        double totalCredits = 0.0;

        for (Course c : currentTerm.getCourses()) {
            if ((category == 0 && c.checkIsCore()) || (category == 1 && !c.checkIsCore())) {
                totalWeighted += c.getCourseAverage() * c.getCredits();
                totalCredits += c.getCredits();
            }
        }
        return new double[] {totalWeighted, totalCredits};
    }

    // EFFECTSL shows the final average result for the chosen category
    private void showCategoryAverage(int category, double totalWeighted, double totalCredits) {
        double avg = totalWeighted / totalCredits;
        String letter = GradeCalculator.convertToLetterGrade(avg);
        double gpa = GradeCalculator.letterToGPA(letter);

        String catLabel = (category == 0) ? "core" : "elective ";
        String result = "Average for " + catLabel + " courses in ";
        result += currentTerm.getTermName() + " (" + currentTerm.getTermYear() + "): ";
        result += fmt(avg) + "% (" + letter + "), GPA: " + gpa;
        showMessageDialog(this, result);
    }

    // MODIFIES: this
    // EFFECTS: saves terms list to DATA_FILE
    private void handleSave() {
        ensureDataDir();
        try {
            JsonWriter writer = new JsonWriter(DATA_FILE);
            writer.open();
            writer.write(terms);
            writer.close();
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: loads terms from DATA_FILE and updates currentTerm
    private void handleLoad() {
        try {
            JsonReader reader = new JsonReader(DATA_FILE);
            List<Term> loaded = reader.readTerms();
            if (loaded.isEmpty()) {
                showMessageDialog(this, "No terms found in file.");
                return;
            }

            terms = loaded;
            currentTerm = terms.get(0);
            refreshTermSelector();
            header.setText("Courses in " + currentTerm.getTermName() + " " + currentTerm.getTermYear());
            refreshCourseList();
            updateTermAverageBar();

            JOptionPane.showMessageDialog(this, "Loaded " + terms.size() + " terms from file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load: " + e.getMessage());
        }
    }

    // MODIFIES: this
    // EFFECTS: ensures that the data directory exists
    private void ensureDataDir() {
        try {
            Path dir = Path.of("./data");
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to create data directory: " + e.getMessage());
        }
    }

    // EFFECTS: prompts user and return trimmed non-empty input, or null if cancelled/empty
    private String promptNonEmpty(String prompt) {
        String value = showInputDialog(this, prompt);
        if (value == null) {
            return null;
        }

        value = value.trim();
        if (value.isEmpty()) {
            return null;
        }

        return value;
    }

    // EFFECTS: lets user choose an assignment from course; returns index or -1
    private int chooseAssignmentIndex(Course course) {
        List<Assignment> assignments = course.getAssignments();
        if (assignments.isEmpty()) {
            showMessageDialog(this, "No assignments to remove in this course.");
            return -1;
        }

        String[] options = new String[assignments.size()];
        for (int i = 0; i < assignments.size(); i++) {
            options[i] = assignments.get(i).getName();
        }

        String msg = "Select an assignment to remove:";
        String title = "Remove Assignment";
        int choice = showOptionDialog(this, msg, title, DEFAULT_OPTION, QUESTION_MESSAGE, null, options, options[0]);
        return choice;
    }

    // EFFECTS: updates side panel with info about selected course
    private void updateCourseDetails() {
        Course selected = courseList.getSelectedValue();
        if (selected == null) {
            courseDetails.setText("");
            updateWeightTotal(); // ensure label shows '—' when nothing selected
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Course Code: ").append(selected.getCourseCode()).append("\n");
        sb.append("Credits: ").append(selected.getCredits()).append("\n");

        String type = selected.checkIsCore() ? "core" : "elective";
        sb.append("Type: ").append(type).append("\n");

        double avg = selected.getCourseAverage();
        String letter = GradeCalculator.convertToLetterGrade(avg);
        double gpa = GradeCalculator.letterToGPA(letter);
        String avgLine = "Average: " + fmt(avg) + "% (" + letter + "), GPA: " + gpa + "\n";
        sb.append(avgLine);

        sb.append("Assignments:\n");
        for (Assignment a : selected.getAssignments()) {
            String line = "- " + a.getName() + ": Weight " + a.getWeight() + ", Grade " + a.getGrade() + "%\n";
            sb.append(line);
        }
        courseDetails.setText(sb.toString());

        updateWeightTotal();
    }

    // EFFECTS: returns the sum of assignment weights for a course
    private double sumWeights(Course c) {
        double sum = 0.0;
        for (Assignment a : c.getAssignments()) {
            sum += a.getWeight();
        }
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: updates the weightTotalLabel for the selected course
    private void updateWeightTotal() {
        if (weightTotalLabel == null) {
            return;
        }
        Course selected = courseList.getSelectedValue();
        if (selected == null) {
            weightTotalLabel.setText("Weight total: —");
            return;
        }
        double total = sumWeights(selected);
        weightTotalLabel.setText("Weight total: " + fmt(total) + " / 1.00");
    }


    // MODIFIES: this
    // EFFECTS: updates progress bar to show current term average
    private void updateTermAverageBar() {
        if (termAverageBar == null || currentTerm == null) {
            return;
        }

        double avg = computeDisplayedTermAverage(currentTerm);
        int value = (int) Math.round(avg);
        termAverageBar.setValue(value);
        termAverageBar.setString("Term Average: " + fmt(avg) + "%");
    }

    // EFFECTS: computes the term average based only on courses with at least one assignment
    private double computeDisplayedTermAverage(Term term) {
        double totalWeighted = 0.0;
        double totalCredits = 0.0;

        for (Course c : term.getCourses()) {
            if (!c.getAssignments().isEmpty()) {
                totalWeighted += c.getCourseAverage() * c.getCredits();
                totalCredits += c.getCredits();
            }
        }

        if (totalCredits == 0.0) {
            return 0.0;
        }
        return totalWeighted / totalCredits;
    }

    // EFFECTS: returns value formatted to 1 decimal place
    private String fmt(double value) {
        return String.format("%.1f", value);
    }

    // EFFECTS: starts the GradeTracker GUI
    public static void main(String[] args) {
        new GradeTrackerUI();
    }

}

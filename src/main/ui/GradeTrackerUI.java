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

        add(createButtonPanel(), BorderLayout.SOUTH);

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

        topPanel.add(termPanel, BorderLayout.WEST);
        topPanel.add(header, BorderLayout.CENTER);
        refreshTermSelector();
        termSelector.addActionListener(e -> handleTermChanged());
        
        return topPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates the button panel with action buttons
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(e -> handleAddCourse());
        buttonPanel.add(addCourseButton);

        JButton addAssignmentButton = new JButton("Add Assignment");
        addAssignmentButton.addActionListener(e -> handleAddAssignment());
        buttonPanel.add(addAssignmentButton);

        JButton filterCoursesButton = new JButton("Filter Courses");
        filterCoursesButton.addActionListener(e -> handleFilterCourses());
        buttonPanel.add(filterCoursesButton);

        JButton removeCourseButton = new JButton("Remove Course");
        removeCourseButton.addActionListener(e -> handleRemoveCourse());
        buttonPanel.add(removeCourseButton);

        JButton removeAssignmentButton = new JButton("Remove Assignment");
        removeAssignmentButton.addActionListener(e -> handleRemoveAssignment());
        buttonPanel.add(removeAssignmentButton);

        return buttonPanel;
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

        menuBar.add(fileMenu);
        
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

        int result = showConfirmDialog(this, "Remove course " + selected.getCourseCode() + "?", "Confirm Removal", YES_NO_OPTION);

        if (result == YES_OPTION) {
            currentTerm.removeCourse(selected);
            refreshCourseList();
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to add a new assignment to selected course
    private void handleAddAssignment() {
        Course selected = courseList.getSelectedValue();
        if (selected == null) {
            showMessageDialog(this, "Please select a course to add an assignment to.");
            return;
        }

        String name = JOptionPane.showInputDialog(this, "Enter Assignment Name:");
        if (name == null) {
            return;
        }

        String weight = JOptionPane.showInputDialog(this, "Enter Assignment Weight (0.0 - 1.0):");
        if (weight == null) {
            return;
        }

        String grade = JOptionPane.showInputDialog(this, "Enter Assignment Score (0.0 - 100.0):");
        if (grade == null) {
            return;
        }

        try {
            double w = Double.parseDouble(weight);
            double g = Double.parseDouble(grade);

            selected.addAssignment(new Assignment(name.trim(), w, g));
            refreshCourseList();
        } catch (NumberFormatException ex) {
            showMessageDialog(this, "Invalid weight or grade value.");
        }
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
    }

    // MODIFIES: this
    // EFFECTS: updates the list to show all courses in currentTerm
    private void refreshCourseList() {
        courseListModel.clear();
        for (Course c : currentTerm.getCourses()) {
            courseListModel.addElement(c);
        }
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

    // EFFECTS: proompts user and reutrn trimmed non-empty input, or null if cancelled/empty
    private String promptNonEmpty(String prompt) {
        String value = showInputDialog(this, prompt);
        if (value == null ) {
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

    // EFFECTS: starts the GradeTracker GUI
    public static void main(String[] args) {
        new GradeTrackerUI();
    }

}

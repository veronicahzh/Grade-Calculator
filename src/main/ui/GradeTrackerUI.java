package ui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.util.*;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import model.Course;
import model.Term;

// Represents the GUI for the Grade Tracker application
@ExcludeFromJacocoGeneratedReport
public class GradeTrackerUI extends JFrame {
    private List<Term> terms;
    private Term currentTerm;

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

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the main GUI components
    private void initComponents() {
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Courses in " + currentTerm.getTermName() + " " + currentTerm.getTermYear());
        header.setHorizontalAlignment(SwingConstants.CENTER);
        add(header, BorderLayout.NORTH);

        courseListModel = new DefaultListModel<>();
        courseList = new JList<>(courseListModel);
        courseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(courseList);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(e -> handleAddCourse());
        buttonPanel.add(addCourseButton);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshCourseList();
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

    // MODIFIES: this
    // EFFECTS: updates the list to show all courses in currentTerm
    private void refreshCourseList() {
        courseListModel.clear();
        for (Course c : currentTerm.getCourses()) {
            courseListModel.addElement(c);
        }
    }


    // EFFECTS: starts the GradeTracker GUI
    public static void main(String[] args) {
        new GradeTrackerUI();
    }

}

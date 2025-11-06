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

    }

    // EFFECTS: starts the GradeTracker GUI
    public static void main(String[] args) {
        new GradeTrackerUI();
    }

}

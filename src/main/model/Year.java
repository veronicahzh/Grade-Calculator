package model;

import java.util.ArrayList;
import java.util.List;

/*
 * Represents a school year grouping of academic terms.
 * Each Year has a year number (e.g., 1, 2, 3, 4) and a list of Terms.
 */

public class Year {
    private int yearNumber;     // the numeric identifier for the year
    private List<Term> terms;   // the list of terms within this year

    public Year(int yearNumber) {
        // stub
    }

    public void addTerm(Term t) {
        // stub
    }

    public List<Term> getTerms() {
        return null;
    }

    public int getYearNumber() {
        return 0;
    }

    public double getAverage() {
        return 0.0;
    }
}
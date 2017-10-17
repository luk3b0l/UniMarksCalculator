/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unimarkscalculator;

import java.util.*;

/**
 *
 * @author Lukasz Bol
 */
public class Module 
{
    private String year;
    private String name;
    private String semester;
    private int credits;
    private int grade;
    private ArrayList<Assignment> assignments; 
    
    public Module(String newYear, String newName, String newSemester, int newCredits)
    {
        this.year = newYear;
        this.name = newName;
        this.semester = newSemester;
        this.credits = newCredits;
        assignments = new ArrayList<Assignment>();
    }
    
    public void addAssignment(String name, String type, double result, double weight)
    {
        Assignment tempAssignment = new Assignment(name, type, result, weight);
        assignments.add(tempAssignment);
    }
    
    public String listAllAssignments()
    {
        String s = "";
        for(Assignment temp : assignments)
        {
            s = s + temp.toString() + "\n";
        }
        return s;
    }

    public int getGrade() 
    {
        return grade;
    }

    public void setGrade(int grade) 
    {
        this.grade = grade;
    }
    
    public String toString()
    {
        String s = "***** MODULE INFO *****" + 
                   "\nModule: " + name + 
                   "\nYear: " + year + 
                   "\nSemester: " + semester + 
                   "\nCredits: " + credits + 
                   "\nGrade: " + grade;
        for(Assignment temp : assignments)
        {
            s = s + "\nAssignment: " + temp.toString();
        }
        return s;
    }
}
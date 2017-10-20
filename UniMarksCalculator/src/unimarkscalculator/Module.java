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

    public String getYear() 
    {
        return year;
    }

    public String getSemester() 
    {
        return semester;
    }

    public int getCredits() 
    {
        return credits;
    }

    public void setYear(String year) 
    {
        this.year = year;
    }

    public void setSemester(String semester) 
    {
        this.semester = semester;
    }

    public void setCredits(int credits) 
    {
        this.credits = credits;
    }
    
    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public int getGrade() 
    {
        return grade;
    }

    public void setGrade(int grade) 
    {
        this.grade = grade;
    }
    
    public void updateModuleInfo(String year, String name, String semester, int credits)
    {
        setYear(year);
        setName(name);
        setSemester(semester);
        setCredits(credits);
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
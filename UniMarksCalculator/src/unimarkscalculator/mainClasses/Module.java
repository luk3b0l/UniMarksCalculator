package unimarkscalculator.mainClasses;

import java.util.*;

/**
 *
 * @author Lukasz Bol
 */
public class Module 
{
    private String level;
    private String name;
    private String semester;
    private int credits;
    private int grade;
    private double totalAssignmentsWeight;
    private ArrayList<Assignment> assignments; 
    
    public Module(String newLevel, String newName, String newSemester, int newCredits)
    {
        this.level = newLevel;
        this.name = newName;
        this.semester = newSemester;
        this.credits = newCredits;
        this.totalAssignmentsWeight = 0;
        assignments = new ArrayList<Assignment>();
    }
    
    public void addAssignment(String name, String type, double result, double weight)
    {
        double newTotalWeight = getTotalAssignmentsWeight() + weight;
        setTotalAssignmentsWeight(newTotalWeight);
        System.out.println("TOTAL WEIGHT:" + getTotalAssignmentsWeight());
        Assignment tempAssignment = new Assignment(name, type, result, weight);
        assignments.add(tempAssignment);
    }
    
    public void removeAssignment(String name)
    {
        for(Assignment tempAssignment : assignments)
        {
            if((tempAssignment.getTitle()).equals(name))
            {
                double newTotalWeight = getTotalAssignmentsWeight() - tempAssignment.getWeightPercent();
                setTotalAssignmentsWeight(newTotalWeight);
                System.out.println("TOTAL WEIGHT:" + getTotalAssignmentsWeight());
                assignments.remove(tempAssignment);
                break;
            }
        }
    }
    
    public void updateAssignment(String oldTitle, String newTitle, String type, double result, double weight)
    {
        for(Assignment tempAssignment : assignments)
        {
            if((tempAssignment.getTitle()).equals(oldTitle))
            {
                double newTotalWeight = (getTotalAssignmentsWeight() - tempAssignment.getWeightPercent()) + weight;
                setTotalAssignmentsWeight(newTotalWeight);
                tempAssignment.setTitle(newTitle);
                tempAssignment.setType(type);
                tempAssignment.setResult(result);
                tempAssignment.setWeightPercent(weight);
                System.out.println("TOTAL WEIGHT:" + getTotalAssignmentsWeight());
                break;
            }
        }
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
    public Assignment getAssignment(String name)
    {
        Assignment returnAssignment = null;
        for(Assignment tempAssignment : assignments)
        {
            if((tempAssignment.getTitle()).equals(name))
            {
                return returnAssignment = tempAssignment;
            }
        }
        return returnAssignment;
    }
    
    public ArrayList<Assignment> getAllAssignments()
    {
        return assignments;
    }

    public String getLevel() 
    {
        return level;
    }

    public String getSemester() 
    {
        return semester;
    }

    public int getCredits() 
    {
        return credits;
    }

    public void setLevel(String level) 
    {
        this.level = level;
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
    
    public void updateModuleInfo(String level, String name, String semester, int credits)
    {
        setLevel(level);
        setName(name);
        setSemester(semester);
        setCredits(credits);
    }
    
    public boolean checkAssignmentExists(String assignmentName)
    {
        boolean result = false;
        for(Assignment tempAssignment : assignments)
        {
            if(((tempAssignment.getTitle()).toLowerCase()).equals(assignmentName.toLowerCase()))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    public double getTotalAssignmentsWeight() 
    {
        return totalAssignmentsWeight;
    }

    public void setTotalAssignmentsWeight(double newTotalAssignmentsWeight) 
    {
        this.totalAssignmentsWeight = newTotalAssignmentsWeight;
    }
    
    public String toString()
    {
        String s = "***** MODULE INFO *****" + 
                   "\nModule: " + name + 
                   "\nLevel: " + level + 
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
package unimarkscalculator.mainClasses;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private double grade;
    private double totalAssignmentsWeight;
    private ArrayList<Assignment> listAssignments; 
    
    public Module(String newLevel, String newName, String newSemester, int newCredits)
    {
        this.level = newLevel;
        this.name = newName;
        this.semester = newSemester;
        this.credits = newCredits;
        this.totalAssignmentsWeight = 0;
        listAssignments = new ArrayList<Assignment>();
    }
    
    public void addAssignment(String name, String type, double result, double weight)
    {
        double newTotalWeight = getTotalAssignmentsWeight() + weight;
        setTotalAssignmentsWeight(newTotalWeight);
        Assignment tempAssignment = new Assignment(name, type, result, weight);
        listAssignments.add(tempAssignment);
    }
    
    public void removeAssignment(String nameOfAssignmentToBeRemoved)
    {
        for(Assignment tempAssignment : listAssignments)
        {
            if((tempAssignment.getTitle()).equals(nameOfAssignmentToBeRemoved))
            {
                double newTotalWeight = getTotalAssignmentsWeight() - tempAssignment.getWeightPercent();
                setTotalAssignmentsWeight(newTotalWeight);
                listAssignments.remove(tempAssignment);
                break;
            }
        }
    }
    
    public void updateAssignment(String oldTitle, String newTitle, String newType, double newResult, double newWeight)
    {
        for(Assignment tempAssignment : listAssignments)
        {
            if((tempAssignment.getTitle()).equals(oldTitle))
            {
                double newTotalWeight = (getTotalAssignmentsWeight() - tempAssignment.getWeightPercent()) + newWeight;
                setTotalAssignmentsWeight(newTotalWeight);
                tempAssignment.setTitle(newTitle);
                tempAssignment.setType(newType);
                tempAssignment.setResult(newResult);
                tempAssignment.setWeightPercentage(newWeight);
                break;
            }
        }
    }
    
    public String listAllAssignments()
    {
        String existingAssignmentsInfo = "";
        if(!listAssignments.isEmpty())
        {
            for(Assignment tempAssignment : listAssignments)
            {
                existingAssignmentsInfo = existingAssignmentsInfo + tempAssignment.toString() + "\n";
            }
        }
        return existingAssignmentsInfo;
    }
    
    public Assignment getAssignment(String nameOfassignmentToBeRetrieved)
    {
        Assignment returnedAssignment = null;
        for(Assignment tempAssignment : listAssignments)
        {
            if((tempAssignment.getTitle()).equals(nameOfassignmentToBeRetrieved))
            {
                return returnedAssignment = tempAssignment;
            }
        }
        return returnedAssignment;
    }
    
    public ArrayList<Assignment> getAllAssignments()
    {
        return listAssignments;
    }
    
    public void calculateAndSetGrade()
    {
        ArrayList<Assignment> currentAssignments = getAllAssignments();
        double newResult = 0;
        for(Assignment tempAssignment : currentAssignments)
        {
            newResult = newResult + (tempAssignment.getResult() * (tempAssignment.getWeightPercent() * 0.01));
        }       
        setGrade(newResult);
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

    public void setCredits(int newCredits) 
    {
        this.credits = newCredits;
    }
    
    public String getName() 
    {
        return name;
    }

    public void setName(String newName) 
    {
        this.name = newName;
    }

    public double getGrade() 
    {
        return grade;
    }

    public void setGrade(double newGrade) 
    {
        BigDecimal tempGrade = new BigDecimal(newGrade);
        BigDecimal roundOff = tempGrade.setScale(2, RoundingMode.HALF_UP);
        this.grade = roundOff.doubleValue();
    }
    
    public void updateModuleInfo(String newLevel, String newName, String newSemester, int newCredits)
    {
        setLevel(newLevel);
        setName(newName);
        setSemester(newSemester);
        setCredits(newCredits);
    }
    
    public boolean checkAssignmentExists(String assignmentName)
    {
        boolean assignmentExists = false;
        for(Assignment tempAssignment : listAssignments)
        {
            if(((tempAssignment.getTitle()).toLowerCase()).equals(assignmentName.toLowerCase()))
            {
                assignmentExists = true;
                break;
            }
        }
        return assignmentExists;
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
        String moduleInfo = "===== MODULE: " + name + 
                   "\n--Level: " + level + 
                   "\n--Semester: " + semester + 
                   "\n--Credits: " + credits;
        
        if(totalAssignmentsWeight < 100)
        {
            moduleInfo = moduleInfo + "\n--Completed(%): " + totalAssignmentsWeight + "\n"; 
        }
        else
        {
            moduleInfo = moduleInfo + "\n--Grade(%): " + grade + "\n";
        }
        
        moduleInfo = moduleInfo + "\n\t=== ASSIGNMENTS";
        if(!listAssignments.isEmpty())
        {
            for(Assignment tempAssignment : listAssignments)
            {
                moduleInfo = moduleInfo + tempAssignment.toString();
            }
            moduleInfo = moduleInfo + "\n";
        }
        else
        {
            moduleInfo = moduleInfo + "\n\t<none>\n\n";
        }
        return moduleInfo;
    }
}
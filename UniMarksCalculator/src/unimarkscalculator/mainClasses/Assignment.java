package unimarkscalculator.mainClasses;

/**
 *
 * @author Lukasz Bol
 */
public class Assignment 
{
    private String title;
    private String type;
    private double result;
    private double weightPercentage;
    private final static int TOTAL_WEIGHT = 100;
    
    public Assignment(String newTitle, String newType, double newResult, double newWeightPercentage)
    {
        this.title = newTitle;
        this.type = newType;
        this.result = newResult;
        this.weightPercentage = newWeightPercentage;
    }

    public String getTitle() 
    {
        return title;
    }

    public String getType() 
    {
        return type;
    }

    public double getResult() 
    {
        return result;
    }
    
    public double getWeightPercent() 
    {
        return weightPercentage;
    }    

    public void setTitle(String newTitle) 
    {
        this.title = newTitle;
    }

    public void setType(String newType) 
    {
        this.type = newType;
    }

    public void setResult(double newResult) 
    {
        this.result = newResult;
    }

    public void setWeightPercentage(double newWeightPercentage) 
    {
        this.weightPercentage = newWeightPercentage;
    }
    
    public String toString()
    {
        String assignmentInfo = "\n\tTitle: " + title + 
                                "\n\tType: " + type + 
                                "\n\tResult(%): " + result + 
                                "\n\tWeight(%): " + weightPercentage + "\n";
        return assignmentInfo;
    }
}
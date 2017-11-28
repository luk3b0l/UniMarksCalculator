package unimarkscalculator.mainClasses;

/**
 *
 * @author Lukasz Bol
 */
public class Assignment 
{
    private String title;
    private String type;    // test/exam/coursework
    private double result;
    private double weightPercent;
    private final static int TOTAL_WEIGHT = 100;
    
    public Assignment(String newTitle, String newType, double newResult, double newWeightPercent)
    {
        this.title = newTitle;
        this.type = newType;
        this.result = newResult;
        this.weightPercent = newWeightPercent;
    }

    public String getTitle() {
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
        return weightPercent;
    }    

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public void setResult(double result) 
    {
        this.result = result;
    }

    public void setWeightPercent(double weightPercent) 
    {
        this.weightPercent = weightPercent;
    }
    
    public String toString()
    {
        String s = ":: Assignment INFO ::" + 
                   "\nTitle:" + title + 
                   "\nType: " + type + 
                   "\nResult: " + result + 
                   "\nWeight(%): " + weightPercent;
        return s;
    }
}
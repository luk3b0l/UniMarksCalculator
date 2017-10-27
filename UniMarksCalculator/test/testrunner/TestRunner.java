package testrunner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import unimarkscalculator.mainClasses.AssignmentTest;
import unimarkscalculator.mainClasses.ModuleTest;

/**
 *
 * @author Lukasz Bol
 */
public class TestRunner 
{
    public static void main (String[] args)
    {
        Result result = JUnitCore.runClasses(ModuleTest.class);
        
        for(Failure failure : result.getFailures())
        {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
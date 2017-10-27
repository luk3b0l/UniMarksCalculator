package unimarkscalculator.mainClasses;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lukasz Bol
 */
public class ModuleTest 
{
    private static Module testModule;
    
    public ModuleTest(){}
    
    @BeforeClass
    public static void setUpClass()
    {
        testModule = new Module("6", "Cybersecurity", "a", 15);
    }
    
    @AfterClass
    public static void tearDownClass(){}
    
    @Before
    public void setUp(){}
    
    @After
    public void tearDown(){}    
    
    @Test
    public void getLevel()
    {
        String level = "6";
        assertEquals(testModule.getLevel(), level);
    }
    
    @Test
    public void getModuleName()
    {
        String moduleName = "Cybersecurity";
        assertEquals(testModule.getName(), moduleName);
    }
    
    @Test
    public void getSemester()
    {
        String semester = "a";
        assertEquals(testModule.getSemester(), semester);
    }
    
    @Test
    public void getCredits()
    {
        double credits = 15;
        assertEquals(credits, testModule.getCredits(), 0);  //expected, actual, delta
    }             
}
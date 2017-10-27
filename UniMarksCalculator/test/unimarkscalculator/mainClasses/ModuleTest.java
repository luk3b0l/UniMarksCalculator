package unimarkscalculator.mainClasses;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
    public void testGetModuleName()
    {
        String moduleName = "Cybersecurity";
        assertEquals(testModule.getName(), moduleName);
    }
    
    @Test
    public void testGetSemester()
    {
        String semester = "a";
        assertEquals(testModule.getSemester(), semester);
    }
    
    @Test
    public void testGetCredits()
    {
        double credits = 15;
        assertEquals(credits, testModule.getCredits(), 0);  //expected, actual, delta
    }             
    
    @Test
    public void testSucceedCreatingNewModule()
    {
        Module testSuccessfulModule = new Module("4", "Artificial Intelligence", "b", 15);
        assertNotNull(testSuccessfulModule);
    }
}
package unimarkscalculator.mainClasses;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lukasz Bol
 */
public class ModulesManagerTest 
{

    private static ModulesManager testingModulesManager = ModulesManager.getInstance();

    
    public ModulesManagerTest(){}
    
    @BeforeClass
    public static void setUpClass(){}
    
    @AfterClass
    public static void tearDownClass(){}
    
    @Before
    public void setUp(){}
    
    @After
    public void tearDown(){} 
    
    @Test
    public void testGetInstance()
    {
        
        String description = "NEW ModulesManager";
        testingModulesManager.setDescription(description);
        Assert.assertEquals(description, testingModulesManager.getDescription());
    }
    
    @Test
    public void testAddModule()
    {
        
        testingModulesManager.addModule("5", "Database Concepts", "B", 15);
        
        Module testModule = testingModulesManager.getModule("Database Concepts");
        Assert.assertEquals("Database Concepts", testModule.getName());
    }
    
    @Test 
    public void testRemoveModule()
    {
        //testingModulesManager.addModule("4", "Computer Science Development Exercise", "B", 15);
        testingModulesManager.addModule("6", "Project Planning", "A", 15);
        testingModulesManager.removeModule("Project Planning");
        
        Assert.assertNull(testingModulesManager.getModule("Project Planning"));
    }
    
    @Test
    public void testGetModulesManagerDescription()
    {
        Assert.assertEquals("USER MODULES MANAGER", testingModulesManager.getDescription());
    }
    
    @Test
    public void testSetModulesManagerDescription()
    {
        testingModulesManager.setDescription("User1 MODULES MANAGER");
        Assert.assertEquals("User1 MODULES MANAGER", testingModulesManager.getDescription());
    }
    
    @Test
    public void testSetModulesManagerDescriptionEdgeCase1()
    {
        testingModulesManager.setDescription("");
        Assert.assertEquals("<no description set>", testingModulesManager.getDescription());
    }
    
    @Test
    public void testSetModulesManagerDescriptionEdgeCase2()
    {
        testingModulesManager.setDescription(null);
        Assert.assertEquals("<no description set>", testingModulesManager.getDescription());
    }
}
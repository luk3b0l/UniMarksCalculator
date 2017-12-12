package unimarkscalculator.mainClasses;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 *
 * @author Lukasz Bol
 */
public class ModulesManager 
{
    private static ModulesManager theManager;
    private String description;
    private ArrayList<Module> listUserModules;
    
    private ModulesManager()
    {
        this.description = "USER MODULES MANAGER";
        listUserModules = new ArrayList<Module>();
    }
    
    /*
    * Helps with getting instance of existing Module Manager, instead of creating a new object of the Modules Manager class.
    * IF the Modules Manager object has been previously created ==> create instance of it.
    * ELSE create new object of Modules Manager
    * This method represents part of the SINGLETON Design Pattern and its core to its proper functionality.
    * @return ModulesManager
    */
    public static ModulesManager getInstance()
    {
        if(theManager == null)
        {
            theManager = new ModulesManager();
        }
        return theManager;
    }

    public String getDescription() 
    {
        return description;
    }

    /**
     * Sets description for a new ModulesManager
     * @param description String representation of the actual Modules Manager description to be set
     */
    public void setDescription(String description) 
    {
        if(description == null | description == "")
        {
            this.description = "<no description set>";
        }
        else
        {
            this.description = description;
        }
    }
    
    public void addModule(String newLevel, String newName, String newSemester, int newCredits)
    {
        Module tempModule = new Module(newLevel, newName, newSemester, newCredits);
        listUserModules.add(tempModule);
    }
    
    public void removeModule(String name)
    {   
        try
        {
            for(Module temp : listUserModules)
            {
                if(temp.getName().equals(name))
                {
                    listUserModules.remove(temp);
                    break;
                }
            }
        }
        catch(ConcurrentModificationException e)
        {
            System.out.println(e);
        }  
    }
    
    public Module getModule(String name)
    {
        Module moduleToReturn = null;
        for(Module temp : listUserModules)
        {
            if(temp.getName().equals(name))
            {
                moduleToReturn = temp;
            }
        }
        return moduleToReturn;
    }
    
    public void removeAllModulesList()
    {
        listUserModules.removeAll(listUserModules);
    }
    
    public void removeAssignment(String name)
    {
        for(Module tempModule : listUserModules)
        {
            tempModule.removeAssignment(name);
        }
    }
    
    public ArrayList<Module> getAllModules()
    {
        ArrayList<Module> modulesList = new ArrayList<Module>();
        for(Module temp : listUserModules)
        {
            modulesList.add(temp);
        }
        return modulesList;
    }
        
    public String getAllModulesString()
    {
        String s = "";
        for(Module temp : listUserModules)
        {
            s = s + temp.toString() + "\n";
        }
        return s;
    }
    
    /**
     * Checks if an input module exists in listUserModules list. Returns false if not.
     * @param moduleName
     * @return boolean representation of module existence
     */
    public boolean checkModuleExists(String moduleName)
    {
        boolean result = false;
        for(Module tempModule : listUserModules)
        {
            if((tempModule.getName().toLowerCase()).equals(moduleName.toLowerCase()))
            {
                result = true;
                break;
            }
        }        
        return result; 
    }
}
package com.opencms.template;

import java.util.*;
import java.lang.reflect.*;
import com.opencms.file.*;
import com.opencms.core.*;

/**
 * Class for managing of the instances of all
 * OpenCms template classes.
 * <P>
 * This class provides methods for getting the instance of a
 * class. Once a instance of a template class is build, it is 
 * be cached and re-used. 
 * 
 * @author Alexander Lucas
 * @version $Revision: 1.3 $ $Date: 2000/01/21 10:35:18 $
 */
public class CmsTemplateClassManager implements I_CmsLogChannels { 
    
    /**
     * Hashtable for caching the template class
     */
    private static Hashtable instanceCache = new Hashtable();

    /**
     * Gets the instance of the class with the given classname.
     * If no instance exists a new one will be created using the default constructor.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param classname Name of the requested class.
     * @return Instance of the class with the given name.
     * @exception CmsException 
     */
    public static Object getClassInstance(A_CmsObject cms, String classname)
            throws CmsException {
        
        return getClassInstance(cms, classname, null);
    }
    
    /**
     * Gets the instance of the class with the given classname.
     * If no instance exists a new one will be created using the given arguments.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param classname Name of the requested class.
     * @param callParameters Array of arguments that should be passed to the Constructor.
     * @return Instance of the class with the given name.
     * @exception CmsException 
     */
    public static Object getClassInstance(A_CmsObject cms, String classname, Object[] callParameters)
            throws CmsException {
        
        int numParams = 0;
        if(callParameters != null) {
            numParams = callParameters.length;
        }

        Class[] parameterTypes = new Class[numParams];
        for(int i=0; i<numParams; i++) {
            parameterTypes[i] = callParameters[i].getClass();
        }
                
        return getClassInstance(cms, classname, callParameters, parameterTypes);        
    }

    /**
     * Gets the instance of the class with the given classname.
     * If no instance exists a new one will be created using the given arguments
     * interpreted as objects of the given classes.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param classname Name of the requested class.
     * @param callParameters Array of arguments that should be passed to the Constructor.
     * @param parameterTypes Array of the types of the arguments.
     * @return Instance of the class with the given name.
     * @exception CmsException 
     */
    public static Object getClassInstance(A_CmsObject cms, String classname, Object[] callParameters, Class[] parameterTypes)
            throws CmsException {
        
        Object o = null;
        if(callParameters == null) {
            callParameters = new Object[0];
        }
        
        if(parameterTypes == null) {
            parameterTypes = new Class[0];
        }
        
        if(instanceCache.containsKey(classname)) {
            o = instanceCache.get(classname);
        } else {
            Vector repositories = new Vector();
            //repositories.addElement("/system/servlets/");
            repositories.addElement("/");

            try {
                CmsClassLoader loader = new CmsClassLoader(cms, repositories, null);
                Class c = loader.loadClass(classname);        
            
                // Now we have to look for the constructor
                Constructor con = c.getConstructor(parameterTypes); 
                o = con.newInstance(callParameters);
            } catch(Exception e) {
                String errorMessage = null;
                
                // Construct error message for the different exceptions
                if(e instanceof ClassNotFoundException) {                    
                    errorMessage = "Could not load template class " + classname;
                } else if(e instanceof InstantiationException) {
                    errorMessage = "Could not instantiate template class " + classname;
                } else if(e instanceof NoSuchMethodException) {
                    errorMessage = "Could not find constructor of template class " + classname;
                } else {
                    errorMessage = "Unknown error while getting instance of template class " + classname;
                }

                if(A_OpenCms.isLogging()) {
                    A_OpenCms.log(C_OPENCMS_CRITICAL, "[CmsTemplateClassManager] " + errorMessage);
                }
                throw new CmsException(errorMessage, CmsException.C_CLASSLOADER_ERROR);
            }
                                                
            instanceCache.put(classname, o);
        }
        
        return o;        
    }
    
    /**
     * Clears the cache for template class instances.
     */
      public static void clearCache() {
        if(A_OpenCms.isLogging()) {
            A_OpenCms.log(C_OPENCMS_INFO, "[CmsClassManager] clearing class instance cache.");
        }
        instanceCache.clear();
    }
}

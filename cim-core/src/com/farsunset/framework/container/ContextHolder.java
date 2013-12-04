package com.farsunset.framework.container;

import org.springframework.context.ApplicationContext;

public class ContextHolder  {

    private static  ApplicationContext context;
    
    
    
    public static void setContext(ApplicationContext ac)
    {
    	
    	context =ac;
    }
    
    
    public static  Object getBean(String name)
    {
    	
    	return context.getBean(name);
    }
    
    public static  <T> T getBean(Class<T> c)
    {
    	
    	return context.getBean(c);
    }
}
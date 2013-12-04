package com.farsunset.framework.web.listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;

import com.farsunset.framework.container.ContextHolder;

public class ContainerBeanLoaderListener  implements ServletContextListener{

	ContextLoader contextLoader;
    protected final Logger logger = Logger.getLogger(ContainerBeanLoaderListener.class); 
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		 if(contextLoader != null)
	            contextLoader.closeWebApplicationContext(event.getServletContext());
		 ContextHolder.setContext( null);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		 
		if(contextLoader == null){
			contextLoader = new ContextLoader();
		}
        
		logger.debug("******************* container start begin ******************************");
		ContextHolder.setContext(contextLoader.initWebApplicationContext(event.getServletContext()));
		logger.debug("******************* container start successfull ************************");
		
	}
	   
		
}

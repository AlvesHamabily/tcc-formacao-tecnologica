package com.financialeducation.controller;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class Test
 *
 */
@WebListener
public class Test implements ServletContextListener, ServletContextAttributeListener {

    public void attributeReplaced(ServletContextAttributeEvent event)  { 
    }

    public void attributeRemoved(ServletContextAttributeEvent event)  { 
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	System.out.println(sce.getServletContext().getServletContextName());
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    public void attributeAdded(ServletContextAttributeEvent event)  { 
         // TODO Auto-generated method stub
    }
	
}

package org.zxc.service.listener;

import org.zxc.service.module.ServiceModule;
//import org.zxc.service.module.ResoureModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * Application Lifecycle Listener implementation class GuiceModuleListener
 * 
 */
public class GuiceModuleListener extends GuiceServletContextListener {

    public static Injector injector;

    @Override
    protected Injector getInjector() {
	injector = Guice.createInjector(new ServletModule(),
//					new ResoureModule(),
					new ServiceModule()); 
	return injector;
    }
    
    public static Injector getInjectorInstance(){
    	return injector;
    }

}

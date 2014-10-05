package org.zxc.service.application;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
import org.zxc.service.listener.GuiceModuleListener;

/**
 * @author zhengxc
 * see https://github.com/piersy/jersey2-guice-example-with-test.git
 * 2013-10-17
 */
public class ServiceApplication extends ResourceConfig {

	@Inject
	public ServiceApplication(ServiceLocator serviceLocator) {
		// Set package to look for resources in
		packages("org.zxc.service.resource");

		GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);

		GuiceIntoHK2Bridge guiceBridge = serviceLocator
				.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(GuiceModuleListener.injector);

		register(MyObjectMapperProvider.class).register(JacksonFeature.class);
	}
}

package org.spring.test.v6;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.spring.context.ApplicationContext;
import org.spring.context.support.ClassPathXmlApplicationContext;
import org.spring.service.v6.IUserService;
import org.spring.util.MessageTracker;

import java.util.List;

public class ApplicationContextTest6 {
	
	
	
	@Test
	public void testGetBeanProperty() {
		
		ApplicationContext ctx = new ClassPathXmlApplicationContext("bean-v6.xml");
		IUserService petStore = (IUserService) ctx.getBean("userService");
	
		petStore.placeOrder();
		
		List<String> msgs = MessageTracker.getMsgs();
		
		Assert.assertEquals(3, msgs.size());
		Assert.assertEquals("start tx", msgs.get(0));	
		Assert.assertEquals("place order", msgs.get(1));	
		Assert.assertEquals("commit tx", msgs.get(2));	
		
	}

	@Before
	public void setUp(){
		MessageTracker.clearMsgs();
	}
	
	
}

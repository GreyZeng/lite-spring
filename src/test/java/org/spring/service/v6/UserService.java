package org.spring.service.v6;

import org.spring.stereotype.Component;
import org.spring.util.MessageTracker;

/**
 * @author zenghui
 * 2020/8/9
 */
@Component(value="userService")
public class UserService implements IUserService {

    public UserService() {

    }

    @Override
    public void placeOrder(){
        System.out.println("place order");
        MessageTracker.addMsg("place order");
    }



}

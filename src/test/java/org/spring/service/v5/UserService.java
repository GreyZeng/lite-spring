package org.spring.service.v5;


import org.spring.beans.factory.annotation.Autowired;
import org.spring.dao.v5.AccountDao;
import org.spring.dao.v5.ItemDao;
import org.spring.stereotype.Component;
import org.spring.util.MessageTracker;

/**
 * @author zenghui
 * @date 2020/7/27
 */
@Component(value = "userService")
public class UserService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao itemDao;

    public UserService() {
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void placeOrder(){
        System.out.println("place order");
        MessageTracker.addMsg("place order");

    }
    public void placeOrderWithException(){
        throw new NullPointerException();
    }
}

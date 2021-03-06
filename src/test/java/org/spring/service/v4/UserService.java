package org.spring.service.v4;


import org.spring.beans.factory.annotation.Autowired;
import org.spring.dao.v4.AccountDao;
import org.spring.dao.v4.ItemDao;
import org.spring.stereotype.Component;

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

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }


}

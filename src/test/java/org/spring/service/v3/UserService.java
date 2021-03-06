package org.spring.service.v3;

import org.spring.dao.v3.AccountDao;
import org.spring.dao.v3.ItemDao;

/**
 * @author zenghui
 * @Date 2020/7/25
 */
public class UserService {
    private AccountDao accountDao;
    private ItemDao itemDao;
    private int version;

    public UserService(AccountDao accountDao, ItemDao itemDao, int version) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }

    public UserService(AccountDao accountDao, ItemDao itemDao) {
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = -1;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public int getVersion() {
        return version;
    }
}

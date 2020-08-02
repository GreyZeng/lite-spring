package org.spring.service.v2;

import org.spring.dao.v2.AccountDao;
import org.spring.dao.v2.ItemDao;

/**
 * @author zenghui
 * 2020/8/2
 */
public class UserService {
    private AccountDao accountDao;
    private ItemDao itemDao;
    private String owner;
    private int version;
    // 可以支持on/off, true/false, 1/0
    private boolean checked;


    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public void setItemDao(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}

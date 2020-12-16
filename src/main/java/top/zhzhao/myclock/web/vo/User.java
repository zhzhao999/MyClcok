/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.web.vo;

/**
 *
 *@author zhzhao
 *@version $ Id: User.java,V 0.1 2020/1/9 15:07 zhzhao Exp $
 */
public class User {
    private String name;
    private String loginName;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}

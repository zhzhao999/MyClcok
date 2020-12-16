/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.web.vo;

/**
 *
 *@author zhzhao
 *@version $ Id: HxUser.java,V 0.1 2020/12/16 15:07 zhzhao Exp $
 */
public class HxUser {

    private String name;
    private String account;
    private String userId;
    private String rxSig;
    private String rxUid;
    private String phpSessId;

    public HxUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRxSig() {
        return rxSig;
    }

    public void setRxSig(String rxSig) {
        this.rxSig = rxSig;
    }

    public String getRxUid() {
        return rxUid;
    }

    public void setRxUid(String rxUid) {
        this.rxUid = rxUid;
    }

    public String getPhpSessId() {
        return phpSessId;
    }

    public void setPhpSessId(String phpSessId) {
        this.phpSessId = phpSessId;
    }
}

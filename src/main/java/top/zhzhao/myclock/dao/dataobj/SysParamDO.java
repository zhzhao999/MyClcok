package top.zhzhao.myclock.dao.dataobj;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import top.zhzhao.myclock.dao.dataobj.BaseDO;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统配置表
 *@author zhzhao
 *@version $ Id: UserDO.java,V 0.1 2019/6/13 10:28 zhzhao Exp $
 */
@Entity
@Table(name = "sys_param")
@EntityListeners(AuditingEntityListener.class)
public class SysParamDO extends BaseDO {

    private static final long serialVersionUID = -6469431429436870699L;
    // ------表字段------
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;


    //-------GETTER & SETTER-------

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

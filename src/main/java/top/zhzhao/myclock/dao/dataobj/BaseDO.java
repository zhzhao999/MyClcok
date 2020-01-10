/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.dao.dataobj;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 *
 *@author zhzhao
 *@version $ Id: BaseDO.java,V 0.1 2019/6/13 10:15 zhzhao Exp $
 */
public class BaseDO implements Serializable {

    private static final long serialVersionUID = 2281456180859743287L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
    }
}


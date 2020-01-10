/*
 * https://www.zhzhao.top
 */
package top.zhzhao.myclock.dao;

import org.springframework.stereotype.Repository;
import top.zhzhao.myclock.dao.dataobj.SysParamDO;

/**
 *
 *@author zhzhao
 *@version $ Id: UserDAO.java,V 0.1 2019/6/13 11:26 zhzhao Exp $
 */
@Repository
public interface SysParamDAO extends BaseDAO<SysParamDO,String> {
    SysParamDO findOneByCode(String code);
}

package top.zhzhao.myclock.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 *
 *@author zhzhao
 *@version $ Id: BaseRepository.java,V 0.1 2019/6/13 9:29 zhzhao Exp $
 */
@NoRepositoryBean
interface BaseDAO<T, ID extends Serializable> extends JpaRepository<T, ID>,
                                                      JpaSpecificationExecutor<T> {
}

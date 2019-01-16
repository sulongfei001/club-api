package com.sevenXnetworks.treasure.dao.impl;

import com.sevenXnetworks.treasure.dao.GlobalDictionaryDao;
import com.sevenXnetworks.treasure.entity.GlobalDictionaryEntity;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GlobalDictionaryDaoImpl extends BaseDaoImpl<GlobalDictionaryEntity, Long> implements GlobalDictionaryDao {

    /**
     * 按key查询字典信息列表
     *
     * @param key
     */
    public GlobalDictionaryEntity findByKey(String key) {
        String hql = "from GlobalDictionaryEntity where key=:key";
        Query query = currentSession().createQuery(hql);
        query.setString("key", key);
        return (GlobalDictionaryEntity) query.uniqueResult();
    }
}

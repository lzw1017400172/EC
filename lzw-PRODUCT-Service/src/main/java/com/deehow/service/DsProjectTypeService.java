package com.deehow.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.deehow.core.Constants;
import com.deehow.core.base.BaseService;
import com.deehow.core.util.CacheUtil;
import com.deehow.core.util.ExceptionUtil;
import com.deehow.core.util.InstanceUtil;
import com.deehow.mapper.DsProjectTypeMapper;
import com.deehow.model.DsProjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * cms-協同设计项目类型 服务实现类
 * </p>
 *
 * @author liuzw
 * @since 2018-12-20
 */
@Service
public class DsProjectTypeService extends BaseService<DsProjectType> {


    @Autowired
    private DsProjectTypeMapper dsProjectTypeMapper;


    public int getProjectTypeSizeByName(String name){
        DsProjectType paramType = new DsProjectType();
        paramType.setName(name);
        Wrapper<DsProjectType> wrapper = new EntityWrapper<>(paramType);
        return dsProjectTypeMapper.selectCount(wrapper);
    }

    @Transactional
    public Map<String,Object> verifiedUpdate(DsProjectType record) {
        String lockKey = getLockKey("U_VERIFIEDUPDATE_LOCK");
        if (CacheUtil.getLock(lockKey)) {
            try {
                if(record.getId() == null){
                    int size = getProjectTypeSizeByName(record.getName());
                    if(size > 0){
                        Map<String,Object> returnMap = InstanceUtil.newHashMap("state",2);
                        returnMap.put("msg","分类["+record.getName()+"]已经存在。");
                        return returnMap;
                    }
                } else {
                    DsProjectType model = queryById(record.getId());
                    if(model.getName().equals(record.getName())){
                        return InstanceUtil.newHashMap("state",1);
                    }

                    int size = getProjectTypeSizeByName(record.getName());
                    if(size > 0){
                        Map<String,Object> returnMap = InstanceUtil.newHashMap("state",2);
                        returnMap.put("msg","分类["+record.getName()+"]已经存在。");
                        return returnMap;
                    }
                }
                update(record);
                return InstanceUtil.newHashMap("state",1);
            } finally {
                CacheUtil.unlock(lockKey);
            }
        } else {
            sleep(20);
            return verifiedUpdate(record);
        }
    }
}

package com.deehow.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.deehow.mapper.DsTaskModelMapper;
import com.deehow.model.DsModelLibrary;
import com.deehow.model.DsTaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * cms-協同设计任务-模型--关联 服务实现类
 * </p>
 *
 * @author liuzw
 * @since 2018-12-27
 */
@Service
public class DsTaskModelService {

    @Autowired
    private DsTaskModelMapper dsTaskModelMapper;

    @Autowired
    private DsModelLibraryService dsModelLibraryService;

    public void insertAll(List<DsTaskModel> dsTaskModels){
        if(dsTaskModels != null && dsTaskModels.size() > 0){
            dsTaskModelMapper.insertAll(dsTaskModels);
        }
    }

    public void delete(DsTaskModel dsTaskModel){
        if(dsTaskModel != null){
            Wrapper<DsTaskModel> wrapper = new EntityWrapper<>(dsTaskModel);
            dsTaskModelMapper.delete(wrapper);
        }
    }

    public List<DsModelLibrary> selectModelsByTask(Map<String,Object> map){
        if(map.get("taskId") == null){
            return new ArrayList<>();
        }
        List<Long> modelIds = dsTaskModelMapper.selectModelIdPage(map);
        return dsModelLibraryService.getList(modelIds);
    }
}

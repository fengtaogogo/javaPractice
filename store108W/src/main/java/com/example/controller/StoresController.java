package com.example.controller;

import com.example.domain.IDUtil;
import com.example.mapper.PersonMapper;
import com.example.service.impl.HandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by FlySheep on 17/3/25.
 */
@RestController
public class StoresController {

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private ExecutorService executorService;

    @RequestMapping("/108wstores")
    public void insertStores() {

        //终端类型
        List<Map<String,Object>> storetype=personMapper.selectstoretype();
        //        终端级别
        List<Map<String,Object>> storelevel=personMapper.selectstorelevel();
        //        所有的业务员
        List<Map<String,Object>> Yewuyuans=personMapper.selectYewuyuans();
        //        所有的启用品牌
        List<Map<String,Object>> brands=personMapper.selectbrands();
        //        所有的启用品类
        List<Map<String,Object>> categories=personMapper.selectcategories();
        //        行政区域
        int [] regionid = {440103,440104,440105,440106,440111,440112,440113,440114};
//生成18位不重复数字
        IDUtil idWorker = new IDUtil(0, 0);

        for (int i = 0; i < 10; i++) {
//            Thread t = new Thread(new HandlerService(i,2,Yewuyuans,storetype,storelevel,regionid));
//            t.start();
            executorService.execute(new HandlerService(i,270,Yewuyuans,storetype,storelevel,
                    regionid,personMapper,idWorker,brands,categories));
        }
    }
}

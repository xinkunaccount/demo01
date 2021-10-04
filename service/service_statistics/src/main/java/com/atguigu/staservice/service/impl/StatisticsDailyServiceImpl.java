package com.atguigu.staservice.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.staservice.client.UcenterClient;
import com.atguigu.staservice.entity.StatisticsDaily;
import com.atguigu.staservice.mapper.StatisticsDailyMapper;
import com.atguigu.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-03
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
        @Autowired
        private UcenterClient ucenterClient;
    @Override
    public void countRegister(String day) {
        //每次添加数据之前先删除已有的数据

        QueryWrapper<StatisticsDaily>wrapper=new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);



        R registerCount = this.ucenterClient.getRegisterCount(day);
      Integer count=  (Integer) registerCount.getData().get("count");
      StatisticsDaily statisticsDaily=new StatisticsDaily();
      statisticsDaily.setRegisterNum(count);
      statisticsDaily.setDateCalculated(day);
      statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
      statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
      statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
      this.baseMapper.insert(statisticsDaily);

    }

    //根据条件查询数据
    @Override
    public Map<String, Object> getData(String type, String begin, String end) {

        QueryWrapper<StatisticsDaily>  wrapper=new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> staList = this.baseMapper.selectList(wrapper);
        List<String> dateList=new ArrayList<>();
        List<Integer> numList=new ArrayList<>();
        for (int i = 0; i < staList.size(); i++) {
            StatisticsDaily sta = staList.get(i);
            dateList.add(sta.getDateCalculated());

            switch (type){
                case "login_num":
                    numList.add(sta.getLoginNum());
                    break;
                case "register_num":
                    numList.add(sta.getRegisterNum());
                    break;
                case "course_num":
                    numList.add(sta.getCourseNum());
                    break;
               default:
                   numList.add(sta.getVideoViewNum());
                    break;

            }


        }


        Map<String,Object> map=new HashMap<>();
        map.put("date_calculatedList",dateList);
        map.put("numDataList",numList);


        return map;
    }
}

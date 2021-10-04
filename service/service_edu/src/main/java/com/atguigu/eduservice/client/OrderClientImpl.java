package com.atguigu.eduservice.client;

import com.atguigu.servicebase.exceptionhandler.GuliException;
import net.sf.jsqlparser.statement.execute.Execute;
import org.springframework.stereotype.Component;

@Component
public class OrderClientImpl implements OrderClient {
    @Override
    public boolean getByOrder(String courseId, String orderId) {
        throw new GuliException(20001,"获取order状态时异常");
    }
}

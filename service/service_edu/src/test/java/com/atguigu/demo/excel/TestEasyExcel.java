package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //String fileName="D:\\test.xlsx";
        //EasyExcel.write(fileName,DataDemo.class).sheet("学生信息表").doWrite(getData());
        String fileName="D:\\test.xlsx";
        EasyExcel.read(fileName,DataDemo.class,new ExcelListener()).sheet().doRead();

    }


    public static List<DataDemo> getData(){
        List<DataDemo> list=new ArrayList<>();
        for (int i=0;i<10;i++){
            DataDemo dataDemo=new DataDemo();
            dataDemo.setNum(i);
            dataDemo.setName("zhangsan"+i);
            list.add(dataDemo);
        }

        return  list;
    }
}

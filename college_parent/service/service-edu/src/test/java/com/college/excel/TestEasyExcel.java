package com.college.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        String filename = "D:\\postgraduate\\colloge\\01.xlsx";
        EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(getDate());
    }

    private static List<DemoData> getDate() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setName("jack" + i);
            list.add(data);
        }
        return list;
    }

    @Test
    public void readTest() {
        String filename = "D:\\postgraduate\\colloge\\01.xlsx";
        EasyExcel.read(filename, ReadData.class, new ExcelListener()).sheet("学生列表").doRead();
    }
}

package com.college.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ReadData {
    @ExcelProperty(index = 0)
    private Integer sno;

    @ExcelProperty(index = 1)
    private String name;
}

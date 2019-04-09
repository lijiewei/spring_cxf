package com.ljw.spring_cxf.service.impl;

import com.ljw.spring_cxf.service.ICalculateService;

/**
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2019/4/9 23:29
 */
public class CalculateServiceImpl implements ICalculateService {

    @Override
    public int add(int num1, int num2) {
        return num1 + num2;
    }
}

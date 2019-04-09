package com.ljw.spring_cxf.service;

import javax.jws.WebService;

/**
 * @Description: 计算器接口
 * @Author: Administrator
 * @CreateDate: 2019/4/9 23:28
 */
@WebService(serviceName = "calculateService")
public interface ICalculateService {

    int add(int num1, int num2);

}

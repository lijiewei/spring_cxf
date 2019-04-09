package com.ljw.spring_cxf.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * @Description: 自定义的许可拦截器
 * @Author: Administrator
 * @CreateDate: 2019/4/9 21:55
 */
public class AuthInfoInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private String username;
    private String password;

    public AuthInfoInterceptor() {
        super(Phase.PRE_INVOKE);
    }

    /**
     * 添加头信息
     * <authInfo>
     *     <username>admin</username>
     *     <password>123</password>
     * <authInfo/>
     * @return
     */
    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {

        //1.获取头信息
        List<Header> headers = soapMessage.getHeaders();
        //2.判断头信息是否存在
        if(CollectionUtils.isEmpty(headers)){
            throw new Fault(new IllegalArgumentException("头信息不能为空"));
        }
        //3.从头信息中获取认证信息
        Header header = headers.get(0);
        Element authInfoEl = (Element) header.getObject();
        Element usernameEl = (Element) authInfoEl.getElementsByTagName("username").item(0);
        String username = usernameEl.getTextContent();
        Element passwordEl = (Element) authInfoEl.getElementsByTagName("password").item(0);
        String password = passwordEl.getTextContent();
        //4.验证是否存在
       if(!"zhangsan".equals(username) || !"123".equals(password)){
           throw new Fault(new IllegalArgumentException("用户名或者密码错误"));
       }



    }
}

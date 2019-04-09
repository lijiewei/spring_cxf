package com.ljw.spring_cxf.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;

/**
 * @Description: 自定义的许可拦截器
 * @Author: Administrator
 * @CreateDate: 2019/4/9 21:55
 */
public class AuthInfoInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private String username;
    private String password;

    public AuthInfoInterceptor(String username, String password) {
        super(Phase.PREPARE_SEND);

        this.username = username;
        this.password = password;
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

        Document document = DOMUtils.createDocument();

        //1.创建三个元素
        Element authInfoEl = document.createElement("authInfo");
        Element usernameEl = document.createElement("username");
        Element passwordEl = document.createElement("password");
        //2.把用户名和密码添加到username和password
        usernameEl.setTextContent(username);
        passwordEl.setTextContent(password);
        //3.确定关系
        authInfoEl.appendChild(usernameEl);
        authInfoEl.appendChild(passwordEl);
        //4.把authInfo添加到header部分
        QName qName = new QName("authinfo");
        Header header = new Header(qName,authInfoEl);
        soapMessage.getHeaders().add(header);

    }
}

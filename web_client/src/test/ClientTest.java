import com.ljw.spring_cxf.interceptor.AuthInfoInterceptor;
import com.ljw.spring_cxf.service.ICalculateService;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Description:
 * @Author: Administrator
 * @CreateDate: 2019/4/10 0:20
 */
public class ClientTest {


    @Test
    public void CXFXMLTest() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        ICalculateService calculateService = applicationContext.getBean("demoClient", ICalculateService.class);
        System.out.println(calculateService.add(1, 4));
    }

    @Test
    public void JAVATest() {
        //加不上拦截器
        URL wsdlURL = null;
        try {
            wsdlURL = new URL("http://localhost:8080/spring_cxf/ws/calculate?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        QName SERVICE_NAME = new QName("http://service.spring_cxf.ljw.com/", "calculateService");

        Service service = Service.create(wsdlURL, SERVICE_NAME);
        ICalculateService calculateService = service.getPort(ICalculateService.class);
        System.out.println(calculateService.add(2,7));
    }

    @Test
    public void JAVATest2() {
        //1.创建JaxWsProxyFactoryBean的对象，用于接收服务
        JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
//        2.设置服务的发布地址，表示去哪里过去服务
        jaxWsProxyFactoryBean.setAddress("http://localhost:8080/spring_cxf/ws/calculate");
//        3.设置服务的发布接口，使用本地的代理接口
        jaxWsProxyFactoryBean.setServiceClass(ICalculateService.class);

        //===================拦截器======================
        //官方提供日志拦截器
        jaxWsProxyFactoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        //自定义的许可拦截器
        jaxWsProxyFactoryBean.getOutInterceptors().add(new AuthInfoInterceptor("zhangsan" ,"123"));
        //===================拦截器======================

//        4.通过create方法返回接口代理实例
        ICalculateService calculateService = (ICalculateService) jaxWsProxyFactoryBean.create();
//        5.调用远程方法
        System.out.println(calculateService.add(2, 4));
    }
}

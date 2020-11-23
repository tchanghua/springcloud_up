package test;

import com.lwl.study.LagouServiceEmailApplication8082;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {LagouServiceEmailApplication8082.class})
public class EmailTest {
    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean


    @Test
    public void testSendEmail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cfwspringcloud@163.com");
        message.setTo("173239748@qq.com"); //自己给自己发送邮件
        message.setSubject("注册验证码");
        message.setText("code");
        mailSender.send(message);
    }

}

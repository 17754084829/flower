package common.mail.observer;

import common.mail.mailHelp.MailHelper;
import common.mail.msg.Msg;
import common.model.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class EmailObserver extends Observer{
    public EmailObserver(String name,String route){
        this.name=name;
        this.route=route;
    }
    public EmailObserver(){}
    @Override
    public void deal(Msg msg) {
        try {
            Mail mail = (Mail) msg.getMsg();
            MailHelper.sendMail(mail);
            log.info(this.name+":正在处理任务   路由为:"+this.route+"  邮箱接受者地址为:"+mail.getRec_user());
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}

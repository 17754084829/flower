package web.filter;

import com.alibaba.fastjson.JSON;
import common.mail.observer.EmailObserver;
import common.util.DataVerify;
import common.util.FilterPermission;
import common.util.JobHelper;
import common.util.URL;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;

@Slf4j
public class PermissionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            Class.forName("common.mail.observered.Observered");
            JobHelper.subscription(new EmailObserver("邮件处理","email"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        String path=request.getRequestURI();
        String code=(String)request.getSession().getAttribute("verify");
        if(!DataVerify.stringIsNotEmpty(code)){
           if(FilterPermission.hasPermission(path,FilterPermission.getPermissionList(URL.defaultAllowUrl))){
               filterChain.doFilter(servletRequest,servletResponse);
               return;
           }else {
               request.getRequestDispatcher("/resource/302.html").forward(request,response);
               log.info("非法访问: "+path);
               return;
           }
        }
        if(!FilterPermission.hasPermission(path,FilterPermission.getPermissionList(code))){
            request.getRequestDispatcher("/resource/302.html").forward(request,response);
            log.info("授权失败: "+path);
            return;
        }else{
            filterChain.doFilter(servletRequest,servletResponse);
            log.info("授权通过 :"+path);
        }

    }
}

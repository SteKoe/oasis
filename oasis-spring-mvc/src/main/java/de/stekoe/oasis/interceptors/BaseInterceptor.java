package de.stekoe.oasis.interceptors;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        String controller = handlerMethod.getBean().getClass().getSimpleName();
        String action = handlerMethod.getMethod().getName();
        modelAndView.addObject("controller", controller);
        modelAndView.addObject("action", action);
        modelAndView.addObject("actionPath", controller + "/" + action);
    }
}
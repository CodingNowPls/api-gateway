package com.cn.striverfeng.controller;

import com.cn.striverfeng.core.ApiGatewayHand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 说明： ${DESCRIPTION}
 *
 * @author StriverFeng
 * @create 2017-08-14 8:27
 **/
@Controller()
public class ApiHandController {

    @Autowired
    private ApiGatewayHand apiHand;

    @RequestMapping("/api")
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        apiHand.handle(request, response);
    }
}

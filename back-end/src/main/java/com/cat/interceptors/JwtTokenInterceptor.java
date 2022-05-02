package com.cat.interceptors;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cat.utils.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * date: 2021/12/29 10:17
 *
 * @author: 薛定谔的猫猫虫
 * @message:
 */
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        System.out.println(token);
        Map<String,Object> map = new HashMap<>();
        try {
            JWTUtils.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            map.put("code", 403);
            map.put("msg", "token已过期,请先登录");
        } catch (SignatureVerificationException e){
            map.put("state", false);
            map.put("msg", "签名错误!");
        } catch (AlgorithmMismatchException e){
            map.put("state", false);
            map.put("msg", "加密算法不匹配!");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",401);
            map.put("msg", "token异常,没有权限访问");
        }
        String json = JSON.toJSONString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}

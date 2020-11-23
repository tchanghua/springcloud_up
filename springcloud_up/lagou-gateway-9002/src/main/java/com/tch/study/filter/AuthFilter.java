package com.tch.study.filter;

import com.tch.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class AuthFilter implements GlobalFilter, Ordered {
    @Autowired
    private UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().toString();
        if(path.startsWith("/api/user/") || path.startsWith("/api/code/")){
            return chain.filter(exchange);
        }else{
            MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
            List<HttpCookie> tokens = cookies.get("token");
            if(tokens!=null && tokens.size()>0){
                String token = tokens.get(0).getValue();
                String email = userService.checkToken(token);
                if (email!=null){
                    return chain.filter(exchange);
                }
            }
            response.setStatusCode(HttpStatus.UNAUTHORIZED); // 状态码
            String data = "Authentication failed!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}

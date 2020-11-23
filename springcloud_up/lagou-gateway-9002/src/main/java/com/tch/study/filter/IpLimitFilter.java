package com.tch.study.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RefreshScope
public class IpLimitFilter implements GlobalFilter, Ordered ,Runnable{

    @Value("${ip.limit.minutes}")
    private int minutes = 3;
    @Value("${ip.limit.maxTimes}")
    private int maxTimes = 5;

    private ConcurrentHashMap<Long, ConcurrentHashMap<String,Integer>> data = new ConcurrentHashMap<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getPath().toString();
        if(path.startsWith("/api/user/register")){
            // 从request对象中获取客户端ip
            String clientIp = request.getRemoteAddress().getHostString();
            if("0:0:0:0:0:0:0:1".equals(clientIp)){
                clientIp = "127.0.0.1";
            }
            int times = 0;
            //判断是否超过限制
            for(ConcurrentHashMap<String,Integer> minuteMap:data.values()){
                Integer minuteTimes = minuteMap.get(clientIp);
                if(minuteTimes!=null){
                    times = times+minuteTimes;
                }
            }
            if(times>=maxTimes){
                response.setStatusCode(HttpStatus.SEE_OTHER); // 状态码
                String data = "Frequent operation, request has been rejected!";
                DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
                return response.writeWith(Mono.just(wrap));
            }
            //记录访问记录
            setData(clientIp);
        }
        return chain.filter(exchange);
    }

    private void setData(String clientIp) {
        Date date = new Date();
//        获得分钟对应的Integer key;
        Long key = date.getTime()/1000/60;
        ConcurrentHashMap<String, Integer> minuteConcurrentHashMap = data.get(key);
        if(minuteConcurrentHashMap==null){
            minuteConcurrentHashMap = new ConcurrentHashMap<String,Integer>();
            data.put(key,minuteConcurrentHashMap);
        }
        Integer times = minuteConcurrentHashMap.get(clientIp);
        if(times==null){
            minuteConcurrentHashMap.put(clientIp,1);
        }else{
            minuteConcurrentHashMap.put(clientIp,times+1);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    //定期清除多余的记录
    @Override
    public void run() {
        Date now = new Date();
        Long deleteKey = now.getTime()/1000/60 - minutes;
        for(Long key:data.keySet()){
            if(key.longValue()<=deleteKey){
                data.remove(key);
            }
        }
    }

    @PostConstruct
    public void startSh(){
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(this,5,5, TimeUnit.SECONDS);
    }

}

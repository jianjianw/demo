package com.example.demo.interceptor;

import com.example.demo.DatabaseContextHolder;
import com.example.demo.DatabaseType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yangqin on 2017/8/26.
 */
@Aspect
@Configuration
//顺序不能设太高 dubbo的service会失效
@Order(-1000)
public class DatabaseInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseInterceptor.class);
	
	/*@Value("${rds.name.map}")
	private String rdsNameMapStr;*/
	private Map<String, String> rdsNameMap = new HashMap<String, String>();
	
	@Pointcut("execution(* com.example.demo.service.*.*(..))")
	public void rpc() {
	}
	
	/*public synchronized  void init(){
		String[] array = rdsNameMapStr.split(",");
		for(int i = 0;i < array.length;i++){
			String[] subArray = array[i].split(":");
			rdsNameMap.put(subArray[0], subArray[1]);
		}
	}*/
	
	
	@Before("rpc()")
	public void doBefore(JoinPoint jp){
		/*if(rdsNameMap.isEmpty()){
			init();
		}*/
		Object[] objects = jp.getArgs();
		if(objects.length == 0){
			throw new RuntimeException("systemBookCode not found");
		}
		int object = (int)objects[0];
		if( object == 1){
			DatabaseContextHolder.setDatabaseType(DatabaseType.main);
		}else{
			DatabaseContextHolder.setDatabaseType(DatabaseType.yuntu);
		}

	}

	
}

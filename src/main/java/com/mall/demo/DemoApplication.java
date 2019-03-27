package com.mall.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	public String home(){
		return "hello spring boot";
	}

	@RequestMapping("/hello")
	public String hello(){
		return "hello world";
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/roleAuth")
	public String role(){
		return "demo auth";
	}

	//对请求值的检查，id小于10
	@PreAuthorize("#id<10 or principal.username.equals(#userName) and user.username.equals('abc')")
	//对返回值的检查，2的倍数
	@PostAuthorize("returnObject%2==0")
	@RequestMapping("/test")
	public Integer test(Integer id,String userName ,User user){
		return id;
	}

	//过滤请求中不满足是2的倍数的数
	@PreFilter("filterObject%2==0")
	//过滤返回值中不满足是4的倍数的数
	@PostFilter("filterObject%4==0")
	@RequestMapping("/test2")
	public List<Integer> test2(List<Integer> idList){
		return idList;
	}

}

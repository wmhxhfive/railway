package com.railway.wm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebMvc

/*public class WmApplication extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(WmApplication.class, args);

	}
}*/
/**
 * 修改启动类，继承 SpringBootServletInitializer 并重写 configure 方法
 */
public class WmApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(WmApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(WmApplication.class, args);

	}
}

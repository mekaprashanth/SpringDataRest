package com.prash.sdr;

import javax.servlet.Filter;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.prash.sdr.config.LoggingAspect;
import com.prash.sdr.service.LocationServiceFactory;
import com.prash.sdr.service.ModelServiceFactory;
import com.prash.servlet.filter.HostURIFilter;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Main {
	
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
	@Bean
    public FilterRegistrationBean registerCorsFilter() {
		Filter filterOne = new HostURIFilter();
        FilterRegistrationBean reg = new FilterRegistrationBean(filterOne);
        reg.setOrder(4);
        return reg;
    }
	
	@Bean
	public FactoryBean<?> locationServiceLocatorFactoryBean() {
	    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
	    factoryBean.setServiceLocatorInterface(LocationServiceFactory.class);
	    return factoryBean;
	 }
	
	@Bean
	public FactoryBean<?> modelServiceLocatorFactoryBean() {
	    ServiceLocatorFactoryBean factoryBean = new ServiceLocatorFactoryBean();
	    factoryBean.setServiceLocatorInterface(ModelServiceFactory.class);
	    return factoryBean;
	 }
	
	@Bean
    public LoggingAspect logAspect() {
        return new LoggingAspect();
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.config;

import com.uclab.leanuxplatform.services.EegService;
import com.uclab.leanuxplatform.services.FeedbackService;
//import com.uclab.leanuxplatform.services.FaceDetectionService;
import com.uclab.leanuxplatform.services.LoginService;
import com.uclab.leanuxplatform.services.ProjectService;
import com.uclab.leanuxplatform.services.util.RequestResponseLoggingInterceptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Fahad Ahmed Satti
 */
@Configuration
//@ComponentScan("com.uclab.leanuxplatform")
//@EnableSpringConfigured
//@PropertySource({
//    "classpath:application.properties", //"file:${app.home}/settings.properties"
//})
public class LeanUXContextConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer pspc
                = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]{new ClassPathResource("application.properties")};
        pspc.setLocations(resources);
        pspc.setIgnoreUnresolvablePlaceholders(true);
       // System.out.println(pspc.getAppliedPropertySources());
        return pspc;
    }
    
    @Bean
    @DependsOn({"properties"})
    public ApplicationProperties applicationProperties(){
        return new ApplicationProperties();
    }

    //Remote access beans
    @Bean
    public RestTemplate restTemplate() {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory());
        
        ArrayList<HttpMessageConverter<?>> converters = new ArrayList<>(
        Arrays.asList(new MappingJackson2HttpMessageConverter(), new ResourceHttpMessageConverter(), new FormHttpMessageConverter() ));
        RestTemplate rt = new RestTemplate(factory);
        rt.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
        rt.getMessageConverters().addAll(converters);
        return rt;
    }

    @Bean
    public LoginService loginService() {
        return new LoginService();
    }

    @Bean
    @DependsOn({"restTemplate"})
    public ProjectService projectService() {
        return new ProjectService();
    }

    @Bean
    @DependsOn({"restTemplate"})
    @Scope(value = "prototype")
    public FeedbackService feedbackService() {
        return new FeedbackService();
    }

    @Bean
    public EegService eegService() {
        return new EegService();
    }

    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

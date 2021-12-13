/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uclab.leanuxplatform.config;

import com.uclab.leanuxplatform.controllers.screens.AddParticipantController;
import com.uclab.leanuxplatform.controllers.screens.MainController;
import com.uclab.leanuxplatform.controllers.screens.PreSurveyController;
import com.uclab.leanuxplatform.controllers.screens.ProjectDetailsController;
import com.uclab.leanuxplatform.controllers.screens.customcontrols.ModalityItemController;
import com.uclab.leanuxplatform.controllers.screens.customcontrols.ParticipantItemController;
import com.uclab.leanuxplatform.controllers.screens.customcontrols.ProjectItemController;
import com.uclab.leanuxplatform.controllers.screens.customcontrols.ToolbarController;
import com.uclab.leanuxplatform.controllers.screens.utils.DynamicSurvey;
import com.uclab.leanuxplatform.scope.ScreenScope;
import com.uclab.leanuxplatform.services.EegService;
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
@ComponentScan("com.uclab.leanuxplatform")
//@EnableSpringConfigured
//@PropertySource({
//    "classpath:application.properties", //"file:${app.home}/settings.properties"
//})
public class LeanUXContextConfig {


    @Bean
    public CustomScopeConfigurer getCustomScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        final Map<String, Object> scopeMap = new HashMap<>();
        scopeMap.put("screen", screenScope());
        configurer.setScopes(scopeMap);
        return configurer;
    }

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
    public ScreenScope screenScope() {
        return new ScreenScope();
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
    public EegService eegService() {
        return new EegService();
    }

    @Bean
    @Scope(value = "prototype")
    public ProjectItemController projectItemController() {
        return new ProjectItemController();
    }
    
    @Bean
    @Scope(value = "prototype")
    public MainController mainController() {
        return new MainController();
    }
    
    @Bean
    @Scope(value = "prototype")
    public ModalityItemController modalityItemController() {
        return new ModalityItemController();
    }

    @Bean
    @Scope(value = "prototype")
    public ParticipantItemController participantItemController() {
        return new ParticipantItemController();
    }

    @Bean
    @Scope(value = "prototype")
    public ProjectDetailsController projectDetailsController() {
        return new ProjectDetailsController();
    }


    @Bean
    @Scope(value = "prototype")
    public ToolbarController toolbarController() {
        return new ToolbarController();
    }
    
    @Bean
    public AddParticipantController addParticipantController() {
        return new AddParticipantController();
    }
    
    @Bean
    public PreSurveyController preSurveyController() {
        return new PreSurveyController();
    }
    
    @Bean
    @Scope(value = "prototype")
    public DynamicSurvey dynamicSurvey() {
        return new DynamicSurvey();
    }
    
    //To resolve ${} in @Value
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

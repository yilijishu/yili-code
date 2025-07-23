package com.yilijishu.code.config;

import com.yilijishu.code.bean.CodeGenerate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodeConfig {

    @Value("${com.yilijishu.code.generate.build.path}")
    private String buildPath;



    @Bean
    @ConditionalOnProperty(name = "com.yilijishu.code.generate.enabled", havingValue = "true")
    public CodeGenerate codeGenerate(ApplicationContext applicationContext){
        CodeGenerate codeGenerate = new CodeGenerate(applicationContext, buildPath);
        return codeGenerate;
    }

}

package com.winsafe.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.pagehelper.PageHelper;

@Configuration
public class PageHelperConf {
	@Value("${mybatis.pagehelper.dialect}")
	private String dialect;
	
	@Value("${mybatis.pagehelper.offsetAsPageNum}")
	private String offsetAsPageNum;
	
	@Value("${mybatis.pagehelper.rowBoundsWithCount}")
    private String rowBoundsWithCount;
	
	@Value("${mybatis.pagehelper.reasonable}")
    private String reasonable;
	
	@Value("${mybatis.pagehelper.autoRuntimeDialect}")
	private String autoRuntimeDialect;
	

	@Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", offsetAsPageNum);
        p.setProperty("rowBoundsWithCount", rowBoundsWithCount);
        p.setProperty("reasonable", reasonable);
        p.setProperty("autoRuntimeDialect", autoRuntimeDialect);
        pageHelper.setProperties(p);
        return pageHelper;
    }
}

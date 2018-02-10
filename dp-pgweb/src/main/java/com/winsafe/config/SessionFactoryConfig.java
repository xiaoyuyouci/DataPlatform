package com.winsafe.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@Configuration
@EnableTransactionManagement
@MapperScan("com.winsafe.dao")
public class SessionFactoryConfig {

    private static Logger logger = LogManager.getLogger(SessionFactoryConfig.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PageHelper pageHelper;
    
    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;

    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;

    /**
     *创建sqlSessionFactoryBean 实例
     * 并且设置configtion 如驼峰命名.等等
     * 设置mapper 映射路径
     * 设置datasource数据源
     * @return
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean createSqlSessionFactoryBean() {
        logger.info("createSqlSessionFactoryBean method");

        try{
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            Interceptor[] plugins =  new Interceptor[]{pageHelper};
            sqlSessionFactoryBean.setPlugins(plugins);//自定义的sqlSessionFactoryBean如果没配置插件的话，会导致插件无效
            sqlSessionFactoryBean.setDataSource(dataSource);            
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
            return sqlSessionFactoryBean;
        }
        catch(IOException ex){
            logger.error("Error happens when getting config files." + ExceptionUtils.getMessage(ex));
        }
        return null;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}

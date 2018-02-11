package com.winsafe.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.winsafe.datasource.DynamicDataSource;
import com.winsafe.datasource.DynamicDataSourceContextHolder;

@Configuration
@EnableTransactionManagement
public class DruidConfig implements EnvironmentAware {

    private List<String> customDataSourceNames = new ArrayList<>();

    private Logger logger = LogManager.getLogger(DruidConfig.class);

    private ConversionService conversionService = new DefaultConversionService();

    private Environment environment;

    public static final String DATA_SOURCE_PREfIX_CUSTOM="spring.custom.datasource.";

    public static final String DATA_SOURCE_CUSTOM_NAME="name";

    public static final String SEP = ",";
    public static final String DRUID_SOURCE_PREFIX = "spring.datasource.druid.";

    public static final String DATA_SOURCE_TYPE = "type";
    public static final String DATA_SOURCE_DRIVER = "driver-class-name";
    public static final String DATA_SOURCE_URL = "url";
    public static final String DATA_SOURCE_USER_NAME = "username";
    public static final String DATA_SOURCE_PASSWORD = "password";

    public static final String DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";


    public static final  String RESULT_CONSTANTS = "result";
    public static final  String SUCCESS_RESULT = "SUCCESS";
    public static final  String FAIL_RESULT = "FAIL";
    public static final  String MESSAGE_CONSTANTS = "message";

    public static final String STUDENTS_NODE_NAME = "students";
    public static final String STUDENT_NODE_NAME = "student";

    public static final String ID_ATTRIBUTE_NAME = "id";
    public static final String NAME_ATTRIBUTE_NAME = "name";
    public static final String CLASS_ATTRIBUTE_NAME = "className";
    public static final String CRAETE_DATE_ATTRIBUTE_NAME = "createDate";
    public static final String UPDATE_DATE_ATTRIBUTE_NAME = "updateDate";
    public static final String ENABLED_ATTRIBUTE_NAME = "enabled";
    public static final String DB_TYPE = "dbType";

    public static final String ID_JSON_PATH = "$.id";
    public static final String STUDENT_ID_JSON_PATH = "$.student.id";
    public static final String NAME_JSON_PATH = "$.name";
    public static final String STUDENT_NAME_JSON_PATH = "$.student.name";
    public static final String CLASS_JSON_PATH = "$.className";
    public static final String STUDENT_CLASS_JSON_PATH = "$.student.className";
    public static final String RESULT_JSON_PATH = "$.result";
    public static final String MESSAGE_JSON_PATH = "$.message";
    public static final String STUDENTS_JSON_PATH = "$.students";
    public static final String NEW_NAME_JSON_PATH = "$.newName";
    public static final String NEW_CLASS_JSON_PATH = "$.newClassName";

    public static final String STUDENT_NOT_EXIST  = "The student having id %d does not exist!";
    public static final String STUDENTS_NOT_EXIST = "No matched students";
    

    public static final String ADD_STUDENT_API_URL = "/api/addStudent";
    public static final String ADD_STUDENTS_API_URL = "/api/addStudents";
    public static final String DELETE_STUDENT_API_URL = "/api/deleteStudent";
    public static final String UPDATE_STUDENT_API_URL = "/api/updateStudent";
    public static final String GET_STUDENT_API_URL = "/api/getStudent";
    public static final String GET_STUDENTS_API_URL = "/api/getStudents";
    public static final String DELETE_STUDENTS_API_URL = "/api/deleteStudents";
    
    /**
     * @param environment the enviroment to set
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "dataSource")
    @Primary
    public AbstractRoutingDataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        LinkedHashMap<Object, Object> targetDatasources = new LinkedHashMap<>();
        initCustomDataSources(targetDatasources);
        dynamicDataSource.setDefaultTargetDataSource(targetDatasources.get(customDataSourceNames.get(0)));
        dynamicDataSource.setTargetDataSources(targetDatasources);
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }

    private void initCustomDataSources(LinkedHashMap<Object, Object> targetDataResources)
    {
        RelaxedPropertyResolver property = new RelaxedPropertyResolver(environment, DATA_SOURCE_PREfIX_CUSTOM);
        String dataSourceNames = property.getProperty(DATA_SOURCE_CUSTOM_NAME);
        if(StringUtils.isEmpty(dataSourceNames))
        {
            logger.error("The multiple data source list are empty.");
        }
        else{
              RelaxedPropertyResolver springDataSourceProperty = new RelaxedPropertyResolver(environment, "spring.datasource.");

              Map<String, Object> druidPropertiesMaps = springDataSourceProperty.getSubProperties("druid.");
              Map<String,Object> druidValuesMaps = new HashMap<>();
              for(String key:druidPropertiesMaps.keySet())
              {
                  String druidKey = DruidConstants.DRUID_SOURCE_PREFIX + key;
                  druidValuesMaps.put(druidKey,druidPropertiesMaps.get(key));
              }

              MutablePropertyValues dataSourcePropertyValue = new MutablePropertyValues(druidValuesMaps);

              for (String dataSourceName : dataSourceNames.split(SEP)) {
                try {
                    Map<String, Object> dsMaps = property.getSubProperties(dataSourceName+".");

                    for(String dsKey : dsMaps.keySet())
                    {
                        if(dsKey.equals("type"))
                        {
                            dataSourcePropertyValue.addPropertyValue("spring.datasource.type", dsMaps.get(dsKey));
                        }
                        else
                        {
                            String druidKey = DRUID_SOURCE_PREFIX + dsKey;
                            dataSourcePropertyValue.addPropertyValue(druidKey, dsMaps.get(dsKey));
                        }
                    }

                    DataSource ds = dataSourcebuild(dataSourcePropertyValue);
                    if(null != ds){
                        if(ds instanceof DruidDataSource)
                        {
                            DruidDataSource druidDataSource = (DruidDataSource)ds;
                            druidDataSource.setName(dataSourceName);
                            initDruidFilters(druidDataSource);
                        }

                        customDataSourceNames.add(dataSourceName);
                        DynamicDataSourceContextHolder.datasourceId.add(dataSourceName);
                        targetDataResources.put(dataSourceName,ds);

                    }
                    logger.info("Data source initialization 【"+dataSourceName+"】 successfully ...");
                } catch (Exception e) {
                    logger.error("Data source initialization【"+dataSourceName+"】 failed ...", e);
                }
            }
        }
    }


    /**
     * @Title: DataSourcebuild
     * @Description: 创建数据源
     * @param dataSourcePropertyValue 数据源创建所需参数
     *
     * @return DataSource 创建的数据源对象
     */
    public DataSource dataSourcebuild(MutablePropertyValues dataSourcePropertyValue)
    {
        DataSource ds = null;

        if(dataSourcePropertyValue.isEmpty()){
            return ds;
        }

        String type = dataSourcePropertyValue.get("spring.datasource.type").toString();

        if(StringUtils.isNotEmpty(type))
        {
            if(StringUtils.equals(type,DruidDataSource.class.getTypeName()))
            {
                ds = new DruidDataSource();

                RelaxedDataBinder dataBinder = new RelaxedDataBinder(ds, DRUID_SOURCE_PREFIX);
                dataBinder.setConversionService(conversionService);
                dataBinder.setIgnoreInvalidFields(false);
                dataBinder.setIgnoreNestedProperties(false);
                dataBinder.setIgnoreUnknownFields(true);
                dataBinder.bind(dataSourcePropertyValue);
            }
        }
        return ds;
    }

    @Bean
    public ServletRegistrationBean statViewServlet(){

        RelaxedPropertyResolver property = new RelaxedPropertyResolver(environment, "spring.datasource.druid.");

        Map<String, Object> druidPropertiesMaps = property.getSubProperties("stat-view-servlet.");


        boolean statViewServletEnabled = false;
        String statViewServletEnabledKey = ENABLED_ATTRIBUTE_NAME;
        ServletRegistrationBean registrationBean = null;

        if(druidPropertiesMaps.containsKey(statViewServletEnabledKey))
        {
            String statViewServletEnabledValue = druidPropertiesMaps.get(statViewServletEnabledKey).toString();
            statViewServletEnabled = Boolean.parseBoolean(statViewServletEnabledValue);
        }
        if(statViewServletEnabled){
            registrationBean = new ServletRegistrationBean();
            StatViewServlet statViewServlet = new StatViewServlet();

            registrationBean.setServlet(statViewServlet);

            String urlPatternKey= "url-pattern";
            String allowKey= "allow";
            String denyKey= "deny";
            String usernameKey= "login-username";
            String secretKey = "login-password";
            String resetEnableKey= "reset-enable";

            if(druidPropertiesMaps.containsKey(urlPatternKey)){
                String urlPatternValue =
                        druidPropertiesMaps.get(urlPatternKey).toString();
                registrationBean.addUrlMappings(urlPatternValue);
            }
            else
            {
                registrationBean.addUrlMappings("/druid/*");
            }

            addBeanParameter(druidPropertiesMaps,registrationBean, "allow",allowKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "deny",denyKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "loginUsername",usernameKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "loginPassword",secretKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "resetEnable",resetEnableKey);
        }

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        RelaxedPropertyResolver property =
                new RelaxedPropertyResolver(environment, "spring.datasource.druid.");

        Map<String, Object> druidPropertiesMaps = property.getSubProperties("web-stat-filter.");


        boolean webStatFilterEnabled = false;
        String webStatFilterEnabledKey = ENABLED_ATTRIBUTE_NAME;
        FilterRegistrationBean registrationBean = null;
        if(druidPropertiesMaps.containsKey(webStatFilterEnabledKey))
        {
            String webStatFilterEnabledValue =
                    druidPropertiesMaps.get(webStatFilterEnabledKey).toString();
            webStatFilterEnabled = Boolean.parseBoolean(webStatFilterEnabledValue);
        }
        if(webStatFilterEnabled){
            registrationBean = new FilterRegistrationBean();
            WebStatFilter filter = new WebStatFilter();
            registrationBean.setFilter(filter);

            String urlPatternKey = "url-pattern";
            String exclusionsKey = "exclusions";
            String sessionStatEnabledKey = "session-stat-enable";
            String profileEnabledKey = "profile-enable";
            String principalCookieNameKey = "principal-cookie-name";
            String principalSessionNameKey = "principal-session-name";
            String sessionStateMaxCountKey = "session-stat-max-count";

            if(druidPropertiesMaps.containsKey(urlPatternKey)){
                String urlPatternValue =
                        druidPropertiesMaps.get(urlPatternKey).toString();
                registrationBean.addUrlPatterns(urlPatternValue);
            }
            else{
                registrationBean.addUrlPatterns("/*");
            }

            if(druidPropertiesMaps.containsKey(exclusionsKey)){
                String exclusionsValue =
                        druidPropertiesMaps.get(exclusionsKey).toString();
                registrationBean.addInitParameter("exclusions",exclusionsValue);
            }
            else{
                registrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
            }

            addBeanParameter(druidPropertiesMaps,registrationBean, "sessionStatEnable",sessionStatEnabledKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "profileEnable",profileEnabledKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "principalCookieName",principalCookieNameKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "sessionStatMaxCount",sessionStateMaxCountKey);
            addBeanParameter(druidPropertiesMaps,registrationBean, "principalSessionName",principalSessionNameKey);
        }
        return registrationBean;
    }

    private void addBeanParameter(Map<String,Object> druidPropertyMap, RegistrationBean registrationBean, String paramName, String propertyKey){
        if(druidPropertyMap.containsKey(propertyKey)){
            String propertyValue =
                    druidPropertyMap.get(propertyKey).toString();
            registrationBean.addInitParameter(paramName, propertyValue);
        }
    }

    private void initDruidFilters(DruidDataSource druidDataSource){

        List<Filter> filters = druidDataSource.getProxyFilters();

        RelaxedPropertyResolver filterProperty =
                new RelaxedPropertyResolver(environment, "spring.datasource.druid.filter.");


        String filterNames= environment.getProperty("spring.datasource.druid.filters");

        String[] filterNameArray = filterNames.split("\\,");

        for(int i=0; i<filterNameArray.length;i++){
            String filterName = filterNameArray[i];
            Filter filter = filters.get(i);

            Map<String, Object> filterValueMap = filterProperty.getSubProperties(filterName + ".");
            String statFilterEnabled = filterValueMap.get(ENABLED_ATTRIBUTE_NAME).toString();
            if(statFilterEnabled.equals("true")){
                MutablePropertyValues propertyValues = new  MutablePropertyValues(filterValueMap);
                RelaxedDataBinder dataBinder = new RelaxedDataBinder(filter);
                dataBinder.bind(propertyValues);
            }
        }
    }
}

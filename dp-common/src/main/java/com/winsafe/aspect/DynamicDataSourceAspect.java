package com.winsafe.aspect;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.winsafe.annotation.DataSourceAnnotation;
import com.winsafe.datasource.DataSourceName;
import com.winsafe.datasource.DynamicDataSourceContextHolder;

@Aspect
@Order(-1)
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LogManager.getLogger(DynamicDataSourceAspect.class);

    /**
     * 切换数据库
     * @param point
     * @param dataSourceAnnotation
     * @return
     * @throws Throwable
     */
    @Before("@annotation(dataSourceAnnotation)")
    public void changeDataSource(JoinPoint point, DataSourceAnnotation dataSourceAnnotation){
    	for(Object object: point.getArgs()){
    		if(object instanceof DataSourceName){
    			String dsId = ((DataSourceName) object).getName();
    			if(!DynamicDataSourceContextHolder.existDateSoure(dsId)){
    	            logger.info("Data source 【"+dsId+"】 not exists, use primary data source instead.");
    	            return;
    	        }else{
    	            DynamicDataSourceContextHolder.setDateSoureType(dsId);
    	        }
    			break;
    		}
    	}
    }

    /**
     * @Title: destroyDataSource
     * @Description: 销毁数据源  在所有的方法执行执行完毕后
     * @param point
     * @param dataSourceAnnotation
     * @return void
     * @throws
     */
    @After("@annotation(dataSourceAnnotation)")
    public void destroyDataSource(JoinPoint point,DataSourceAnnotation dataSourceAnnotation){
        DynamicDataSourceContextHolder.clearDateSoureType();
    }
}

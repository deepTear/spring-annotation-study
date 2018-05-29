package com.mouchina.server.provider.datasource;


import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

public class DynamicDataSourceAop {

	Logger logger = Logger.getLogger(DynamicDataSourceAop.class);
	  
    @Autowired  
    private DynamicDataSourceHolder dataSourceHolder;  
  
    //AOP环绕切点  
    public Object doAroundMethod(ProceedingJoinPoint pjp) throws Throwable {  
        Object response = null;  
        // method为方法名。并不是URL访问的名称  
        String method = pjp.getSignature().getName();  
        boolean hasBinded = false;  
        try {  
            // hasBinded 是否已经绑定datasource  
            hasBinded = dataSourceHolder.hasBindedDataSourse();  
            logger.info("判断是否已经绑定数据源 ******* "+hasBinded+"   当前要执行的方法名称--------"+method);  
            if (!hasBinded) {
                if (method.startsWith("query") || method.startsWith("select") || method.startsWith("find")  
                        || method.startsWith("get") || method.startsWith("load") || method.startsWith("search") || method.startsWith("count")) {  
                    dataSourceHolder.markSlave();  
                } else {
                    dataSourceHolder.markMaster();  
                }
            }  
            //调用proceed()方法作为环绕通知执行方法前后的分水岭  
            logger.info("查询开始--------------------------------->");
            response = pjp.proceed();  
            logger.info("查询结束--------------------------------->");  
        } finally {
            if (!hasBinded) {  
                logger.info("请求的方法执行完毕，清除已标记选取的数据源");  
                dataSourceHolder.markRemove();
            }  
        }  
        return response;  
    }  
}

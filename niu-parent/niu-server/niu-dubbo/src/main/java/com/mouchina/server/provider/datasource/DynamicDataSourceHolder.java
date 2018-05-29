package com.mouchina.server.provider.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSourceHolder extends AbstractRoutingDataSource implements InitializingBean{

	// 线程本地环境  
    private static final ThreadLocal<String> dataSourceHolder = new ThreadLocal<String>();  
    // 可选取slave的keys  
    private List<String> slaveDataSourcesKey;  
    // 可选取master的keys  
    private List<String> masterDataSourcesKey;  
    //从库数据源  
    private Map<String, DataSource> slaveDataSource;  
    //主库数据源  
    private Map<String, DataSource> masterDataSource;  
    
    private Logger logger = Logger.getLogger(DynamicDataSourceHolder.class);  
  
    @Override  
    //spring 执行数据源切换的核心方法。  
    protected Object determineCurrentLookupKey() {  
        return dataSourceHolder.get();  
    }  
      
    @Override  
    public void afterPropertiesSet() {  
        // 数据检验和合并  
        logger.info("开始向Spring DataSource 提供数据源选取");  
        Map<Object, Object> allDataSources = new HashMap<Object, Object>();  
        allDataSources.putAll(masterDataSource);  
        if (slaveDataSource != null) {  
            allDataSources.putAll(slaveDataSource);  
        }  
        super.setTargetDataSources(allDataSources);  
        super.afterPropertiesSet();  
        logger.info("主库数据源：："+masterDataSource.keySet());  
        logger.info("从库数据源：："+slaveDataSource.keySet());  
        logger.info("已经完提供数据源选取");  
    }  
    /** 
     * 注册slave datasource 
     *  
     * @param slavetDataSources 
     */  
    public void setSlaveDataSource(Map<String, DataSource> slaveDataSources) {  
        if (slaveDataSources == null || slaveDataSources.size() == 0) {  
            return;  
        }  
        logger.info("提供可选取从库数据源："+slaveDataSources.keySet());  
        this.slaveDataSource = slaveDataSources;  
        slaveDataSourcesKey = new ArrayList<String>();  
        for (Entry<String, DataSource> entry : slaveDataSources.entrySet()) {  
        	slaveDataSourcesKey.add(entry.getKey());  
        }  
    }  
    /** 
     * 注册master datasource 
     *  
     * @param masterDataSources 
     */  
    public void setMasterDataSource(Map<String, DataSource> masterDataSources) {  
        if (masterDataSources == null) {  
            throw new IllegalArgumentException("Property 'masterDataSources' is required");  
        }  
        logger.info("提供可选取主库数据源："+masterDataSources.keySet());  
        this.masterDataSource = masterDataSources;  
        this.masterDataSourcesKey = new ArrayList<String>();  
        for (Entry<String, DataSource> entry : masterDataSources.entrySet()) {  
        	masterDataSourcesKey.add(entry.getKey());  
        }  
    }  
  
    /** 
     * 标记选取从库数据源 
     */  
    public void markSlave() {  
        if (dataSourceHolder.get() != null) {  
            // 从现在的策略来看,不允许标记两次,直接抛异常,优于早发现问题  
            throw new IllegalArgumentException("当前已有选取数据源,不允许覆盖,已选数据源key:" + dataSourceHolder.get());  
        }  
        logger.info("查询的是从库");  
        String dataSourceKey = selectFromSlave();  
        setDataSource(dataSourceKey);  
    }  
  
    /** 
     * 标记选取主库数据源 
     */  
    public void markMaster() {  
        if (dataSourceHolder.get() != null) {  
            // 从现在的策略来看,不允许标记两次,直接抛异常,优于早发现问题  
            throw new IllegalArgumentException("当前已有选取数据源,不允许覆盖,已选数据源key:" + dataSourceHolder.get());  
        }  
        logger.info("查询的是主库");  
        String dataSourceKey = selectFromMaster();  
        setDataSource(dataSourceKey);  
    }  
  
    /** 
     * 删除己标记选取的数据源 
     */  
    public void markRemove() {  
        dataSourceHolder.remove();  
    }  
    /** 
     * 是否已经绑定datasource 
     * 绑定：true 
     * 没绑定：false 
     * @return 
     */  
    public boolean hasBindedDataSourse(){  
        boolean hasBinded = dataSourceHolder.get() != null;  
        return hasBinded;  
    }  
  
    private String selectFromSlave() {  
        if (slaveDataSource == null) {  
            logger.info("提供可选取slave数据源："+slaveDataSource+",将自动切换从主master选取数据源");  
            return selectFromMaster();  
        } else {  
            Random random = new Random();  
            return slaveDataSourcesKey.get(random.nextInt(slaveDataSourcesKey.size()));  
        }
    }  
  
    private String selectFromMaster() {  
        Random random = new Random();  
        String dataSourceKey = masterDataSourcesKey.get(random.nextInt(masterDataSourcesKey.size()));  
        return dataSourceKey;  
    }  
  
    private void setDataSource(String dataSourceKey) {  
        logger.info("数据源  key *-*-*-*-*-* "+dataSourceKey);  
        dataSourceHolder.set(dataSourceKey);  
    }  

}

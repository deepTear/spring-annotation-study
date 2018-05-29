package com.mouchina.server.api.base;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T,PK extends Serializable> {

	/**
	 * 唯一主键查询实体
	 * @param pk
	 * @return
	 */
	public T findByPrimaryKey(PK pk);
	
	/**
	 * 保存
	 * @param t
	 * @return
	 */
	public boolean save(T t);
	
	/**
	 * 选择性保存数据
	 * @param t
	 * @return
	 */
	public boolean saveBySelective(T t);

	/**
	 * 通过唯一主键删除数据
	 * @param pk
	 * @return
	 */
    public boolean deletePrimaryKey(PK pk);
    
    /**
     * 选择性修改数据
     * @param t
     * @return
     */
    public boolean updateByPrimaryKeySelective(T t);
    
    /**
     * 修改所有内容
     * @param t
     * @return
     */
    public boolean updateByPrimaryKey(T t);

    /**
     * 查询全部列表
     * @param order
     * @param t
     * @return
     */
    public List<T> findList(String order,T t);
    
    /**
     * 分页查询列表
     * @param currentPage
     * @param pageSize
     * @param order
     * @param t
     * @return
     */
    public List<T> findPageList(int currentPage,int pageSize,String order,T t);
	
}

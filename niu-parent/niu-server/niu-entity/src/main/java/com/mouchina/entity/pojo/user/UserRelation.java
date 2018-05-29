package com.mouchina.entity.pojo.user;

import java.io.Serializable;
import java.util.Date;

public class UserRelation implements Serializable {
	
	public static final byte RELATION_TYPE_0 = 0;
	public static final byte RELATION_TYPE_1 = 1;
	
	public static final byte TYPE_0 = 0;
	public static final byte TYPE_1 = 1;
	
    /** */
    private Long id;

    /** 建立关系时间*/
    private Date createTime;

    /** 触发关系对象ID*/
    private Long relationA;

    /** 被触发关系对象ID*/
    private Long relationB;

    /** 关系类型（0：主动型  1：被动型）*/
    private Byte relationType;

    /** 创建关系数据源ID*/
    private Long dataId;

    /** 业务类型 0 ：邀请  1：关注*/
    private Byte type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_user_relation
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_relation.id
     *
     * @return the value of tb_user_relation.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_relation.id
     *
     * @param id the value for tb_user_relation.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_relation.create_time
     *
     * @return the value of tb_user_relation.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_relation.create_time
     *
     * @param createTime the value for tb_user_relation.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_relation.relation_a
     *
     * @return the value of tb_user_relation.relation_a
     *
     * @mbggenerated
     */
    public Long getRelationA() {
        return relationA;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_relation.relation_a
     *
     * @param relationA the value for tb_user_relation.relation_a
     *
     * @mbggenerated
     */
    public void setRelationA(Long relationA) {
        this.relationA = relationA;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_relation.relation_b
     *
     * @return the value of tb_user_relation.relation_b
     *
     * @mbggenerated
     */
    public Long getRelationB() {
        return relationB;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_relation.relation_b
     *
     * @param relationB the value for tb_user_relation.relation_b
     *
     * @mbggenerated
     */
    public void setRelationB(Long relationB) {
        this.relationB = relationB;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_relation.relation_type
     *
     * @return the value of tb_user_relation.relation_type
     *
     * @mbggenerated
     */
    public Byte getRelationType() {
        return relationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_relation.relation_type
     *
     * @param relationType the value for tb_user_relation.relation_type
     *
     * @mbggenerated
     */
    public void setRelationType(Byte relationType) {
        this.relationType = relationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_relation.data_id
     *
     * @return the value of tb_user_relation.data_id
     *
     * @mbggenerated
     */
    public Long getDataId() {
        return dataId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_relation.data_id
     *
     * @param dataId the value for tb_user_relation.data_id
     *
     * @mbggenerated
     */
    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_relation.type
     *
     * @return the value of tb_user_relation.type
     *
     * @mbggenerated
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_relation.type
     *
     * @param type the value for tb_user_relation.type
     *
     * @mbggenerated
     */
    public void setType(Byte type) {
        this.type = type;
    }
}
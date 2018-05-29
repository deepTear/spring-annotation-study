package com.mouchina.dao.user;

import com.mouchina.entity.pojo.user.UserTopic;
import com.mouchina.entity.pojo.user.UserTopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UserTopicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int countByExample(UserTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int deleteByExample(UserTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int insert(UserTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int insertSelective(UserTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    List<UserTopic> selectByExampleWithRowbounds(UserTopicExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    List<UserTopic> selectByExample(UserTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    UserTopic selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UserTopic record, @Param("example") UserTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UserTopic record, @Param("example") UserTopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserTopic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_topic
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserTopic record);
}
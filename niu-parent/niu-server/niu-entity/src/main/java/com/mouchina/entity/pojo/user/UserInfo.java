package com.mouchina.entity.pojo.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class UserInfo implements Serializable {
	
	public static final byte USER_STATE_0 = 0;
	public static final byte USER_STATE_1 = 1;
	public static final byte USER_SEX_0 = 0;
	public static final byte USER_SEX_1 = 1;
	public static final byte USER_SEX_2 = 2;
	
	private String loginKey;
	
    /** ID*/
    private Long id;

    /** 昵称*/
    private String nickName;

    /** 手机号*/
    private String phone;

    /** 密码*/
    private String password;

    /** 出生日期*/
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8") 
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    private Date birthday;

    /** 职业（0：未选择 ）*/
    private Byte profession = 0;

    /** 性别（0：未选择 ）*/
    private Byte sex = 0;

    /** 兴趣*/
    private String instrest;

    /** 收入（0：未选择 ）*/
    private Byte salary = 0;

    /** 注册时间*/
    private Date createTime;

    /** 最近一次登录时间*/
    private Date lastLoginTime;

    /** 头像*/
    private String photo;

    /** 状态（0：正常  1：封禁 2：删除）*/
    private Byte state = 0;

    /** 邀请码*/
    private String inviteCode;

    /** 省编码*/
    private String provinceCode;

    /** 市编码*/
    private String cityCode;

    /** 区县编码*/
    private String areaCode;

    /** 详细地址地址*/
    private String address;

    /** 角色（0：普通用户 1：系统人员  2：商户人员）*/
    private Byte role = 0;

    /** 修改时间*/
    private Date modifyTime;

    /** 令牌*/
    private String token;

    private String province; // 省份

	private String city; // 市

	private String area; // 区县

    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb_user_info
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.id
     *
     * @return the value of tb_user_info.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.id
     *
     * @param id the value for tb_user_info.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.nick_name
     *
     * @return the value of tb_user_info.nick_name
     *
     * @mbggenerated
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.nick_name
     *
     * @param nickName the value for tb_user_info.nick_name
     *
     * @mbggenerated
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.phone
     *
     * @return the value of tb_user_info.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.phone
     *
     * @param phone the value for tb_user_info.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.password
     *
     * @return the value of tb_user_info.password
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.password
     *
     * @param password the value for tb_user_info.password
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.birthday
     *
     * @return the value of tb_user_info.birthday
     *
     * @mbggenerated
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.birthday
     *
     * @param birthday the value for tb_user_info.birthday
     *
     * @mbggenerated
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.profession
     *
     * @return the value of tb_user_info.profession
     *
     * @mbggenerated
     */
    public Byte getProfession() {
        return profession;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.profession
     *
     * @param profession the value for tb_user_info.profession
     *
     * @mbggenerated
     */
    public void setProfession(Byte profession) {
        this.profession = profession;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.sex
     *
     * @return the value of tb_user_info.sex
     *
     * @mbggenerated
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.sex
     *
     * @param sex the value for tb_user_info.sex
     *
     * @mbggenerated
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.instrest
     *
     * @return the value of tb_user_info.instrest
     *
     * @mbggenerated
     */
    public String getInstrest() {
        return instrest;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.instrest
     *
     * @param instrest the value for tb_user_info.instrest
     *
     * @mbggenerated
     */
    public void setInstrest(String instrest) {
        this.instrest = instrest == null ? null : instrest.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.salary
     *
     * @return the value of tb_user_info.salary
     *
     * @mbggenerated
     */
    public Byte getSalary() {
        return salary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.salary
     *
     * @param salary the value for tb_user_info.salary
     *
     * @mbggenerated
     */
    public void setSalary(Byte salary) {
        this.salary = salary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.create_time
     *
     * @return the value of tb_user_info.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.create_time
     *
     * @param createTime the value for tb_user_info.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.last_login_time
     *
     * @return the value of tb_user_info.last_login_time
     *
     * @mbggenerated
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.last_login_time
     *
     * @param lastLoginTime the value for tb_user_info.last_login_time
     *
     * @mbggenerated
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.photo
     *
     * @return the value of tb_user_info.photo
     *
     * @mbggenerated
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.photo
     *
     * @param photo the value for tb_user_info.photo
     *
     * @mbggenerated
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.state
     *
     * @return the value of tb_user_info.state
     *
     * @mbggenerated
     */
    public Byte getState() {
        return state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.state
     *
     * @param state the value for tb_user_info.state
     *
     * @mbggenerated
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.invite_code
     *
     * @return the value of tb_user_info.invite_code
     *
     * @mbggenerated
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.invite_code
     *
     * @param inviteCode the value for tb_user_info.invite_code
     *
     * @mbggenerated
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode == null ? null : inviteCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.province_code
     *
     * @return the value of tb_user_info.province_code
     *
     * @mbggenerated
     */
    public String getProvinceCode() {
        return provinceCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.province_code
     *
     * @param provinceCode the value for tb_user_info.province_code
     *
     * @mbggenerated
     */
    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode == null ? null : provinceCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.city_code
     *
     * @return the value of tb_user_info.city_code
     *
     * @mbggenerated
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.city_code
     *
     * @param cityCode the value for tb_user_info.city_code
     *
     * @mbggenerated
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.area_code
     *
     * @return the value of tb_user_info.area_code
     *
     * @mbggenerated
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.area_code
     *
     * @param areaCode the value for tb_user_info.area_code
     *
     * @mbggenerated
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.address
     *
     * @return the value of tb_user_info.address
     *
     * @mbggenerated
     */
    public String getAddress() {
        return address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.address
     *
     * @param address the value for tb_user_info.address
     *
     * @mbggenerated
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.role
     *
     * @return the value of tb_user_info.role
     *
     * @mbggenerated
     */
    public Byte getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.role
     *
     * @param role the value for tb_user_info.role
     *
     * @mbggenerated
     */
    public void setRole(Byte role) {
        this.role = role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.modify_time
     *
     * @return the value of tb_user_info.modify_time
     *
     * @mbggenerated
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.modify_time
     *
     * @param modifyTime the value for tb_user_info.modify_time
     *
     * @mbggenerated
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tb_user_info.token
     *
     * @return the value of tb_user_info.token
     *
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tb_user_info.token
     *
     * @param token the value for tb_user_info.token
     *
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}
    
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
    
}
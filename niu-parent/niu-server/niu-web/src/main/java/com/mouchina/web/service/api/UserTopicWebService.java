package com.mouchina.web.service.api;

import java.util.Date;

public interface UserTopicWebService {

	/**
	 * 统计某用户某天发布动态条数
	 * @param date
	 * @param userId
	 * @param isFree  是否是普通动态
	 * @return
	 */
	public int countPublishTopic(Date date,Long userId,Boolean isFree);
}

package com.mouchina.server.api.user;

import java.util.Date;

import com.mouchina.entity.pojo.user.UserTopic;
import com.mouchina.server.api.base.BaseService;

public interface UserTopicService extends BaseService<UserTopic,Long>{

	public int countPublishTopic(Date date,Long userId,Boolean isFree);
}

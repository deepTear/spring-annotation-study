package com.mouchina.web.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouchina.server.api.user.UserTopicService;
import com.mouchina.web.service.api.UserTopicWebService;

@Service
public class UserTopicWebServiceImpl implements UserTopicWebService {

	@Autowired
	private UserTopicService userTopicService;

	@Override
	public int countPublishTopic(Date date, Long userId, Boolean isFree) {
		return userTopicService.countPublishTopic(date, userId, isFree);
	}
	
	
}

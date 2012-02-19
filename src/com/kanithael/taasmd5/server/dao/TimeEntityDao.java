package com.kanithael.taasmd5.server.dao;

import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.DAOBase;
import com.kanithael.taasmd5.shared.TimeEntity;

public class TimeEntityDao extends DAOBase {

	private static final TimeEntityDao INSTANCE = new TimeEntityDao();

	private TimeEntityDao() {
	}

	public static TimeEntityDao getInstance() {
		return INSTANCE;
	}

	static {
		ObjectifyService.register(TimeEntity.class);
	}

	public void saveTimeEntity(TimeEntity timeEntity) {
		ofy().put(timeEntity);
	}

	public List<TimeEntity> fetchLastTimeEntities() {
		List<TimeEntity> timeEntities = ofy().query(TimeEntity.class).list();
		if (timeEntities.size() == 0) {
			TimeEntity teMD5 = new TimeEntity(TimeEntity.TimeEntityType.MD5);
			ofy().put(teMD5);
			timeEntities.add(teMD5);
			TimeEntity teTAAS = new TimeEntity(TimeEntity.TimeEntityType.TAAS_MD5);
			ofy().put(teTAAS);
			timeEntities.add(teTAAS);
		}
		return timeEntities;
	}
}

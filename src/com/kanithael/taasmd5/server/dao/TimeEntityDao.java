package com.kanithael.taasmd5.server.dao;

import java.util.Date;
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

	public TimeEntity fetchLastTimeEntity() {
		TimeEntity te = null;
		List<TimeEntity> timeEntities = ofy().query(TimeEntity.class).list();
		if (timeEntities.size() == 0) {
			te = new TimeEntity();
			ofy().put(te);
		} else {
			te = timeEntities.get(0);
		}
		return te;
	}
}

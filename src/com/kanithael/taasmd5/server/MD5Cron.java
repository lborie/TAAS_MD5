package com.kanithael.taasmd5.server;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.kanithael.taasmd5.server.dao.TimeEntityDao;
import com.kanithael.taasmd5.shared.TimeEntity;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MD5Cron extends RemoteServiceServlet {
	private static final String MENTION_TAG = "@";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TimeEntityDao dao = TimeEntityDao.getInstance();
		Twitter twitter = new TwitterFactory().getInstance();
		List<TimeEntity> timeEntities = dao.fetchLastTimeEntities();

		for (TimeEntity entity : timeEntities) {
			this.tagProcess(entity, twitter, dao);
		}
	}

	private void tagProcess(TimeEntity entity, Twitter twitter, TimeEntityDao dao) {
		Query query = new Query(entity.getTag());
		if (entity.getLastId() != null) {
			query.setSinceId(entity.getLastId());
		} else {
			entity.setLastId(new Long(0));
		}

		try {
			List<String> timeLineContent = new ArrayList<String>();
			ResponseList<Status> responseList = twitter.getHomeTimeline();
			for (Status response : responseList) {
				timeLineContent.add(response.getText());
			}

			QueryResult qr = twitter.search(query);
			for (Tweet tweet : qr.getTweets()) {
				String fromUser = tweet.getFromUser();
				String content = tweet.getText();
				String futurStatus = MENTION_TAG + fromUser + " " + this.encode(content.replaceAll(entity.getTag(), "").trim());
				if (!timeLineContent.contains(futurStatus) && tweet.getId() != entity.getLastId() && !content.contains("TaasMD5") && !"TaasMD5".equals(fromUser)) {
					twitter.updateStatus(futurStatus);
					if (entity.getLastId() < tweet.getId()) {
						entity.setLastId(tweet.getId());
					}
					System.out.println(futurStatus);
				}
			}
			dao.saveTimeEntity(entity);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private String encode(String password) {
		byte[] uniqueKey = password.getBytes();
		byte[] hash = null;

		try {
			hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
		} catch (NoSuchAlgorithmException e) {
			throw new Error("No MD5 support in this VM.");
		}

		StringBuilder hashString = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(hash[i]);
			if (hex.length() == 1) {
				hashString.append('0');
				hashString.append(hex.charAt(hex.length() - 1));
			} else
				hashString.append(hex.substring(hex.length() - 2));
		}
		return hashString.toString();
	}

}

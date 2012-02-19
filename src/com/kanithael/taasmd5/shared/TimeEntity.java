/**
 * 
 */
package com.kanithael.taasmd5.shared;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

/**
 * @author kanithael
 */
@Entity
@Unindexed
public class TimeEntity {

	public enum TimeEntityType {
		TAAS_MD5("#taasmd5"), MD5("#MD5");

		private String type;

		private TimeEntityType(String tag) {
			this.type = tag;
		}

		public String getType() {
			return this.type;
		}
	}

	@Id
	private Long id;

	private Long lastId;

	@Indexed
	private String tag;

	public TimeEntity(TimeEntityType type) {
		this.tag = type.getType();
	}

	/**
	 * @return the lastTimeEntity
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param lastTimeEntity
	 *            the lastTimeEntity to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the lastId
	 */
	public Long getLastId() {
		return lastId;
	}

	/**
	 * @param lastId
	 *            the lastId to set
	 */
	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}
}

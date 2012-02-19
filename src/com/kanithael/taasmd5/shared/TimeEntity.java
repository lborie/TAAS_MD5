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
	@Id
	private Long id;

	private Long lastId;

	@Indexed
	private String lastTimeEntity = "lastTimeEntity";

	/**
	 * @return the lastTimeEntity
	 */
	public String getLastTimeEntity() {
		return lastTimeEntity;
	}

	/**
	 * @param lastTimeEntity
	 *            the lastTimeEntity to set
	 */
	public void setLastTimeEntity(String lastTimeEntity) {
		this.lastTimeEntity = lastTimeEntity;
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

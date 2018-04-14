package cn.com.flaginfo.platform.terminal.mongo.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.util.Date;

/**
 * @version 0.0.1
 * @since 0.0.1
 * @author Shaun Chyxion <br />
 * chyxion@163.com <br />
 * Sep 12, 2015 2:26:18 PM
 */
public abstract class BaseMongoDbModel implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String ID = "id";
	public static final String DATE_CREATED = "dateCreated";
	public static final String DATE_UPDATED = "dateUpdated";

	@Id
	protected String id;
	@CreatedDate
	protected Date dateCreated;
	@LastModifiedDate
	protected Date dateUpdated;

	@Indexed
	protected String corpId;

	private String status;

	private Boolean flag;
	/**
	 * @return the corpId
	 */
	public String getCorpId() {
		return corpId;
	}
	/**
	 * @param corpId the corpId to set
	 */
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
}

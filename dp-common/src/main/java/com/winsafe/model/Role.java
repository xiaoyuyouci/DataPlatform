package com.winsafe.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统角色Model
 * @author Ryan
 *
 */
public class Role implements Serializable {
    private Integer id;

    private Integer creator;

    private Date creationTime;

    private Integer modifier;

    private Date modificationTime;

    private String name;

    private Integer deletable;
    
    private String searchVal;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public Integer getDeletable() {
		return deletable;
	}

	public void setDeletable(Integer deletable) {
		this.deletable = deletable;
	}

	public String getSearchVal() {
		return searchVal;
	}

	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
}
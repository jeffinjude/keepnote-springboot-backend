package com.stackroute.keepnote.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class Category {
	/**
	 * 
	 */
	@Id
	private String categoryId;
	/**
	 * 
	 */
	private String categoryName;
	/**
	 * 
	 */
	private String categoryDescription;
	/**
	 * 
	 */
	private String categoryCreatedBy;
	/**
	 * 
	 */
	private Date categoryCreationDate;
	/**
	 * 
	 */
	public Category() {}
	
    /**
	 * @param categoryId
	 * @param categoryName
	 * @param categoryDescription
	 * @param categoryCreatedBy
	 * @param categoryCreationDate
	 */
	public Category(final String categoryId, final String categoryName, final String categoryDescription, final String categoryCreatedBy,
			final Date categoryCreationDate) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.categoryCreatedBy = categoryCreatedBy;
		this.categoryCreationDate = categoryCreationDate;
	}

	public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(final String categoryId) {
       this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(final String categoryName) {
       this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return this.categoryDescription;
    }

    public void setCategoryDescription(final String categoryDescription) {
    	this.categoryDescription = categoryDescription;
    }

    public String getCategoryCreatedBy() {
        return this.categoryDescription;
    }

    public void setCategoryCreatedBy(final String categoryCreatedBy) {
        this.categoryCreatedBy=categoryCreatedBy;
    }

    public Date getCategoryCreationDate() {
        return this.categoryCreationDate;
    }

    public void setCategoryCreationDate(final Date categoryCreationDate) {
       this.categoryCreationDate=categoryCreationDate;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Category [id=" + categoryId + ", categoryName=" + categoryName + ", categoryDescription=" + categoryDescription
				+ ", categoryCreatedBy=" + categoryCreatedBy + ", categoryCreationDate=" + categoryCreationDate + "]";
	}


}

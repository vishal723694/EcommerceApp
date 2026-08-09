package com.entity;

/**
 * Enhanced Category entity model with descriptions and product counter metadata.
 * 
 * @author Vishal
 */
public class CategoryDetail {
    private int cid;
    private String cname;
    private String description;
    private String iconClass;
    private int productCount;

    public CategoryDetail() {}

    public CategoryDetail(int cid, String cname, String description, String iconClass, int productCount) {
        this.cid = cid;
        this.cname = cname;
        this.description = description;
        this.iconClass = iconClass;
        this.productCount = productCount;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}

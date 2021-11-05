package org.guigo.hbte.bean;

import scala.Serializable;

/**
 * 数据库的动态配置 元数据映射字段属性的值
 */
public class Schema implements Serializable {
    private String id;
    private String typeId;
    private String companyId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Schema() {

    }

    public Schema(String id, String typeId, String companyId) {
        this.id = id;
        this.typeId = typeId;
        this.companyId = companyId;
    }
}

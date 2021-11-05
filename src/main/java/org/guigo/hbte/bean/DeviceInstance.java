package org.guigo.hbte.bean;

import org.guigo.hbte.annotation.TableKuduColumn;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 设备实时数据实例
 */
public class DeviceInstance implements Serializable {

    public DeviceInstance() {
        time = System.currentTimeMillis() * 1000;
    }

    @TableKuduColumn
    private long time;
    @TableKuduColumn
    private String id;
    @TableKuduColumn("type_id")
    private String typeId;
    @TableKuduColumn("company_id")
    private String companyId;

    @TableKuduColumn("dynamic_attribute1")
    private String dynamicAttribute1;

    @TableKuduColumn("dynamic_attribute2")
    private String dynamicAttribute2;

    @TableKuduColumn("dynamic_attribute3")
    private String dynamicAttribute3;

    @TableKuduColumn("dynamic_attribute4")
    private String dynamicAttribute4;

    @TableKuduColumn("dynamic_attribute5")
    private String dynamicAttribute5;

    @TableKuduColumn("dynamic_attribute6")
    private String dynamicAttribute6;

    @TableKuduColumn("dynamic_attribute7")
    private String dynamicAttribute7;

    @TableKuduColumn("dynamic_attribute8")
    private String dynamicAttribute8;

    @TableKuduColumn("dynamic_attribute9")
    private String dynamicAttribute9;

    @TableKuduColumn("dynamic_attribute10")
    private String dynamicAttribute10;

    @TableKuduColumn("dynamic_attribute11")
    private String dynamicAttribute11;

    @TableKuduColumn("dynamic_attribute12")
    private String dynamicAttribute12;

    @TableKuduColumn("dynamic_attribute13")
    private String dynamicAttribute13;

    @TableKuduColumn("dynamic_attribute14")
    private String dynamicAttribute14;

    @TableKuduColumn("dynamic_attribute15")
    private String dynamicAttribute15;

    @TableKuduColumn("dynamic_attribute16")
    private String dynamicAttribute16;

    @TableKuduColumn("dynamic_attribute17")
    private String dynamicAttribute17;

    @TableKuduColumn("dynamic_attribute18")
    private String dynamicAttribute18;

    @TableKuduColumn("dynamic_attribute19")
    private String dynamicAttribute19;

    @TableKuduColumn("dynamic_attribute20")
    private String dynamicAttribute20;

    @TableKuduColumn("dynamic_attribute21")
    private String dynamicAttribute21;

    @TableKuduColumn("dynamic_attribute22")
    private String dynamicAttribute22;

    @TableKuduColumn("dynamic_attribute23")
    private String dynamicAttribute23;

    @TableKuduColumn("dynamic_attribute24")
    private String dynamicAttribute24;

    @TableKuduColumn("dynamic_attribute25")
    private String dynamicAttribute25;

    @TableKuduColumn("dynamic_attribute26")
    private String dynamicAttribute26;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getDynamicAttribute1() {
        return dynamicAttribute1;
    }

    public String getDynamicAttribute2() {
        return dynamicAttribute2;
    }

    public String getDynamicAttribute3() {
        return dynamicAttribute3;
    }

    public String getDynamicAttribute4() {
        return dynamicAttribute4;
    }

    public String getDynamicAttribute5() {
        return dynamicAttribute5;
    }

    public String getDynamicAttribute6() {
        return dynamicAttribute6;
    }

    public String getDynamicAttribute7() {
        return dynamicAttribute7;
    }

    public String getDynamicAttribute8() {
        return dynamicAttribute8;
    }

    public String getDynamicAttribute9() {
        return dynamicAttribute9;
    }

    public String getDynamicAttribute10() {
        return dynamicAttribute10;
    }

    public String getDynamicAttribute11() {
        return dynamicAttribute11;
    }

    public String getDynamicAttribute12() {
        return dynamicAttribute12;
    }

    public String getDynamicAttribute13() {
        return dynamicAttribute13;
    }

    public String getDynamicAttribute14() {
        return dynamicAttribute14;
    }

    public String getDynamicAttribute15() {
        return dynamicAttribute15;
    }

    public String getDynamicAttribute16() {
        return dynamicAttribute16;
    }

    public String getDynamicAttribute17() {
        return dynamicAttribute17;
    }

    public String getDynamicAttribute18() {
        return dynamicAttribute18;
    }

    public String getDynamicAttribute19() {
        return dynamicAttribute19;
    }

    public String getDynamicAttribute20() {
        return dynamicAttribute20;
    }

    public String getDynamicAttribute21() {
        return dynamicAttribute21;
    }

    public String getDynamicAttribute22() {
        return dynamicAttribute22;
    }

    public String getDynamicAttribute23() {
        return dynamicAttribute23;
    }

    public String getDynamicAttribute24() {
        return dynamicAttribute24;
    }

    public String getDynamicAttribute25() {
        return dynamicAttribute25;
    }

    public String getDynamicAttribute26() {
        return dynamicAttribute26;
    }

    /**
     * 设置动态属性
     *
     * @param attributeName 动态属性名称
     * @param value         动态属性值
     */
    public void attribute(String attributeName, Object value) {
        try {
            Field dynamicAttribute = this.getClass().getDeclaredField(attributeName);
            dynamicAttribute.setAccessible(true);
            if (String.class == dynamicAttribute.getType()) {
                dynamicAttribute.set(this, value.toString());
            } else if (Integer.class == dynamicAttribute.getType()) {
                dynamicAttribute.set(this, Integer.getInteger((String) value));
            } else if (Boolean.class == dynamicAttribute.getType()) {
                dynamicAttribute.set(this, Boolean.getBoolean((String) value));
            } else if (Float.class == dynamicAttribute.getType()) {
                dynamicAttribute.set(this, Float.parseFloat((String) value));
            } else if (Double.class == dynamicAttribute.getType()) {
                dynamicAttribute.set(this, Double.parseDouble((String) value));
            } else if (Long.class == dynamicAttribute.getType()) {
                dynamicAttribute.set(this, Long.getLong((String) value));
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException("device instance dont have this attribute : " + attributeName);
        }
    }

    @Override
    public String toString() {
        return "DeviceInstance{" +
                "time=" + time +
                ", id='" + id + '\'' +
                ", typeId='" + typeId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", dynamicAttribute1='" + dynamicAttribute1 + '\'' +
                ", dynamicAttribute2='" + dynamicAttribute2 + '\'' +
                ", dynamicAttribute3='" + dynamicAttribute3 + '\'' +
                ", dynamicAttribute4='" + dynamicAttribute4 + '\'' +
                ", dynamicAttribute5='" + dynamicAttribute5 + '\'' +
                ", dynamicAttribute6='" + dynamicAttribute6 + '\'' +
                ", dynamicAttribute7='" + dynamicAttribute7 + '\'' +
                ", dynamicAttribute8='" + dynamicAttribute8 + '\'' +
                ", dynamicAttribute9='" + dynamicAttribute9 + '\'' +
                ", dynamicAttribute10='" + dynamicAttribute10 + '\'' +
                ", dynamicAttribute11='" + dynamicAttribute11 + '\'' +
                ", dynamicAttribute12='" + dynamicAttribute12 + '\'' +
                ", dynamicAttribute13='" + dynamicAttribute13 + '\'' +
                ", dynamicAttribute14='" + dynamicAttribute14 + '\'' +
                ", dynamicAttribute15='" + dynamicAttribute15 + '\'' +
                ", dynamicAttribute16='" + dynamicAttribute16 + '\'' +
                ", dynamicAttribute17='" + dynamicAttribute17 + '\'' +
                ", dynamicAttribute18='" + dynamicAttribute18 + '\'' +
                ", dynamicAttribute19='" + dynamicAttribute19 + '\'' +
                ", dynamicAttribute20='" + dynamicAttribute20 + '\'' +
                ", dynamicAttribute21='" + dynamicAttribute21 + '\'' +
                ", dynamicAttribute22='" + dynamicAttribute22 + '\'' +
                ", dynamicAttribute23='" + dynamicAttribute23 + '\'' +
                ", dynamicAttribute24='" + dynamicAttribute24 + '\'' +
                ", dynamicAttribute25='" + dynamicAttribute25 + '\'' +
                ", dynamicAttribute26='" + dynamicAttribute26 + '\'' +
                '}';
    }
}

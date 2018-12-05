package com.ccit.entity;

import javax.persistence.*;

/**
 * Created by lance on 16-11-14.
 */
@Entity
@Table(name = "global_dictionary", schema = "matrix", catalog = "")
public class GlobalDictionaryEntity {
    private long id;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String key;

    @Basic
    @Column(name = "key", nullable = true, length = 100)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String value;

    @Basic
    @Column(name = "value", nullable = true, length = 1000)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }    private String remark;

    @Basic
    @Column(name = "remark", nullable = true, length = 255)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalDictionaryEntity that = (GlobalDictionaryEntity) o;

        if (id != that.id) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        return result;
    }
}

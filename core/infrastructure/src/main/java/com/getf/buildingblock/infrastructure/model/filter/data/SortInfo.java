package com.getf.buildingblock.infrastructure.model.filter.data;

import com.getf.buildingblock.infrastructure.util.StringUtil;

/**
 * 排序信息
 */
public class SortInfo {

    /**
     * 字段名
     */
    String fieldName;

    /**
     * 是否逆序排序
     */
    boolean isDesc;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDbFieldName() {
        fieldName = fieldName.replace("`", "");
        return StringUtil.humpToLine(fieldName);
    }

    public boolean getDesc() {
        return isDesc;
    }

    public void setDesc(boolean desc) {
        isDesc = desc;
    }
}

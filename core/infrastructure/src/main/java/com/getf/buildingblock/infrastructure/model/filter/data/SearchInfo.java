package com.getf.buildingblock.infrastructure.model.filter.data;

import com.getf.buildingblock.infrastructure.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询信息
 */
public class SearchInfo {
    public static final String EQ = "EQ";
    public static final String NEQ = "NEQ";
    public static final String GT = "GT";
    public static final String GTE = "GTE";
    public static final String LT = "LT";
    public static final String LTE = "LTE";
    public static final String LIKE = "LIKE";

    public static SearchInfo createSearchInfo(String fieldName, String value) {
        return createSearchInfo(fieldName, value, EQ);
    }

    public static SearchInfo createSearchInfo(String fieldName, String value, String searchType) {
        SearchInfo r = new SearchInfo();
        r.setFieldName(fieldName);
        r.setSearchType(searchType);
        List<String> values = new ArrayList<>();
        values.add(value);
        r.setValues(values);
        return r;
    }

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 查询类型 可选值 EQ GT GTE LT LTE LIKE 默认值LIKE
     */
    private String searchType;

    /**
     * 查询的值
     */
    List<String> values;

    public String getFieldName() {
        return fieldName;
    }

    public String getDbFieldName() {
        fieldName = fieldName.replace("`", "");
        return StringUtil.humpToLine(fieldName);
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}

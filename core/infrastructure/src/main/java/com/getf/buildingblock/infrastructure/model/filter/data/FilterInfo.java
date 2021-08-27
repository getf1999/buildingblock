package com.getf.buildingblock.infrastructure.model.filter.data;

import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.model.result.ListPageResult;
import lombok.Data;
import lombok.var;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Data
public class FilterInfo {
    public static FilterInfo parse(String v) {
        FilterInfo r = JSONObject.parseObject(v, FilterInfo.class);
        return r;
    }

    public static FilterInfo parse(InputStream s) throws IOException {
        FilterInfo r = JSONObject.parseObject(s, FilterInfo.class);
        return r;
    }

    public static FilterInfo createFilterInfo(String fieldName, String value) {
        SearchInfo searchInfo = SearchInfo.createSearchInfo(fieldName, value);
        return createFilterInfo(searchInfo);
    }

    public static FilterInfo createFilterInfo(String fieldName, String value, String searchType) {
        SearchInfo searchInfo = SearchInfo.createSearchInfo(fieldName, value, searchType);
        return createFilterInfo(searchInfo);
    }

    public static FilterInfo createFilterInfo(SearchInfo searchInfo) {
        List<SearchInfo> searchInfos = new ArrayList();
        searchInfos.add(searchInfo);
        return createFilterInfo(searchInfos);
    }

    public static FilterInfo createFilterInfo(List<SearchInfo> searchInfos) {
        FilterInfo r = new FilterInfo();
        r.setSearchInfos(searchInfos);
        return r;
    }

    public void addSearchInfo(SearchInfo searchInfo) {
        var searchInfos = getSearchInfos();
        if(searchInfos == null) searchInfos = new ArrayList();
        searchInfos.add(searchInfo);
        setSearchInfos(searchInfos);
    }

    /**
     * 每页多少条 0时为无穷大
     */
    private int pageSize;

    /**
     * 当前第几页
     */
    private int pageIndex;

    /**
     * 查询信息
     */
    private List<SearchInfo> searchInfos;

    /**
     * 排序信息
     */
    private List<SortInfo> sortInfos;

    /**
     * 分页完成后返回的总数
     */
    private int total;

    /**
     * 需要查询的字段 null时为全部字段
     */
    private List<String> selectFields;

    /**
     * 忽略搜索的字段
     */
    private List<String> ignoreSearchFields;

    public SearchInfo getSearchInfo(String fieldName){
        var r=this.getSearchInfos().stream().filter(m->m.getFieldName().equals(fieldName)).findAny().orElse(null);
        return r;
    }


    public SearchInfo getSearchInfo(String fieldName,String searchType){
        var r=this.getSearchInfos().stream().filter(m->m.getFieldName().equals(fieldName)&&m.getSearchType().equals(searchType)).findAny().orElse(null);
        return r;
    }

    private JSONObject otherParams;

    public <T> ListPageResult<T> toListPageResult(T data){
        ListPageResult listPageResult=new ListPageResult();
        var listPageData=new ListPageResult.ListPageData<T>();
        listPageData.setPageIndex(pageIndex);
        listPageData.setPageSize(pageSize);
        listPageData.setTotal(total);
        listPageData.setData(data);
        listPageResult.setData(listPageData);
        return listPageResult;
    }
}

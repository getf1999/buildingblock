package com.getf.buildingblock.infrastructure.model.result;

import com.alibaba.fastjson.JSONArray;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import lombok.Data;

public class ListPageResult<T> extends Result<ListPageResult.ListPageData<T>> {
    @Data
    public static class ListPageData<T>{
        private int pageIndex;
        private int pageSize;
        private int total;
        private T data;
    }
}

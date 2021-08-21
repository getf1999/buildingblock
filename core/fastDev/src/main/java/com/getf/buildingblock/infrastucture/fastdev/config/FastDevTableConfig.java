package com.getf.buildingblock.infrastucture.fastdev.config;

import lombok.Data;

import java.util.List;

@Data
public class FastDevTableConfig {
    /**
     * 是否使用黑名单 默认true
     */
    private Boolean useBlackList;

    /**
     * useBlackList为true时 为黑名单列表  为false时 为白名单列表
     */
    private List<String> list;

    private List<TableConfig> tableConfigs;

    @Data
    public static class TableConfig{
        private String name;
        private String primaryKey;

        private CRUDConfig addConfig;

        private CRUDConfig editConfig;

        private CRUDConfig deleteConfig;

        private CRUDConfig getConfig;

        private CRUDConfig queryConfig;

        @Data
        public static class CRUDConfig{

            private List<String> ignoreFields;

            private String sql;
        }
    }
}

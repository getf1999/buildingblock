package com.getf.buildingblock.infrastructure.fastdev.config;

import com.getf.buildingblock.infrastructure.util.StringUtil;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties("getf.buildingblock.infrastucture.fastdev")
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

    /**
     * 公共拦截器类名集合
     */
    private List<String> publicInterceptorClassNames;

    @Data
    public static class TableConfig{
        private String routeName;
        public String getRouteName(){
            if(StringUtil.isNullOrEmpty(routeName)){
                return tableName;
            }
            return routeName;
        }

        private String tableName;
        private String primaryKeyName;

        private CRUDConfig addConfig;

        private CRUDConfig editConfig;

        private CRUDConfig deleteConfig;

        private CRUDConfig getConfig;

        private QueryConfig queryConfig;

        private QueryTreeConfig queryTreeConfig;

        /**
         * 忽略公共拦截器类名集合
         */
        private List<String> ignorePublicInterceptorClassNames;

        /**
         * 拦截器类名集合
         */
        private List<String> interceptorClassNames;

        @Data
        public static class CRUDConfig{

            private List<String> ignoreFields;

            private boolean disabled;
        }

        @Data
        public static class QueryConfig extends CRUDConfig{
            private String sql;
        }

        @Data
        public static class QueryTreeConfig extends QueryConfig{
            private String parentFieldName;
            private String childrenFieldName;
        }
    }
}

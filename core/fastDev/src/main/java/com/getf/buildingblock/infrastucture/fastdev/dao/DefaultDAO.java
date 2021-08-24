package com.getf.buildingblock.infrastucture.fastdev.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.getf.buildingblock.infrastructure.model.filter.data.FilterInfo;
import com.getf.buildingblock.infrastructure.util.StringUtil;
import com.getf.buildingblock.infrastucture.fastdev.config.FastDevTableConfig;
import com.getf.buildingblock.infrastucture.fastdev.dao.sql.builder.SqlHelper;
import lombok.var;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class DefaultDAO extends BaseDAO{
}

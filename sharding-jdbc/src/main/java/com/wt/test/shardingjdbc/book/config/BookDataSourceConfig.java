package com.wt.test.shardingjdbc.book.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.api.config.strategy.StandardShardingStrategyConfiguration;
import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import io.shardingsphere.core.keygen.KeyGenerator;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Xljnc
 * @date 2019/2/6 21:36
 * @description
 */
@Configuration
@MapperScan(basePackages = "com.wt.test.shardingjdbc.book.mapper", sqlSessionTemplateRef = "bookSqlSessionTemplate")
@Slf4j
public class BookDataSourceConfig {

    @Bean(name = "ds0")
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.ds0")
    public DataSource dataSource0() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ds1")
    @ConfigurationProperties(prefix = "sharding.jdbc.datasource.ds1")
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataSource")
    @Primary
    public DataSource getShardingDataSource() throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getBookTableRuleConfiguration());
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("category", "ds$->{id % 2}"));
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "book_$->{id % 3}"));
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new HashMap<>(), null);
    }

    private static KeyGenerator getKeyGenerator() {
        return new DefaultKeyGenerator();
    }

    private TableRuleConfiguration getBookTableRuleConfiguration() {
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration();
        tableRuleConfiguration.setLogicTable("book");
        tableRuleConfiguration.setActualDataNodes("ds$->{0..1}.book_$->{0..2}");
        tableRuleConfiguration.setKeyGenerator(getKeyGenerator());
        tableRuleConfiguration.setKeyGeneratorColumnName("id");
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("category", new BookDataSourceShardingAlgorithm()));
        tableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("id", "book_$->{id % 3}"));
        return tableRuleConfiguration;
    }


    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", dataSource0());
        dataSourceMap.put("ds1", dataSource1());
        return dataSourceMap;
    }

    @Slf4j
    static class BookDataSourceShardingAlgorithm implements PreciseShardingAlgorithm<String> {
        private static final String DEFAULT_DATASOURCE = "ds0";

        @Override
        public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
            String value = shardingValue.getValue();
            if (StringUtils.isEmpty(value)) {
                log.error("null sharding value,logicTable:{},column:{}", shardingValue.getLogicTableName(), shardingValue.getColumnName());
                return DEFAULT_DATASOURCE;
            }
            String expectedDsName = "ds" + value.hashCode() % 2;
            for (String name : availableTargetNames) {
                if (expectedDsName.equals(name))
                    return name;
            }
            return DEFAULT_DATASOURCE;
        }
    }

    @Bean
    public DataSourceTransactionManager transactitonManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    @Bean(name = "bookSqlSessionFactory")
    @Primary
    public SqlSessionFactory bookSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/book/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "bookSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate bookSqlSessionTemplate(@Qualifier("bookSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

package lxhausys.mybatis.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.util.ObjectUtils;

import lxhausys.mybatis.config.mybatis.DaoSupport;

@Configuration
@Conditional(MybatisProperties.class)
@EnableConfigurationProperties({MybatisProperties.class})
@MapperScan(basePackages = MybatisConfig.BASE_PACKAGES,
            annotationClass = org.apache.ibatis.annotations.Mapper.class
)
public class MybatisConfig {
  
  Logger log = LoggerFactory.getLogger(MybatisConfig.class);
  
  public static final String BASE_PACKAGES = "lxhausys";
  
  @Autowired
  MybatisProperties properties;
  
  @Autowired
  @Qualifier("lazyConnectionDataSourceProxy")
  LazyConnectionDataSourceProxy lazyConnectionDataSourceProxy;
  
  @Autowired
  @Qualifier("mssql")
  DataSource dataSource_mssql;
  
  @Autowired
  @Qualifier("oracle")
  DataSource dataSource_oracle;
  
  // mssql
  @Bean(name = "sqlSessionFactoryBean_mssql")
  @Primary
  SqlSessionFactoryBean sqlSessionFactoryBean_mssql() throws Exception {
    String configFile = properties.getConfigFile();
    String mapperLocation = properties.getMapperLocation();
    String typeAliasesPackage = properties.getTypeAliasesPackage();
    log.info("[mssql] configFile: " + configFile);
    log.info("[mssql] mapperLocation: " + mapperLocation);
    log.info("[mssql] typeAliasesPackage: " + typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    // RoutingDataSource 로 runtime 시 datasource 를 결정해야 할 경우 아래 코드 사용
    // sqlSessionFactoryBean.setDataSource(lazyConnectionDataSourceProxy);
    sqlSessionFactoryBean.setDataSource(dataSource_mssql);
    
    // mybatis.xml 을 사용할 경우
    sqlSessionFactoryBean.setConfigLocation(
        new PathMatchingResourcePatternResolver().getResource(configFile));
    sqlSessionFactoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources(mapperLocation));
    // parameterType, resultType 에 java package 생략 가능
    // 단, BASE_PACKAGES 하위 package 에 동일한 클래스명이 2개 이상일 경우 어플리케이션 booting 시 오류가 발생함
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    // 페이징 처리를 위한 mybatis-plugin 추가
    sqlSessionFactoryBean.setPlugins(new lxhausys.mybatis.config.mybatis.PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean
  @Primary
  DaoSupport daoSupport_mssql(
      @Qualifier("sqlSessionTemplate_mssql") SqlSessionTemplate sqlSessionTemplate) throws Exception {
    return new DaoSupport(sqlSessionTemplate);
  }
  
  @Bean(name = "sqlSessionTemplate_mssql")
  @Primary
  SqlSessionTemplate sqlSessionTemplate_mssql(
      @Qualifier("sqlSessionFactoryBean_mssql") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
     return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
  }
  
  @Bean(name = "sqlSessionTemplateForBatchExecutor_mssql")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor_mssql(
      @Qualifier("sqlSessionFactoryBean_mssql") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject(), ExecutorType.BATCH);
  }
  
  
  
  // oracle
  @Bean(name = "sqlSessionFactoryBean_oracle")
  SqlSessionFactoryBean sqlSessionFactoryBean_oracle() throws Exception {
    String configFile = properties.getConfigFile();
    String mapperLocation = properties.getMapperLocation();
    String typeAliasesPackage = properties.getTypeAliasesPackage();
    log.info("[oracle] configFile: " + configFile);
    log.info("[oracle] mapperLocation: " + mapperLocation);
    log.info("[oracle] typeAliasesPackage: " + typeAliasesPackage);
    
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    // RoutingDataSource 로 runtime 시 datasource 를 결정해야 할 경우 아래 코드 사용
    // sqlSessionFactoryBean.setDataSource(routingDataSource);
    sqlSessionFactoryBean.setDataSource(dataSource_oracle);
    
    // mybatis.xml 을 사용할 경우
    sqlSessionFactoryBean.setConfigLocation(
        new PathMatchingResourcePatternResolver().getResource(configFile));
    sqlSessionFactoryBean.setMapperLocations(
        new PathMatchingResourcePatternResolver().getResources(mapperLocation));
    // parameterType, resultType 에 java package 생략 가능
    // 단, BASE_PACKAGES 하위 package 에 동일한 클래스명이 2개 이상일 경우 어플리케이션 booting 시 오류가 발생함
    if (!ObjectUtils.isEmpty(typeAliasesPackage)) {
      sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
    }
    // 페이징 처리를 위한 mybatis-plugin 추가
    sqlSessionFactoryBean.setPlugins(new lxhausys.mybatis.config.mybatis.PagingInterceptor());
    return sqlSessionFactoryBean;
  }
  
  @Bean
  DaoSupport daoSupport_oracle(
      @Qualifier("sqlSessionTemplate_oracle") SqlSessionTemplate sqlSessionTemplate) throws Exception {
    return new DaoSupport(sqlSessionTemplate);
  }
  
  @Bean(name = "sqlSessionTemplate_oracle")
  SqlSessionTemplate sqlSessionTemplate_oracle(
      @Qualifier("sqlSessionFactoryBean_oracle") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
  }
  
  @Bean(name = "sqlSessionTemplateForBatchExecutor_oracle")
  SqlSessionTemplate sqlSessionTemplateForBatchExecutor_oracle(
      @Qualifier("sqlSessionFactoryBean_oracle") SqlSessionFactoryBean sqlSessionFactoryBean) throws Exception {
    return new SqlSessionTemplate(sqlSessionFactoryBean.getObject(), ExecutorType.BATCH);
  }
}

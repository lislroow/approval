package lxhausys.mybatis.api.datasource;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import lxhausys.mybatis.api.employ.dto.EmployPageREQ;
import lxhausys.mybatis.api.employ.dto.EmployVO;
import lxhausys.mybatis.config.mybatis.Pageable;
import lxhausys.mybatis.config.mybatis.PagedList;

@Mapper
public interface OracleMapper {
  
  List<EmployVO> selectAll();
  PagedList<EmployVO> selectList(Pageable param);
  PagedList<EmployVO> selectListByName(EmployPageREQ param);
  int insert(EmployVO param);
  EmployVO select(String id);
  int update(EmployVO param);
  int delete(EmployVO param);
  
}

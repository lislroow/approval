package lxhausys.kafka.api.mytopic;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import lxhausys.kafka.api.mytopic.dto.MyTopicVO;
import lxhausys.mybatis.config.mybatis.Pageable;
import lxhausys.mybatis.config.mybatis.PagedList;

@Mapper
public interface MyTopicMapper {
  
  int insert(Map<String, Object> param);
  List<MyTopicVO> selectAll();
  PagedList<MyTopicVO> selectList(Pageable param);
  
}

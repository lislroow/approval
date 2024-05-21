package lxhausys.kafka.api.mytopic;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lxhausys.kafka.api.mytopic.dto.MyTopicVO;
import lxhausys.mybatis.config.mybatis.Pageable;
import lxhausys.mybatis.config.mybatis.PagedList;

@Service
@Transactional(readOnly = true)
public class MyTopicService {
  
  private MyTopicMapper mapper;
  
  public MyTopicService(MyTopicMapper mapper) {
    this.mapper = mapper;
  }
  
  public List<MyTopicVO> selectAll() {
    List<MyTopicVO> list = null;
    list = mapper.selectAll();
    return list;
  }
  
  public PagedList<MyTopicVO> selectList(Pageable param) {
    return mapper.selectList(param);
  }
}

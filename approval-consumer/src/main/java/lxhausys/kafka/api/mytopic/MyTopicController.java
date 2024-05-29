package lxhausys.kafka.api.mytopic;

import java.util.List;

import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lxhausys.kafka.api.mytopic.dto.MyTopicVO;
import lxhausys.mybatis.config.mybatis.Pageable;
import lxhausys.mybatis.config.mybatis.PagedList;

@RestController
public class MyTopicController {

  private MyTopicService service;
  private KafkaListenerEndpointRegistry registry;
  
  public MyTopicController(MyTopicService service, KafkaListenerEndpointRegistry registry) {
    this.service = service;
    this.registry = registry;
  }
  
  // GET: 조회
  /*
    curl --location 'http://localhost:8080/api/mytopic/all'
  */
  @GetMapping("/api/mytopic/all")
  public List<MyTopicVO> selectAll() {
    List<MyTopicVO> res = service.selectAll();
    return res;
  }

  /*
    curl --location 'http://localhost:8080/api/mytopic/list?page=2&pageSize=3'
  */
  @GetMapping("/api/mytopic/list")
  public PagedList<MyTopicVO> selectList(Pageable param) {
    PagedList<MyTopicVO> res = service.selectList(param);
    return res;
  }
  
  

//POST: mytopic listener 실행
/*
curl -X POST http://localhost:8080/api/mytopic/start
*/
  @PostMapping("/api/mytopic/start")
  public void start() {
    MessageListenerContainer listener = this.registry.getListenerContainer("mytopicListener");
    listener.start();
  }
  
// POST: mytopic listener 중지
/*
curl -X POST http://localhost:8080/api/mytopic/stop
*/
  @PostMapping("/api/mytopic/stop")
  public void stop() {
    MessageListenerContainer listener = this.registry.getListenerContainer("mytopicListener");
    listener.stop();
  }
}

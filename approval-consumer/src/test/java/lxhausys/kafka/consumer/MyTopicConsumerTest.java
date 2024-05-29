package lxhausys.kafka.consumer;

import static org.assertj.core.api.Assertions.entry;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lxhausys.MainApplication;
import lxhausys.kafka.api.mytopic.MyTopicMapper;
import lxhausys.kafka.api.mytopic.dto.MyTopicVO;
import lxhausys.mybatis.util.Uuid;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MainApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyTopicConsumerTest {

  @Autowired
  private MyTopicConsumer consumer;
  
  @Autowired
  private MyTopicMapper mapper;
  
  @Test
  @Order(1)
  public void step1() {
    List<Map<String, Object>> list = null;
    list = mapper.selectAll();
    System.out.println("step1) select list="+list);
  }
  
  @Test
  @Order(2)
  public void step2() {
    Map<String, Object> data = Map.ofEntries(
        entry("id", Uuid.create()),
        entry("name", "scott"),
        entry("createDate", LocalDateTime.now()),
        entry("modifyDate", LocalDateTime.now()),
        entry("createId", "tester"),
        entry("modifyId", "tester")
        );
    Map<String, Object> param = Map.of("data", data);
    consumer.mytopicListener(param, null);
    System.out.println("step2) consumer.mytopicListener 호출. param="+param);
  }
  
  @Test
  @Order(3)
  public void step3() {
    List<Map<String, Object>> list = null;
    list = mapper.selectAll();
    System.out.println("step3) select list="+list);
  }
}

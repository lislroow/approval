package lxhausys.kafka.consumer;

import java.time.Duration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lxhausys.kafka.api.mytopic.MyTopicMapper;

@Service
public class MyTopicConsumer implements ConsumerSeekAware {
  
  private static final Logger log = LoggerFactory.getLogger(MyTopicConsumer.class);
  
  @Autowired
  private MyTopicMapper mapper;
  
//  private ConsumerSeekCallback seekCallback;
  
  @KafkaListener(topics = "mytopic", containerFactory = "kafkaListener")
  @Transactional
  public void mytopicListener(Map<String, Object> data, Acknowledgment ack) {
    log.info("data={}", data);
    
    if (data.get("data") instanceof java.util.Map) {
      Map<String, Object> param = (Map<String, Object>) data.get("data");
      try {
        mapper.insert(param);
        //int i = 1/0;
        ack.acknowledge();
      } catch (Exception e) {
        ack.nack(Duration.ofMillis(200));
        System.err.println(e.getMessage());
        throw e;
      }
    }
    
  }
  
//  @Override
//  public void registerSeekCallback(ConsumerSeekCallback callback) {
//    this.seekCallback = callback;
//  }
//  
//  @Override
//  public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
//    callback.seek("mytopic", 0, 1);
//  }
  
}

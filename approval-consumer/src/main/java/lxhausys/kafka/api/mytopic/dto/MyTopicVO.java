package lxhausys.kafka.api.mytopic.dto;

import lxhausys.mybatis.api.audit.dto.AuditVO;

public class MyTopicVO extends AuditVO {

  private String id;
  private String name;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}

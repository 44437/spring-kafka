package com.u44437.spring_kafka.model;

import lombok.Data;

@Data
public class MessageReq {
  public String main;
  public String comment;

  @Override
  public String toString() {
    return "MessageReq{" +
            "main='" + main + '\'' +
            ", comment='" + comment + '\'' +
            '}';
  }

  public String toJSONString() {
    return String.format("{\"main\":\"%s\", \"comment\":\"%s\"}", main, comment);
  }
}

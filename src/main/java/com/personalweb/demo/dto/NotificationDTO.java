package com.personalweb.demo.dto;

import com.personalweb.demo.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private Long outerId;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer type;
}

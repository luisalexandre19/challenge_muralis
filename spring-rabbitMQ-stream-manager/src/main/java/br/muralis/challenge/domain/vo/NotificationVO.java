package br.muralis.challenge.domain.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationVO {

    private String message;

    private Object value;

}

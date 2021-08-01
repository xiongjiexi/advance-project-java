package cn.xiongjiexi.mq.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class JMessage<T> {
    private Map<String, String> headers;

    private T body;
}

package cn.jessexiong.distribution.cache.model;

import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateRequest {

    private List<String> orderIds;

    private String amount;

}

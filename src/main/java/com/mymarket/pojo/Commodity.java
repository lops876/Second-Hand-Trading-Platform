package com.mymarket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {
    private Integer id;
    private String name;
    private String description;
    private Integer publisher;
    private String type;
    private BigDecimal price;
    private Integer stock;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;

}

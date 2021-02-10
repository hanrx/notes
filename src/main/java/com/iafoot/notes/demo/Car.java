package com.iafoot.notes.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author iAfoot
 * @Create 20210210 13:05
 * @Version 1.0.0
 */
@Slf4j//引入log4j
@Data//引入getter、setter方法
//@AllArgsConstructor//引入全参构造器
@NoArgsConstructor//引入无参构造数
@ToString//引入toString方法
@EqualsAndHashCode //根据属性重新equals方法
public class Car {
    private Long id;
    private String name;
}

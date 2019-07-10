package com;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 赵贺飞
 * @Date: 2018/11/30 18:06
 */
public class main {

    public static void main(String[] args){
        List<String> dogs = new ArrayList<String>();
        dogs.add("Aigi");
        dogs.add("Spitz");
        dogs.add("Mastiff");
        dogs.add("Finnish Spitz");
        System.err.println(StringUtils.join(dogs, ","));
    }
}

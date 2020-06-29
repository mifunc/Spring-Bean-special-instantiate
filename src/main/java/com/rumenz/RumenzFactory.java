package com.rumenz;

public interface RumenzFactory {
    //jdk1.8 默认实现
    default   RumenzA rumenzFactory(){
        RumenzA rumenzA = new RumenzA();
        rumenzA.setId("123");
        rumenzA.setName("入门小站");
        return rumenzA;
    }
    default  RumenzA rumenzFactory(String id){
        return new RumenzA(id);
    }
}

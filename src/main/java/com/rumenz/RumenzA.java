package com.rumenz;

public class RumenzA {

    private String id;
    private String name;

    public  RumenzA() {
        System.out.println("RumenzA 无参构造方法");
    }


    public static RumenzA createRumenzA(){
        RumenzA rumenzA=new RumenzA();
        rumenzA.setId("123");
        rumenzA.setName("入门小站");
        return rumenzA;
    }

    public RumenzA(String id) {
        this.id = id;
        System.out.println("ID构造方法");
    }

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

    @Override
    public String toString() {
        return "RumenzA{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }


}

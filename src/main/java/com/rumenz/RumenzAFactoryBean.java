package com.rumenz;

import org.springframework.beans.factory.FactoryBean;

public class RumenzAFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return RumenzA.createRumenzA();
    }

    @Override
    public Class<?> getObjectType() {
        return RumenzA.class;
    }
}

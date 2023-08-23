package com.agrhub.app.smart_retail.repositories;


import com.agrhub.app.smart_retail.models.BaseEntity;

import java.util.List;

public interface BaseService<V extends BaseEntity> {

    Long createEntity(V entity);

    V readEntity(Long id);

    void updateEntity(V entity);

    void deleteEntity(Long id);

    List<V> listEntities(String startCursor);
}

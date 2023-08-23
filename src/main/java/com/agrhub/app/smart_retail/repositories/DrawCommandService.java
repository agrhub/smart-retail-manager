package com.agrhub.app.smart_retail.repositories;

import com.agrhub.app.smart_retail.models.BaseEntity;
import com.agrhub.app.smart_retail.models.DrawCommandEntity;
import com.google.appengine.api.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrawCommandService implements BaseService<DrawCommandEntity> {

    private final static String KIND = "DrawCommands";

    @Autowired
    DatastoreService datastore;

    @Override
    public Long createEntity(DrawCommandEntity entity) {
        Entity data = new Entity(KIND);
        data = entity.getEntity(data);
        Key k = datastore.put(data);
        return k.getId();
    }

    @Override
    public DrawCommandEntity readEntity(Long id) {
        Entity data = null;
        try {
            data = datastore.get(KeyFactory.createKey(KIND, id));
        } catch (EntityNotFoundException e) {
            return null;
        }
        return new DrawCommandEntity(data);
    }

    @Override
    public void updateEntity(DrawCommandEntity entity) {
        Key key = KeyFactory.createKey(KIND, entity.getId());
        Entity data = new Entity(key);
        data = entity.getEntity(data);
        datastore.put(data);
    }

    @Override
    public void deleteEntity(Long id) {
        Entity data = null;
        try {
            data = datastore.get(KeyFactory.createKey(KIND, id));

            data.setIndexedProperty(BaseEntity.IS_DELETED, true);
            datastore.put(data);
        } catch (EntityNotFoundException e) {
        }
    }

    public List<DrawCommandEntity> entitiesToTasks(List<Entity> resultList) {
        List<DrawCommandEntity> resultTasks = new ArrayList<>();
        for (Entity entity : resultList) {
            resultTasks.add(new DrawCommandEntity(entity));
        }
        return resultTasks;
    }

    @Override
    public List<DrawCommandEntity> listEntities(String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString));
        }

        Query.Filter notDeleted =
                new Query.FilterPredicate(BaseEntity.IS_DELETED, Query.FilterOperator.EQUAL, Boolean.valueOf(false));

        Query query = new Query(KIND)
                .setFilter(notDeleted)
                .addSort(DrawCommandEntity.PRIORITY, Query.SortDirection.ASCENDING);
        PreparedQuery preparedQuery = datastore.prepare(query);
        List<Entity> results = preparedQuery.asList(fetchOptions);

        return entitiesToTasks(results);
    }

    public List<DrawCommandEntity> listEntitiesWithDeviceType(Long deviceType) {
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        Query.Filter notDeleted =
                new Query.FilterPredicate(BaseEntity.IS_DELETED, Query.FilterOperator.EQUAL, false);
        Query.Filter deviceTypeCondition = new Query.FilterPredicate(DrawCommandEntity.DEVICE_TYPE_ID, Query.FilterOperator.EQUAL, deviceType);
        Query.Filter allCondition = Query.CompositeFilterOperator.and(deviceTypeCondition, notDeleted);

        Query query = new Query(KIND)
                .setFilter(allCondition)
                .addSort(DrawCommandEntity.PRIORITY, Query.SortDirection.ASCENDING);
        PreparedQuery preparedQuery = datastore.prepare(query);
        List<Entity> results = preparedQuery.asList(fetchOptions);

        return entitiesToTasks(results);
    }
}

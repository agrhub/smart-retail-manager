package com.agrhub.app.smart_retail.repositories;

import com.agrhub.app.smart_retail.models.BaseEntity;
import com.agrhub.app.smart_retail.models.DeviceTypeEntity;
import com.google.appengine.api.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DeviceTypeService implements BaseService<DeviceTypeEntity> {

    private final static String KIND = "DeviceTypes";

    @Autowired
    DatastoreService datastore;

    @Override
    public Long createEntity(DeviceTypeEntity entity) {
        Entity data = new Entity(KIND);
        data = entity.getEntity(data);
        Key k = datastore.put(data);
        return k.getId();
    }

    @Override
    public DeviceTypeEntity readEntity(Long id) {
        Entity data = null;
        try {
            data = datastore.get(KeyFactory.createKey(KIND, id));
        } catch (EntityNotFoundException e) {
            return null;
        }
        return new DeviceTypeEntity(data);
    }

    @Override
    public void updateEntity(DeviceTypeEntity entity) {
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

    public List<DeviceTypeEntity> entitiesToTasks(Iterator<Entity> resultList) {
        List<DeviceTypeEntity> resultTasks = new ArrayList<>();
        while (resultList.hasNext()) {
            resultTasks.add(new DeviceTypeEntity(resultList.next()));
        }
        return resultTasks;
    }

    @Override
    public List<DeviceTypeEntity> listEntities(String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString));
        }

        Query.Filter notDeleted =
                new Query.FilterPredicate(BaseEntity.IS_DELETED, Query.FilterOperator.EQUAL, false);

        Query query = new Query(KIND)
                .setFilter(notDeleted)
                .addSort(BaseEntity.CREATE_AT, Query.SortDirection.ASCENDING);
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<DeviceTypeEntity> resultBooks = entitiesToTasks(results);
        Cursor cursor = results.getCursor();
        if (cursor != null && resultBooks.size() == 10) {
            return resultBooks;
        } else {
            return resultBooks;
        }
    }
}

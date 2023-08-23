package com.agrhub.app.smart_retail.repositories;

import com.agrhub.app.smart_retail.models.BaseEntity;
import com.agrhub.app.smart_retail.models.DeviceEntity;
import com.google.appengine.api.datastore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class DeviceService implements BaseService<DeviceEntity> {

    private final static String KIND = "Devices";

    @Autowired
    com.google.appengine.api.datastore.DatastoreService datastore;

    @Override
    public Long createEntity(DeviceEntity entity) {
        Entity data = new Entity(KIND);
        data = entity.getEntity(data);
        Key k = datastore.put(data);
        return k.getId();
    }

    @Override
    public DeviceEntity readEntity(Long id) {
        Entity data = null;
        try {
            data = datastore.get(KeyFactory.createKey(KIND, id));
        } catch (EntityNotFoundException e) {
            return null;
        }
        return new DeviceEntity(data);
    }

    @Override
    public void updateEntity(DeviceEntity entity) {
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

    public List<DeviceEntity> entitiesToTasks(Iterator<Entity> resultList) {
        List<DeviceEntity> resultTasks = new ArrayList<>();
        while (resultList.hasNext()) {
            resultTasks.add(new DeviceEntity(resultList.next()));
        }
        return resultTasks;
    }

    @Override
    public List<DeviceEntity> listEntities(String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10);
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

        List<DeviceEntity> resultBooks = entitiesToTasks(results);
        Cursor cursor = results.getCursor();
        if (cursor != null && resultBooks.size() == 10) {
            return resultBooks;
        } else {
            return resultBooks;
        }
    }

    public DeviceEntity getDeviceByMacAddress(String macAddress) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1);

        Query.Filter condition =
                new Query.FilterPredicate(DeviceEntity.MAC_ADDRESS, Query.FilterOperator.EQUAL, macAddress);

        Query query = new Query(KIND)
                .setFilter(condition);
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        DeviceEntity returnVal = null;
        if (results != null) {
            while (results.hasNext()) {
                returnVal = new DeviceEntity(results.next());
                break;
            }
        }
        return returnVal;
    }

    public List<DeviceEntity> getDeviceWithProduct(Long productId) {
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        Query.Filter notDeleted =
                new Query.FilterPredicate(BaseEntity.IS_DELETED, Query.FilterOperator.EQUAL, false);
        Query.Filter productCondition = new Query.FilterPredicate(DeviceEntity.PRODUCT_ID, Query.FilterOperator.EQUAL, productId);
        Query.Filter allCondition = Query.CompositeFilterOperator.and(productCondition, notDeleted);

        Query query = new Query(KIND)
                .setFilter(allCondition)
                .addSort(BaseEntity.CREATE_AT, Query.SortDirection.ASCENDING);
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<DeviceEntity> resultBooks = entitiesToTasks(results);
        Cursor cursor = results.getCursor();
        if (cursor != null && resultBooks.size() == 10) {
            return resultBooks;
        } else {
            return resultBooks;
        }
    }
}

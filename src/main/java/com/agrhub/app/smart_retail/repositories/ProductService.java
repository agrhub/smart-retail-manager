package com.agrhub.app.smart_retail.repositories;

import com.agrhub.app.smart_retail.models.BaseEntity;
import com.agrhub.app.smart_retail.models.ProductEntity;
import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ProductService implements BaseService<ProductEntity> {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final static String KIND = "Products";

    @Autowired
    com.google.appengine.api.datastore.DatastoreService datastore;

    @Override
    public Long createEntity(ProductEntity entity) {
        Entity data = new Entity(KIND);
        data = entity.getEntity(data);
        Key k = datastore.put(data);
        return k.getId();
    }

    @Override
    public ProductEntity readEntity(Long id) {
        Entity data = null;
        try {
            data = datastore.get(KeyFactory.createKey(KIND, id));
        } catch (EntityNotFoundException e) {
            return null;
        }
        ProductEntity returnVal = new ProductEntity(data, this);
        return returnVal;
    }

    @Override
    public void updateEntity(ProductEntity entity) {
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

    public List<ProductEntity> entitiesToTasks(Iterator<Entity> resultList) {
        List<ProductEntity> resultTasks = new ArrayList<>();
        while (resultList.hasNext()) {
            resultTasks.add(new ProductEntity(resultList.next(), this));
        }
        return resultTasks;
    }

    @Override
    public List<ProductEntity> listEntities(String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString));
        }

        return filter(null, fetchOptions);
    }

    public List<ProductEntity> productOfCategory(Long categoryId) {
        return filter(categoryId, null);
    }

    private List<ProductEntity> filter(Long categoryId, FetchOptions fetchOptions) {
        if (fetchOptions == null) {
            fetchOptions = FetchOptions.Builder.withDefaults();
        }

        Query query = null;

        Query.Filter notDeleted = new Query.FilterPredicate(BaseEntity.IS_DELETED, Query.FilterOperator.EQUAL, false);

        if (categoryId == null) {
            query = new Query(KIND)
                    .setFilter(notDeleted)
                    .addSort(ProductEntity.NAME, Query.SortDirection.ASCENDING);
        } else {
            Query.Filter categoryCondition = new Query.FilterPredicate(ProductEntity.CATEGORY_ID, Query.FilterOperator.EQUAL, categoryId);
            Query.Filter allCondition = Query.CompositeFilterOperator.and(categoryCondition, notDeleted);

            query = new Query(KIND)
                    .setFilter(allCondition)
                    .addSort(ProductEntity.NAME, Query.SortDirection.ASCENDING);
        }

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<ProductEntity> resultBooks = entitiesToTasks(results);
        return resultBooks;
    }

    public List<ProductEntity> getPromotion() {
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();

        Query query = null;

        Query.Filter notDeleted = new Query.FilterPredicate(BaseEntity.IS_DELETED, Query.FilterOperator.EQUAL, false);
        Query.Filter promotionCondition = new Query.FilterPredicate(ProductEntity.DISCOUNT, Query.FilterOperator.GREATER_THAN, 0);
        Query.Filter allCondition = Query.CompositeFilterOperator.and(promotionCondition, notDeleted);

        query = new Query(KIND)
                .setFilter(allCondition)
                .addSort(ProductEntity.DISCOUNT, Query.SortDirection.DESCENDING);

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<ProductEntity> resultBooks = entitiesToTasks(results);
        return resultBooks;
    }

    public List<ProductEntity> getProductsWithIds(String ids) {
        if (ids == null || ids.isEmpty()) {
            return null;
        }
        Gson gson = new Gson();

        List<Long> idsObj = gson.fromJson(ids, new TypeToken<List<Long>>(){}.getType());
        if (idsObj == null || idsObj.isEmpty()) {
            return null;
        }
        List<Key> keys = new ArrayList<>();
        for (Long id : idsObj) {
            keys.add(KeyFactory.createKey(KIND, id));
        }
        Map<Key, Entity> result = datastore.get(keys);
        List<ProductEntity> returnVal = new ArrayList<>();
        for (Map.Entry<Key, Entity> entry : result.entrySet()) {
            returnVal.add(new ProductEntity(entry.getValue(), this));
        }

        return returnVal;
    }
}

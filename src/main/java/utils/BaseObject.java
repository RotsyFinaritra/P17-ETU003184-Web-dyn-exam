package main.java.utils;

import java.util.List;

public abstract class BaseObject {
    int id;
    String tableName;
    public BaseObject(int id, String tableName) {
        this.id = id;
        this.tableName = tableName;
    }

    public abstract void save() throws Exception;

    public abstract BaseObject findById(int id) throws Exception;

    public abstract List<BaseObject> findAll() throws Exception;

    public abstract List<BaseObject> findAllWithPagination(int index, int count) throws Exception;

    public abstract void delete() throws Exception;

    public abstract void update() throws Exception;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    
}

package main.java.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GenericClassOO extends BaseObject {

    public GenericClassOO(int id, String tableName) {
        super(id, tableName);
    }

    @Override
    public void delete() throws Exception {
        Class<?> class1 = this.getClass();
        String tableName = this.getTableName();
        String sql = "DELETE FROM " + tableName + " WHERE ";
        Field[] fields = class1.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (i < fields.length - 1) {
                sql += field.getName() + " = ? AND ";
            } else {
                sql += field.getName() + " = ?";
            }
        }
        try (Connection connection = DB.connect();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int i = 1;
            for (Field field : fields) {
                field.setAccessible(true);
                preparedStatement.setObject(i, field.get(this));
                i++;
            }
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<BaseObject> findAll() throws Exception {
        Class<?> class1 = this.getClass();
        String tableName = this.getTableName();
        String sql = "SELECT * FROM " + tableName;
        List<BaseObject> results = new ArrayList<>();

        try (Connection connection = DB.connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            Constructor<?> constructor = null;

            Class<?>[] paramTypes = new Class<?>[columnCount];
            for (int i = 0; i < columnCount; i++) {
                String columnClass = metaData.getColumnClassName(i + 1);

                if (columnClass.equals("java.lang.Integer")) {
                    System.out.println(columnClass);
                    paramTypes[i] = int.class;
                } else if (columnClass.equals("java.math.BigDecimal")) {

                    paramTypes[i] = double.class;
                } else {
                    System.out.println(columnClass);
                    paramTypes[i] = Class.forName(columnClass);
                }
            }

            try {
                constructor = class1.getDeclaredConstructor(paramTypes);
            } catch (NoSuchMethodException e) {
                throw new Exception("Aucun constructeur valide trouvé pour " + class1.getSimpleName(), e);
            }

            while (resultSet.next()) {
                List<Object> values = new ArrayList<>();
                for (int i = 0; i < columnCount; i++) {
                    Object value = resultSet.getObject(i + 1);
                    if (value.getClass().equals(java.math.BigDecimal.class)) {
                        value = Double.parseDouble(value.toString());
                    }
                    values.add(value);
                }

                Object o = constructor.newInstance(values.toArray());
                results.add((BaseObject) o);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la récupération des données.", e);
        }
        return results;
    }

    @Override
    public List<BaseObject> findAllWithPagination(int index, int count) throws Exception {
        return null;
    }

    @Override
    public BaseObject findById(int id) throws Exception {
        BaseObject result = null;
        Class<?> class1 = this.getClass();
        String tableName = this.getTableName();
        // Field[] fields = class1.getDeclaredFields();
        // Field idField = null;
        // for (Field field : fields) {
        // System.out.println(field.getName());
        // if (field.getName().equals("id")) {
        // idField = field;
        // break;
        // }
        // }

        String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
        try (
                Connection connection = DB.connect();) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            Constructor<?> constructor = null;

            Class<?>[] paramTypes = new Class<?>[columnCount];
            for (int i = 0; i < columnCount; i++) {
                String columnClass = metaData.getColumnClassName(i + 1);
                System.out.println(columnClass);
                if (columnClass.equals("java.lang.Integer")) {
                    paramTypes[i] = int.class;
                } else if (columnClass.equals("java.lang.Boolean")) {
                    paramTypes[i] = boolean.class;
                } else if (columnClass.equals("java.lang.Double") || columnClass.equals("java.math.BigDecimal")) {
                    paramTypes[i] = double.class;
                } else if (columnClass.equals("java.sql.Date")) {
                    paramTypes[i] = java.sql.Date.class;
                } else if (columnClass.equals("java.lang.String")) {
                    paramTypes[i] = String.class;
                } else {
                    paramTypes[i] = Class.forName(columnClass);
                }
            }

            try {
                constructor = class1.getDeclaredConstructor(paramTypes);
            } catch (NoSuchMethodException e) {
                throw new Exception("Aucun constructeur valide trouvé pour " + class1.getSimpleName(), e);
            }

            if (resultSet.next()) {
                List<Object> values = new ArrayList<>();
                for (int i = 0; i < columnCount; i++) {
                    Object value = null;
                    if (paramTypes[i].getName().equals("java.lang.Integer")) {
                        value = resultSet.getInt(i + 1);
                    } else if (paramTypes[i].equals(boolean.class)) {
                        value = resultSet.getBoolean(i + 1);
                    } else if (paramTypes[i].equals(double.class)
                            || paramTypes[i].equals(BigDecimal.class)) {
                        value = resultSet.getDouble(i + 1);
                    } else if (paramTypes[i].equals(Date.class)) {
                        value = resultSet.getDate(i + 1);
                    } else if (paramTypes[i].equals(String.class)) {
                        value = resultSet.getString(i + 1);
                    } else {
                        value = resultSet.getObject(i + 1);
                    }
                    values.add(value);
                }
                System.out.println(values);
                for (Object valObject : values) {
                    System.out.println(valObject.getClass());
                }
                result = (BaseObject) constructor.newInstance(values.toArray());
            }
        } catch (Exception e) {
            throw new Exception("Misy erreur " + e);
        }

        return result;
    }

    @Override
    public void save() throws Exception {
        Class<?> class1 = this.getClass();
        Field[] fields = class1.getDeclaredFields();

        String sql = "INSERT INTO " + this.getTableName() + "(";
        for (Field field : fields) {
            sql += field.getName() + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ") VALUES (";
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(Integer.class) || field.getType().equals(Double.class)) {
                sql += field.get(this) + ",";
            } else {
                sql += "'" + field.get(this) + "',";
            }
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ");";
        System.out.println(sql);

        try (Connection connection = DB.connect();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Success insertion");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    public void save(Connection connection) throws Exception {
        Class<?> class1 = this.getClass();
        Field[] fields = class1.getDeclaredFields();

        String sql = "INSERT INTO " + this.getTableName() + "(";
        for (Field field : fields) {
            sql += field.getName() + ",";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ") VALUES (";
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getType().equals(Integer.class) || field.getType().equals(Double.class)) {
                sql += field.get(this) + ",";
            } else {
                sql += "'" + field.get(this) + "',";
            }
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += ");";
        System.out.println(sql);

        try (
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Success insertion");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }

    }

    @Override
    public void update() throws Exception {
        Class<?> class1 = this.getClass();
        Field[] fields = class1.getDeclaredFields();

        String sql = "UPDATE " + this.getTableName() + " SET ";
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id") || field.getName().equals("id" + class1.getSimpleName())) {
                continue;
            }
            sql += field.getName() + " = '" + field.get(this) + "',";
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " WHERE id = " + this.getId() + ";";
        System.out.println(sql);

        try (Connection connection = DB.connect();
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Success update");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public void update(Connection connection) throws Exception {
        Class<?> class1 = this.getClass();
        Field[] fields = class1.getDeclaredFields();

        String sql = "UPDATE " + this.getTableName() + " SET ";
        String idColName = "id";
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id") || field.getName().equals("id" + class1.getSimpleName())) {
                idColName = "id" + class1.getSimpleName();
                continue;
            }
            if (field.getType().equals(boolean.class) || field.getClass().equals(int.class)
                    || field.getClass().equals(double.class)) {
                sql += field.getName() + " = " + field.get(this) + ",";
            } else {
                sql += field.getName() + " = '" + field.get(this) + "',";
            }
        }
        sql = sql.substring(0, sql.length() - 1);
        sql += " WHERE " + idColName + " = " + this.getId() + ";";
        System.out.println(sql);

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Success update");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}

package org.guigo.hbte.mapper;

import org.apache.flink.types.Row;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerPojoToRow<T> {

    private List<Field> fields;

    public CustomerPojoToRow(Class<T> clazz) {
        fields = initAllFields(new ArrayList<>(), clazz);
    }

    public List<Field> initAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            initAllFields(fields, type.getSuperclass());
        }
        return fields;
    }


    public Row toRow(T input) {
        Row row = new Row(fields.size());
        for (int i = 0; i < fields.size(); ++i) {
            Field field = fields.get(i);
            field.setAccessible(true);
            try {
                row.setField(i, field.get(input));
            } catch (Exception e) {
                throw new RuntimeException("this is a bug");
            }
        }
        return row;
    }
}

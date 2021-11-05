package org.guigo.hbte.mapper;

import org.apache.flink.types.Row;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CustomerRowOperationMapper extends CustomerAbstractOperationMapper<Row> {

    public CustomerRowOperationMapper(Class<?> clazz) {
        allColumnNames = initAllColumnNames(clazz);
    }

    protected String[] initAllColumnNames(Class clazz) {
        List<Field> fields = new ArrayList<>();
        getAllFields(fields, clazz);
        String[] allColumnNames = new String[fields.size()];
        for (int i = 0; i < allColumnNames.length; i++) {
            allColumnNames[i] = fields.get(i).getName().toLowerCase(Locale.ROOT);
        }
        return allColumnNames;
    }

    @Override
    public Object getField(Row input, int pos) {
        return input.getField(pos);
    }
}

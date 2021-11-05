package org.guigo.hbte.mapper;

import org.apache.kudu.shaded.com.google.common.base.Strings;
import org.guigo.hbte.annotation.TableKuduColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerAnnotationOperationMapper<T> extends CustomerAbstractOperationMapper<T> {

    private final Logger log = LoggerFactory.getLogger(CustomerAnnotationOperationMapper.class);

    private transient static final List<Field> fields = new ArrayList<>();

    public CustomerAnnotationOperationMapper(Class<T> clazz) {
        initFields(clazz);
        allColumnNames = initAllColumnNames(clazz);
    }

    private void initFields(Class<T> clazz) {
        List<Field> allFields = getAllFields(new ArrayList<>(), clazz);
        for (Field field : allFields) {
            TableKuduColumn columnName = field.getAnnotation(TableKuduColumn.class);
            if (Objects.nonNull(columnName)) {
                field.setAccessible(true);
                fields.add(field);
            }
        }
    }

    private String[] initAnnotationColumns(Class<T> clazz) {
        List<String> columns = new ArrayList<>();
        for (Field field : fields) {
            TableKuduColumn columnName = field.getAnnotation(TableKuduColumn.class);
            if (Strings.isNullOrEmpty(columnName.value())) {
                columns.add(field.getName());
            } else {
                columns.add(columnName.value());
            }
        }
        String[] fieldColumns = new String[columns.size()];
        columns.toArray(fieldColumns);
        return fieldColumns;
    }


    @Override
    protected String[] initAllColumnNames(Class<T> clazz) {
        return initAnnotationColumns(clazz);
    }

    public Object getField(T input, int pos) {
        try {
            return fields.get(pos).get(input);
        } catch (IllegalAccessException e) {
            log.error(e.getLocalizedMessage());
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}

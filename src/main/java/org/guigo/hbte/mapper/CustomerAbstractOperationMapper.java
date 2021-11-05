package org.guigo.hbte.mapper;

import org.apache.kudu.client.KuduTable;
import org.apache.kudu.client.Operation;
import org.apache.kudu.client.PartialRow;
import org.colloh.flink.kudu.connector.internal.writer.KuduOperationMapper;

import java.lang.reflect.Field;
import java.util.*;

public abstract class CustomerAbstractOperationMapper<T> implements KuduOperationMapper<T> {

    private final CustomerAbstractOperationMapper.KuduOperation operation;
    private final static transient String defaultEmpty = "";

    protected String[] allColumnNames;

    protected CustomerAbstractOperationMapper() {
        this(CustomerAbstractOperationMapper.KuduOperation.INSERT);
    }

    protected CustomerAbstractOperationMapper(CustomerAbstractOperationMapper.KuduOperation operation) {
        this.operation = operation;
    }

    protected abstract String[] initAllColumnNames(Class<T> clazz);

    protected List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    public abstract Object getField(T input, int pos);

    public Optional<Operation> createBaseOperation(T input, KuduTable table) {
        if (this.operation == null) {
            throw new UnsupportedOperationException("createBaseOperation must be overridden if no operation specified in constructor");
        } else {
            switch (this.operation) {
                case INSERT:
                    return Optional.of(table.newInsert());
                case UPDATE:
                    return Optional.of(table.newUpdate());
                case UPSERT:
                    return Optional.of(table.newUpsert());
                case DELETE:
                    return Optional.of(table.newDelete());
                default:
                    throw new RuntimeException("Unknown operation " + this.operation);
            }
        }
    }

    public List<Operation> createOperations(T input, KuduTable table) {
        Optional<Operation> operationOpt = this.createBaseOperation(input, table);
        if (!operationOpt.isPresent()) {
            return Collections.emptyList();
        } else {
            Operation operation = operationOpt.get();
            PartialRow partialRow = operation.getRow();

            for (int i = 0; i < this.allColumnNames.length; ++i) {
                Object field = this.getField(input, i);
                partialRow.addObject(this.allColumnNames[i], Objects.nonNull(field) ? field : defaultEmpty);
            }

            return Collections.singletonList(operation);
        }
    }

    public enum KuduOperation {
        INSERT,
        UPDATE,
        UPSERT,
        DELETE;

        KuduOperation() {
        }
    }
}

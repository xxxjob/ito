package org.guigo.hbte.operation;

public abstract class OperationFactory {

    OperationFactory() {
    }

    public static Operation newOperation(String operator) {
        switch (operator) {
            case "<":
                return new CompareLessThan();
            case "<=":
                return new CompareLessThanEqual();
            case ">":
                return new CompareGreaterThan();
            case ">=":
                return new CompareGreaterThanEqual();
            case "=":
                return new CompareEqual();
            default:
                throw new RuntimeException("Unknown operator [" + operator + "] Example : < <= > >= =");
        }
    }
}

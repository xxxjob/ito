package org.guigo.hbte.operation;

public class CompareGreaterThanEqual implements Operation {
    @Override
    public boolean operator(double v1, double v2) {
        return v1 >= v2;
    }
}

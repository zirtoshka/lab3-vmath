package org.example;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MethodManager {
    private Map<Integer, Function<Function<BigDecimal, BigDecimal>, BigDecimal>> methodMap = new HashMap<>();
    private int numberOfSplits = 4;
    private BigDecimal leftBorder;
    private BigDecimal rightBorder;
    private BigDecimal accuracy;
    private Function<BigDecimal, BigDecimal> function;

    private int methodNumber;

    public MethodManager() {
        this.methodMap.put(1, this::rightRectangleMethod);
        this.methodMap.put(2, this::leftRectangleMethod);
        this.methodMap.put(3, this::mediumRectangleMethod);
        this.methodMap.put(4, this::trapezoidMethod);
        this.methodMap.put(5, this::simpsonMethod);
    }

    public Map<Integer, Function<Function<BigDecimal, BigDecimal>, BigDecimal>> getMethodMap() {
        return this.methodMap;
    }

    public void runMethod() {
        int n = numberOfSplits;

        BigDecimal res1 = methodMap.get(methodNumber).apply(function);
        numberOfSplits *= 2;
        BigDecimal res2 = methodMap.get(methodNumber).apply(function);
        while (res2.subtract(res1).abs().compareTo(accuracy) > 0) {
            numberOfSplits *= 2;
            res1 = res2;
            res2 = methodMap.get(methodNumber).apply(function);
        }
        System.out.println("Значение интеграла: " + res2);
        System.out.println("Количество разбиений для достижения точности: " + numberOfSplits);
        numberOfSplits = n;
    }

    private BigDecimal rightRectangleMethod(Function<BigDecimal, BigDecimal> function) {
        BigDecimal res = BigDecimal.ZERO;
        BigDecimal h = rightBorder.subtract(leftBorder).divide(BigDecimal.valueOf(numberOfSplits), MathContext.DECIMAL32);
        BigDecimal x = leftBorder;
        for (int i = 1; i <= numberOfSplits; i++) {
            x = x.add(h);
            res = res.add(function.apply(x));
        }
        res = res.multiply(h);
        System.out.println("Это правые треугольники: " + res);
        return res;
    }

    private BigDecimal leftRectangleMethod(Function<BigDecimal, BigDecimal> function) {
        BigDecimal res = BigDecimal.ZERO;
        BigDecimal h = rightBorder.subtract(leftBorder).divide(BigDecimal.valueOf(numberOfSplits), MathContext.DECIMAL32);
        BigDecimal x = leftBorder;
        for (int i = 1; i <= numberOfSplits; i++) {
            res = res.add(function.apply(x));
            x = x.add(h);

        }
        res = res.multiply(h);
        System.out.println("Это левые треугольники " + res);
        return res;
    }

    private BigDecimal mediumRectangleMethod(Function<BigDecimal, BigDecimal> function) {
        BigDecimal res = BigDecimal.ZERO;
        BigDecimal h = rightBorder.subtract(leftBorder).divide(BigDecimal.valueOf(numberOfSplits), MathContext.DECIMAL32);
        BigDecimal hHalf = h.divide(BigDecimal.valueOf(2), MathContext.DECIMAL32);
        BigDecimal x = leftBorder.add(hHalf);
        for (int i = 1; i <= numberOfSplits; i++) {
            res = res.add(function.apply(x));
            x = x.add(h);
        }
        res = res.multiply(h);
        System.out.println("Это средние треугольники " + res);
        return res;
    }


    private BigDecimal trapezoidMethod(Function<BigDecimal, BigDecimal> function) {
        BigDecimal res = function.apply(leftBorder).add(function.apply(rightBorder)).divide(BigDecimal.valueOf(2), MathContext.DECIMAL32);
        BigDecimal h = rightBorder.subtract(leftBorder).divide(BigDecimal.valueOf(numberOfSplits), MathContext.DECIMAL32);
        BigDecimal x = leftBorder;
        for (int i = 1; i < numberOfSplits; i++) {
            x = x.add(h);
            res = res.add(function.apply(x));
        }
        res = res.multiply(h);

        System.out.println("Это трапеции " + res);
        return res;
    }

    private BigDecimal simpsonMethod(Function<BigDecimal, BigDecimal> function) {
        BigDecimal res = function.apply(leftBorder).add(function.apply(rightBorder));
        BigDecimal h = rightBorder.subtract(leftBorder).divide(BigDecimal.valueOf(numberOfSplits), MathContext.DECIMAL32);
        BigDecimal x = leftBorder;
        for (int i = 1; i < numberOfSplits; i++) {
            x = x.add(h);
            if (i % 2 == 0) {
                res = res.add(function.apply(x).multiply(BigDecimal.valueOf(2)));
            } else {
                res = res.add(function.apply(x).multiply(BigDecimal.valueOf(4)));
            }

        }
        res = res.multiply(h.divide(BigDecimal.valueOf(3), MathContext.DECIMAL32));

        System.out.println("Это Симпсон " + res);

        return res;
    }

    public void setLeftBorder(BigDecimal leftBorder) {
        this.leftBorder = leftBorder;
    }

    public void setRightBorder(BigDecimal rightBorder) {
        this.rightBorder = rightBorder;
    }

    public void setAccuracy(BigDecimal accuracy) {
        this.accuracy = accuracy;
    }

    public void setMethodNumber(int methodNumber) {
        this.methodNumber = methodNumber;
    }

    public void setFunction(Function<BigDecimal, BigDecimal> function) {
        this.function = function;
    }

    public String getNamesMethods() {
        return "1: метод правых прямоугольников\n" +
                "2: метод левых прямоугольников\n" +
                "3: метод срдних прямоугольников\n" +
                "4: метод трапеций\n" +
                "5: метод Симпсона";

    }
}

package org.example;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class FunctionHandler {
    private HashMap<Integer, String> functions = new HashMap<>();
    private Map<Integer, Function<BigDecimal, BigDecimal>> functionMap = new HashMap<>();
    private HashMap<Integer, BigDecimal> breakPoints =new HashMap<>();

    public FunctionHandler() {
        this.functions.put(1, "-2x^3 -4x^2 +8x -4");
        this.functionMap.put(1, this::getFirstFuncValue);

        this.functions.put(2, "1/x");
        this.functionMap.put(2, this::getSecondFuncValue);
        breakPoints.put(2,BigDecimal.ZERO);

        this.functions.put(3,"1/(x+2)^4");
        this.functionMap.put(3, this::getThirdFuncValue);
        breakPoints.put(3,BigDecimal.valueOf(-2));

        this.functions.put(4,"1/(x-1)^(1/3)");
        this.functionMap.put(4, this::getForthFuncValue);
        breakPoints.put(4,BigDecimal.ONE);

        this.functions.put(5,"5x+3");
        this.functionMap.put(5, this::getFifthFuncValue);

        this.functions.put(6,"sin(3x-1)/(x-1)");
        this.functionMap.put(6, this::getSixthFuncValue);
        breakPoints.put(6,BigDecimal.ONE);


    }



    public String getFunctions() {
        StringBuilder stringBuilder=new StringBuilder();
        for (Integer i : functions.keySet()
        ) {
            stringBuilder.append(i+": "+functions.get(i)+"\n");
        }
        return stringBuilder.toString();
    }

    private BigDecimal getFirstFuncValue(BigDecimal x){
//        "-2x^3 -4x^2 +8x -4"
        return x.pow(3).multiply(BigDecimal.valueOf(-2)).add(
                x.pow(2).multiply(BigDecimal.valueOf(-4))).add(
                        x.multiply(BigDecimal.valueOf(8))).add(
                                BigDecimal.valueOf(-4)
        );
    }

    private BigDecimal getSecondFuncValue(BigDecimal x){
//        "1/x"
//        System.out.println("x "+x+"y "+BigDecimal.ONE.divide(x, MathContext.DECIMAL32));
        return BigDecimal.ONE.divide(x, MathContext.DECIMAL32);
    }

    private BigDecimal getThirdFuncValue(BigDecimal x) {
//        "1/(x+2)^4"
        return BigDecimal.ONE.divide(x.add(BigDecimal.valueOf(2)).pow(4), MathContext.DECIMAL32);
    }
    private BigDecimal getForthFuncValue(BigDecimal x) {
//        "1/(x-1)^(1/5)"
        return BigDecimal.ONE.divide(BigDecimal.valueOf(Math.cbrt(x.doubleValue()-1)), MathContext.DECIMAL32);
    }

    private BigDecimal getFifthFuncValue(BigDecimal x) {
//        "5x+3"
        return x.multiply(BigDecimal.valueOf(5)).add(BigDecimal.valueOf(3));
    }
private BigDecimal getSixthFuncValue(BigDecimal x){
//        "sin(3x-1)/(x-1)"
    return BigDecimal.valueOf(Math.sin(3*x.doubleValue()-1)).divide(x.subtract(BigDecimal.ONE),MathContext.DECIMAL32);
}

    public HashMap<Integer, BigDecimal> getBreakPoints() {
        return breakPoints;
    }

    public Map<Integer, Function<BigDecimal, BigDecimal>> getFunctionMap(){
        return  this.functionMap;
    }
}

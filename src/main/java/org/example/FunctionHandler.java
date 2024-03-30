package org.example;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FunctionHandler {
    private HashMap<Integer, String> functions = new HashMap<>();
    private Map<Integer, Function<BigDecimal, BigDecimal>> functionMap = new HashMap<>();

    public FunctionHandler() {
        this.functions.put(1, "-2x^3 -4x^2 +8x -4");
        this.functionMap.put(1, this::getFirstFuncValue);
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


    public Map<Integer, Function<BigDecimal, BigDecimal>> getFunctionMap(){
        return  this.functionMap;
    }
}

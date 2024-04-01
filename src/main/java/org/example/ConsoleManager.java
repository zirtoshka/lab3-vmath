package org.example;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Stream;

public class ConsoleManager {

    private FunctionHandler functionHandler;
    private MethodManager methodManager;

    public ConsoleManager(FunctionHandler functionHandler, MethodManager methodManager) {
        this.functionHandler = functionHandler;
        this.methodManager=methodManager;
    }

    public void startConversation() {
        Scanner sc = new Scanner(System.in);
        String answer = "";

        while (true) {
            System.out.println("Пожалуйста, выберите функцию:");
            System.out.println(functionHandler.getFunctions());

            answer= sc.nextLine().toLowerCase().trim();

            methodManager.setFunction(functionHandler.getFunctionMap().get(Integer.parseInt(answer)));
            methodManager.setBreakPoint(functionHandler.getBreakPoints().get(Integer.parseInt(answer)));

            System.out.println("Интервал для интегрирования через пробел:");
            BigDecimal[] ab =  Stream.of(sc.nextLine().toLowerCase().trim().split(" "))
                    .map(BigDecimal::new)
                    .sorted()
                    .limit(2)
                    .toArray(BigDecimal[]::new);
            methodManager.setLeftBorder(ab[0]);
            methodManager.setRightBorder(ab[1]);


            System.out.println("выберите точность вычисления:");
            answer = sc.nextLine().toLowerCase().trim();
            methodManager.setAccuracy(new BigDecimal(answer));

            System.out.println("выберите метод:");
            System.out.println(methodManager.getNamesMethods());
            answer = sc.nextLine().toLowerCase().trim();
            methodManager.setMethodNumber(Integer.parseInt(answer));

            methodManager.runMethod();


        }
    }
}

package org.example;

import org.example.exp.ExitException;
import org.example.exp.IndexWrongException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

public class ConsoleManager {

    private FunctionHandler functionHandler;
    private MethodManager methodManager;
    private final int funcQuantity;
    private final int methodQuantity;
    private final ArrayList<String> exitStr = new ArrayList<String>() {{
        add("выход");
        add("в");
        add("exit");
        add("e");
    }};

    public ConsoleManager(FunctionHandler functionHandler, MethodManager methodManager) {
        this.functionHandler = functionHandler;
        funcQuantity = functionHandler.getFunctionMap().size();

        this.methodManager = methodManager;
        methodQuantity = methodManager.getMethodMap().size();
    }

    public void startConversation() {
        Scanner sc = new Scanner(System.in);
        String answer = "";
        boolean nextStep = true;
        int func, method;
        try {
            while (true) {
                try {
                    nextStep = true;
                    System.out.println("Пожалуйста, выберите функцию: (для выхода введите выход(в)/exit(e))");
                    System.out.println(functionHandler.getFunctions());

                    answer = sc.nextLine().toLowerCase().trim();
                    if (exitStr.contains(answer)) throw new ExitException();

                    func = Integer.parseInt(answer);
                    if (func <= 0 || func > funcQuantity)
                        throw new IndexWrongException("введите из списка номер функции");
                    methodManager.setFunction(functionHandler.getFunctionMap().get(func));
                    methodManager.setBreakPoint(functionHandler.getBreakPoints().get(func));

                    do {
                        try {
                            System.out.println("Интервал для интегрирования через пробел:");
                            BigDecimal[] ab = Stream.of(sc.nextLine().toLowerCase().trim().split(" "))
                                    .map(BigDecimal::new)
                                    .sorted()
                                    .limit(2)
                                    .toArray(BigDecimal[]::new);
                            methodManager.setLeftBorder(ab[0]);
                            methodManager.setRightBorder(ab[1]);
                            nextStep = true;
                        } catch (NumberFormatException e) {
                            System.out.println("я такое не понимать, введите число, пожалуйста");
                            nextStep = false;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            System.out.println("хотелось бы для интервала иметь оба числа");
                            nextStep = false;
                        }
                    }
                    while (!nextStep);

                    do {
                        try {

                            System.out.println("выберите точность вычисления:");
                            answer = sc.nextLine().toLowerCase().trim();
                            if (exitStr.contains(answer)) throw new ExitException();
                            methodManager.setAccuracy(new BigDecimal(answer));
                            nextStep = true;
                        } catch (NumberFormatException e) {
                            System.out.println("я такое не понимать, введите число, пожалуйста");
                            nextStep = false;
                        }
                    } while (!nextStep);

                    do {
                        try {
                            System.out.println("выберите метод:");
                            System.out.println(methodManager.getNamesMethods());
                            answer = sc.nextLine().toLowerCase().trim();
                            method = Integer.parseInt(answer);
                            if (method <= 0 || method > methodQuantity)
                                throw new IndexWrongException("какой-то очень странный метод, я такое не знаю");

                            if (exitStr.contains(answer)) throw new ExitException();
                            methodManager.setMethodNumber(method);
                            nextStep = true;
                        } catch (NumberFormatException e) {
                            System.out.println("я такое не понимать, введите число, пожалуйста");
                            nextStep = false;
                        } catch (IndexWrongException e) {
                            System.out.println(e.getMessage());
                            nextStep = false;
                        }
                    } while (!nextStep);
                    methodManager.runMethod();

                } catch (NumberFormatException e) {
                    System.out.println("я такое не понимать, введите число, пожалуйста");
                    nextStep = false;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("хотелось бы для интервала иметь оба числа");
                    nextStep = false;
                } catch (IndexWrongException e) {
                    System.out.println(e.getMessage());
                    nextStep = false;
                }


            }
        } catch (ExitException e) {
            System.out.println("Bye bye");
        }
    }
}


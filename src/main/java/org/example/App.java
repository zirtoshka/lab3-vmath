package org.example;

public class App {
    public static void main(String[] args) {
        FunctionHandler functionHandler=new FunctionHandler();
        MethodManager methodManager=new MethodManager();

        ConsoleManager consoleManager = new ConsoleManager(functionHandler, methodManager);
        consoleManager.startConversation();

    }

}
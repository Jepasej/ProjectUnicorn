package org.example;

public class Main {
    public static void main(String[] args) {
        double result = 0;
        final double A = 3;
        final double B = 11;

        Calculator calculator = new Calculator();
        TimesFunction timesFunction = new TimesFunction();

        result = calculator.calculate(A,B, timesFunction);

        System.out.println("A is: " + A + " B is: " + B);
        System.out.println("A times B is: " + result);

        //Instead of making a new class implementing interface, it is okay to just declare "anonymously" in main:

        CalculatorFunction additionFunction = new CalculatorFunction() {
            @Override
            public double calculate(double a, double b) {
                return a+b;
            }

        };

        result = calculator.calculate(A,B, additionFunction);
        System.out.println("A plus B is: " + result);

        /*
        Instead of making an anonymous class, we can also just declare the interface override straight in the method call
        But then we can ONLY USE IT THIS ONCE!
        Here with division
        */

        result = calculator.calculate(A,B, new CalculatorFunction() {
            @Override
            public double calculate(double a, double b) {
            return a/b;
            }
        });

        System.out.println("A divided by B is: " + result);

        /*
        Is it possible to do it straight away in System out print line? Let's try with subtraction!
        */

        System.out.println("A minus B is: " + calculator.calculate(A, B, new CalculatorFunction() {
            @Override
            public double calculate(double a, double b) {
                return a-b;
            }
        }));
    }
}
package org.example;

public class Calculator {

    double calculate(double a, double b, CalculatorFunction cf) {
        return cf.calculate(a, b);
    }
}

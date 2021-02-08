package com.iafoot.notes.arithmetic;

/**
 * 斐波那契数列
 * @author iAfoot
 * @Create 20210208 8:46
 * @Version 1.0.0
 */
public class FibonacciSequence {
    public static void main(String[] args) {
        for (int counter = 0; counter <= 10; counter++){
            System.out.printf("Fibonacci of %d is: %d\n", counter, fibonacci(counter));
        }
    }

    public static long fibonacci(long number) {
        if ((number == 0) || (number == 1))
            return number;
        else
            return fibonacci(number - 1) + fibonacci(number - 2);
    }
}

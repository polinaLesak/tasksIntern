public class task2 {
    public static double calculateUn(int n) {
        double factorialSum = 0.0;
        double factorial = 1.0;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
            factorialSum += factorial;
        }
        return factorialSum / factorial;
    }
}

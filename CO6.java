import java.util.Scanner;

public class WasteSenseKnapsack {

    public static void knapsack(int[] weight, int[] value, int n, int capacity) {

        int[][] dp = new int[n + 1][capacity + 1];

        // Build DP Table
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {

                if (i == 0 || w == 0) {
                    dp[i][w] = 0;
                }
                else if (weight[i - 1] <= w) {

                    dp[i][w] = Math.max(
                            value[i - 1] + dp[i - 1][w - weight[i - 1]],
                            dp[i - 1][w]
                    );
                }
                else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        System.out.println("\nMaximum Total Value = " + dp[n][capacity]);

        // Find selected bins
        int w = capacity;

        System.out.println("\nSelected Bins:");

        for (int i = n; i > 0 && dp[i][w] != 0; i--) {

            if (dp[i][w] != dp[i - 1][w]) {

                System.out.println(
                        "Bin " + i +
                        " (Weight = " + weight[i - 1] +
                        " kg, Value = " + value[i - 1] + ")"
                );

                w = w - weight[i - 1];
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of waste bins: ");
        int n = sc.nextInt();

        int[] weight = new int[n];
        int[] value = new int[n];

        for (int i = 0; i < n; i++) {

            System.out.println("\nBin " + (i + 1));

            System.out.print("Enter Weight (kg): ");
            weight[i] = sc.nextInt();

            System.out.print("Enter Value: ");
            value[i] = sc.nextInt();
        }

        System.out.print("\nEnter Vehicle Capacity (kg): ");
        int capacity = sc.nextInt();

        knapsack(weight, value, n, capacity);

        sc.close();
    }
}
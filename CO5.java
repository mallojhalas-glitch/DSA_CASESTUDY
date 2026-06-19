import java.util.Scanner;

public class WasteSenseCountingSort {

    static void countingSort(int arr[]) {

        int n = arr.length;

        // Find maximum value
        int max = arr[0];

        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        // Create count array
        int count[] = new int[max + 1];

        // Count occurrences
        for (int i = 0; i < n; i++) {
            count[arr[i]]++;
        }

        // Reconstruct sorted array
        int index = 0;

        for (int i = 0; i <= max; i++) {

            while (count[i] > 0) {
                arr[index++] = i;
                count[i]--;
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of smart bins: ");
        int n = sc.nextInt();

        int waste[] = new int[n];

        System.out.println("Enter waste quantities (kg):");

        for (int i = 0; i < n; i++) {
            System.out.print("Bin " + (i + 1) + ": ");
            waste[i] = sc.nextInt();
        }

        System.out.println("\nWaste quantities before sorting:");

        for (int value : waste) {
            System.out.print(value + " ");
        }

        countingSort(waste);

        System.out.println("\n\nWaste quantities after Counting Sort:");

        for (int value : waste) {
            System.out.print(value + " ");
        }

        sc.close();
    }
}
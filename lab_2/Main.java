import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static String FindMaxSubstr(String str) {
		HashMap<Character, Integer> symbols = new HashMap<>();
		String maxSubstr = "";
		int maxStr = 0;
		int startIndex = 0;

		for (int i = 0; i < str.length(); i++) {
			char symbol = str.charAt(i);

			if (symbols.containsKey(symbol)) {
				startIndex = Math.max(startIndex, symbols.get(symbol) + 1);
			}

			symbols.put(symbol, i);

			if (i - startIndex + 1 > maxStr) {
				maxStr = i - startIndex + 1;
				maxSubstr = str.substring(startIndex, i + 1);
			}
		}

		return maxSubstr;
	}

	public static int[] MergeSortedArrays(int[] arr1, int[] arr2) {
		int[] mergedArray = new int[arr1.length + arr2.length];
		int i = 0, j = 0, k = 0;

		while (i < arr1.length && j < arr2.length) {
			if (arr1[i] < arr2[j]) {
					mergedArray[k++] = arr1[i++];
			} else {
					mergedArray[k++] = arr2[j++];
			}
		}

		while (i < arr1.length) {
				mergedArray[k++] = arr1[i++];
		}

		while (j < arr2.length) {
				mergedArray[k++] = arr2[j++];
		}

		return mergedArray;
	}

	public static int FindMaxSubarrSum(int[] arr) {
		int sum;
		int maxSum = 0;

		for (int i = 0; i < arr.length; i++) {
			sum = Math.max(arr[i], maxSum + arr[i]);
			maxSum = Math.max(maxSum, sum);
		}

		return maxSum;
	}

	public static int[][] RotateArrayClockwise(int[][] arr) {
		int n = arr.length;
		int m = arr[0].length;
		int[][] rotArr = new int[m][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				rotArr[j][n - 1 - i] = arr[i][j];
			}
		}

		return rotArr;
	}

	public static int[] FindPair(int[] arr, int target) {
		HashMap<Integer, Integer> adds = new HashMap<>();

		for (int num : arr) {
			int add = target - num;
			if (adds.containsKey(add)) {
				return new int[]{add, num};
			}
			adds.put(num, 1);
		}

		return null;
	}

	public static int SumOf2DArray(int[][] arr) {
		int sum = 0;

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				sum += arr[i][j];
			}
		}

		return sum;
	}

	public static int[] MaxElInRow(int[][] arr) {
		int[] maxVals = new int[arr.length];

		for (int i = 0; i < arr.length; i++) {
			int max = arr[i][0];

			for (int j =1; j < arr[i].length; j++) {
				if (arr[i][j] > max) {
					max = arr[i][j];
				}
			}

			maxVals[i] = max;
		}

		return maxVals;
	}

	public static int[][] RotateArrayCounterclockwise(int[][] arr) {
		int n = arr.length;
		int m = arr[0].length;
		int[][] rotArr = new int[m][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				rotArr[n - 1 - j][i] = arr[i][j];
			}
		}

		return rotArr;
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a number of task: ");
		int n = in.nextInt();

		switch (n) {
			case 1:
				String str = "aastraaa";
				System.out.print(FindMaxSubstr(str));
				break;
			case 2:
				int[] arr1 = {1, 2, 3};
				int[] arr2 = {5, 4, 6};
				int[] res = MergeSortedArrays(arr1, arr2);

				for (int i = 0; i < res.length; i++) {
					System.out.print(res[i] + " ");
				}
				break;
			case 3:
				int[] arr = {-2, -1, 0, 1, 2, 3};
				System.out.print(FindMaxSubarrSum(arr));
				break;
			case 4:
				int[][] arr4 = {
					{1, 2, 3},
					{4, 5, 6},
					{7, 8, 9}
				};
				int[][] res4 = RotateArrayClockwise(arr4);

				for (int[] row : res4) {
					for (int val : row) {
						System.out.print(val + " ");
					}
					System.err.println();
				}
				break;
			case 5:
				int[] arr5 = {1, 2, 3};
				int target = 3;
				int[] res5 = FindPair(arr5, target);
				System.out.print(res5[0] + " " + res5[1]);
				break;
			case 6:
				int[][] arr6 = {
					{1, 2, 3},
					{4, 5, 6},
					{7, 8, 9}
				};
				System.out.print(SumOf2DArray(arr6));
				break;
			case 7:
				int[][] arr7 = {
					{1, 2, 3},
					{4, 5, 6},
					{7, 8, 9}
				};
				int[] res7 = MaxElInRow(arr7);

				for (int val : res7) {
					System.out.print(val + " ");
				}
				break;
			case 8:
				int[][] arr8 = {
					{1, 2, 3},
					{4, 5, 6},
					{7, 8, 9}
				};
				int[][] res8 = RotateArrayCounterclockwise(arr8);

				for (int[] row : res8) {
					for (int val : row) {
						System.out.print(val + " ");
					}
					System.err.println();
				}
				break;
			default:
				System.out.print("No such task number");
		}

		in.close();
	}
}

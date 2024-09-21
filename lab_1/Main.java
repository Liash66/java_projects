import java.util.Scanner;

public class Main {
	public static int CollatzConjecture() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a natural number n: ");
		int n = in.nextInt();
		in.close();
		int k = 0;
		
		while (n != 1) {
			if ( n % 2 == 0) {
				n /= 2;
				k++;
			} else {
				n = 3 * n + 1;
				k++;
			}
		}

		return k;
	}

	public static int SumOfRow() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a number of row: ");
		int n = in.nextInt();
		int sum = 0;

		for (int i = 1; i <= n; i++) {
			System.out.print("Enter a number " + i + ": ");
			int num = in.nextInt();
			
			if (i % 2 == 1) {
					sum += num;
			} else {
					sum -= num;
			}
		}

		in.close();
		return sum;
	}

	public static int FindTreasure() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a treasure coords: ");
		int X = 0;
		int Y = 0;
		int k = 0;
		int stepsNeeded = 0;
		int treasureX = in.nextInt();
		int treasureY = in.nextInt();

		while (true) { 
			System.out.print("Enter a direction: ");
			String dir = in.next();
			if(dir.equals("stop")) {
				break;
			}

			int steps = 0;
			k++;

			System.out.print("Enter a number of steps: ");
			switch (dir) {
				case "north":
					steps = in.nextInt();
					Y += steps;
					break;
				case "south":
					steps = in.nextInt();
					Y -= steps;
					break;
				case "east":
					steps = in.nextInt();
					X += steps;
					break;
				case "west":
					steps = in.nextInt();
					X -= steps;
					break;
				default:
					System.out.println("Unknown direction");
			}

			if (X == treasureX && Y == treasureY) {
				stepsNeeded = k;
			}
		}

		return stepsNeeded;
	}

	public static class Result {
		final int m_maxRoadNum, m_maxTunnelHeight;

		public Result(int maxRoadNum, int maxTunnelHeight) {
			this.m_maxRoadNum = maxRoadNum;
			this.m_maxTunnelHeight = maxTunnelHeight;
		}
	}

	public static Result LogisticMaximin() {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a number of roads: ");
		int roadsNum = in.nextInt();
		int tunnelsNum = 0;
		int tunnelHeight = 0;

		int maxRoadNum = 0;
		int minTunnelHeight;
		int maxTunnelHeight = 0;
		
		for (int i = 0; i < roadsNum; i++) {
			minTunnelHeight = Integer.MAX_VALUE;
			System.out.print("Enter a number of tunnels for road " + (i + 1) + ": ");
			tunnelsNum = in.nextInt();

			for (int j = 0; j < tunnelsNum; j++) {
				System.out.print("Enter a height of tunnel " + (j + 1) + ": ");
				tunnelHeight = in.nextInt();

				if (tunnelHeight < minTunnelHeight) {
					minTunnelHeight = tunnelHeight;
				}
			}

			if (minTunnelHeight > maxTunnelHeight) {
				maxTunnelHeight = minTunnelHeight;
				maxRoadNum = i + 1;
			}
		}

		return new Result(maxRoadNum, maxTunnelHeight);
	}

	public static class TwiceEven {
		final int m_val;

		public TwiceEven(int val) {
			this.m_val = val;
		}

		public boolean ValCheck() {
			int digitCount = (int) (Math.log10(m_val) + 1);

			if (digitCount == 3) {
				return true;
			}

			return false;
		}

		public boolean TwiceEvenCheck() {
			if (ValCheck() == false) {
				System.out.print("Number digit more/less than 3");
				return false;
			}

			int sum = (m_val % 10) + ((m_val % 100) / 10) + m_val / 100;
			int mul = (m_val % 10) * ((m_val % 100) / 10) * m_val / 100;

			if ((sum % 2 == 0) && (mul % 2 == 0)) {
				return true;
			}

			return false;
		}
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter a number of task: ");
		int n = in.nextInt();

		switch (n) {
			case 1:
				System.out.print(CollatzConjecture());
				break;
			case 2:
				System.out.print(SumOfRow());
				break;
			case 3:
				System.out.print(FindTreasure());
				break;
			case 4:
				Result result = LogisticMaximin();
				System.out.print(result.m_maxRoadNum + " " + result.m_maxTunnelHeight);
				break;
			case 5:
				System.out.print("Enter a 3-digit number: ");
				TwiceEven val = new TwiceEven(in.nextInt());
				System.out.print(val.TwiceEvenCheck());
				break;
			default:
				System.out.print("No such task number");
		}

		in.close();
	}
}

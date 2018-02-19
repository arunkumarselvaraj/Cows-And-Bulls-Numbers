import java.util.*;

public class CowsAndBullsNumbers {
	public static List<Integer[]> guessList = new ArrayList<Integer[]>();
	public static int cows = 0;
	public static int bulls = 0;
	public static int trials = 0;
	
	public static boolean hasRepeatingDigits(int number) {
		Map<Integer, Integer> intMap = new HashMap<Integer, Integer>();
		int[] intArr = getDigitArray(number);
		int length = intArr.length;
		for(int i=0; i<length; i++) {
			if(intMap.containsKey(intArr[i])) {
				intMap.clear();
				return true;
			}else {
				intMap.put(intArr[i], 1);
			}
		}
		intMap.clear();
		return false;
	}
	
	public static int pickARandomNumber(int digit) {
		Random rand = new Random();
		int randomNumber = 0;
		if(digit == 3) {
			int min = 123;
			int max = 987;
			randomNumber = rand.nextInt((max - min) + 1) + min;
		}else if(digit == 4) {
			int min = 1234;
			int max = 9876;
			randomNumber = rand.nextInt((max - min) + 1) + min;
		}else if(digit == 5) {
			int min = 12345;
			int max = 98765;
			randomNumber = rand.nextInt((max - min) + 1) + min;
		}
		return randomNumber;
	}
	
	public static int[] getDigitArray(int number) {
		String temp = Integer.toString(number);
		int[] digitArray = new int[temp.length()];
		for (int i = 0; i < temp.length(); i++)
		{
			digitArray[i] = temp.charAt(i) - '0';
		}
		return digitArray;
	}
	
	public static void calculateCowsAndBulls(int computerNumber, int guessedNumber, int digit) {
		int[] cN = getDigitArray(computerNumber);
		int[] gN = getDigitArray(guessedNumber);
		for(int i=0; i<digit; i++) {
			for(int j=0; j<digit; j++) {
				if(cN[i] == gN[j]) {
					if(i == j) {
						++bulls;
					}else {
						++cows;
					}
					break;
				}
			}
		}
		Integer[] guess = new Integer[3];
		guess[0] = guessedNumber;
		guess[1] = cows;
		guess[2] = bulls;
		guessList.add(guess);
	}
	
	public static void printGuessedList() {
		System.out.println("Guess\tNumber\tCows\tBulls");
		Iterator<Integer[]> iter = guessList.iterator();
		int i = 1;
		while(iter.hasNext()) {
			Integer[] temp = iter.next();
			System.out.println(i++ + "\t" + temp[0] + "\t" + temp[1] + "\t" + temp[2]);
		}
	}
	
	public static void startGuessing(int computerNumber, int digit) {
		cows = bulls = 0;
		Scanner sc = new Scanner(System.in);
		System.out.println("Guess the number (" + digit + " digit, non-repeating).");
		int guessedNumber = sc.nextInt();
		int[] guessedNumberDigits = getDigitArray(guessedNumber);
		while((guessedNumberDigits.length != digit) || hasRepeatingDigits(guessedNumber)) {
//			System.out.println(guessedNumberDigits.length);
//			System.out.println(digit);
//			System.out.println(hasRepeatingDigits(guessedNumber));
			System.out.println("Please input a valid number.." + guessedNumberDigits.length + "  " + digit + "  " + hasRepeatingDigits(guessedNumber));
			System.out.println("Guess the number (" + digit + " digit, non-repeating).");
			guessedNumber = sc.nextInt();
			guessedNumberDigits = getDigitArray(guessedNumber);
		}
		trials++;
		calculateCowsAndBulls(computerNumber, guessedNumber, digit);
		if(bulls == digit) {
			System.out.println("Congrats, you found the number!");
			System.out.println("Number of guesses : " + trials + "\n");
			guessList.clear();
			trials = 0;
			gameTypeMenu();
		}else {
			printGuessedList();
			startGuessing(computerNumber, digit);
		}
		sc.close();
	}
	
	public static void gameTypeMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Choose Game Type");
		System.out.println("1. 3 digit numbers");
		System.out.println("2. 4 digit numbers");
		System.out.println("3. 5 digit numbers");
		System.out.println("4. exit");
		int option = sc.nextInt();
		switch(option) {
			case 1:
			case 2:
			case 3:
				int computerNumber = pickARandomNumber(option + 2);
				while(hasRepeatingDigits(computerNumber)) {
					computerNumber = pickARandomNumber(option + 2);
				}
//				System.out.println("Computer Number : " + computerNumber);
				startGuessing(computerNumber, option + 2);
				break;
			case 4:
				return;
			default:
				gameTypeMenu();
		}
		sc.close();
	}
	
	public static void main(String[] args) {
		gameTypeMenu();
		return;
	}
}
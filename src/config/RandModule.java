package config;

import java.util.Random;

public class RandModule {
	private Random rand = new Random();
	private String strCode = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_+";
	private final int[] arrCode = {1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33,
								   34, 35, 36 ,37, 38, 39};
	
    public int getRndInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
    
	public int getRndAreaCode() {
		int i = getRndInt(0, 16);
		return arrCode[i];
	}
	
	public String getRndVal(int size) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<size; i++) {
			sb.append(strCode.charAt(rand.nextInt(48)));
		}
		
		return sb.toString();
	}
}

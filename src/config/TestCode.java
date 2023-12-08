package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestCode {
	public static void main(String args[]) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//double x = Double.parseDouble(br.readLine());
		//double y = Double.parseDouble(br.readLine());
		double x = 1303;
		double y = 469;
		//double divide = Double.parseDouble(br.readLine());
		
		
		//double rs1 = x/divide;
		//double rs2 = y/divide;
		//System.out.println(rs1 + " " + rs2);
		
		
		String text = "abcde";
		CryptoModule cm = new CryptoModule();
		String a = "";
		String b = "";
		try {
			a = cm.aesCBCEncode(text);
			b = cm.aesCBCDecode(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("original : " + text);
		System.out.println("crypto : " + a);
		System.out.println("plan : " + b);
		
		
		 Path currentPath = Paths.get("");
	     String path = System.getProperty("user.home") + "\\Documents\\NextTrip";
	     
	     System.out.println(path);
		
		
	}
}

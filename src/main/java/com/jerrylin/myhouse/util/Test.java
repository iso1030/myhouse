package com.jerrylin.myhouse.util;

import java.io.File;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springside.modules.utils.Encodes;

public class Test {

	private static Pattern uploadImagePattern = Pattern.compile("^/\\d+(/d2|/d3)?/\\d+\\.jpg", Pattern.CASE_INSENSITIVE);
	
	public static void main(String[] args) {
		System.out.println(File.pathSeparator);
//		Random random = new Random();
//		byte[] bytes = new byte[1024];
//		random.nextBytes(bytes);
//		System.out.println(Encodes.encodeUrlSafeBase64(bytes));
		
		String s1 = "/20130404/111.jpg";
		String s2 = "/38/d2/888.jpg";
		
		Matcher matcher = uploadImagePattern.matcher(s1);
		System.out.println(matcher.matches());
		
		matcher = uploadImagePattern.matcher(s2);
		System.out.println(matcher.matches());
	}
}

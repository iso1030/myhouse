package com.jerrylin.myhouse.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTest {

	public static void main(String[] args) throws Exception {
		String intput = "/Users/linhui/Documents/workspace/myhouse/src/main/webapp/images";
		File file = new File(intput);
		System.out.println(file.isDirectory());
		
		String[] list = file.list();
		for (String each : list) {
			System.out.println(each);
		}
		
		String output = "/Users/linhui/Documents/workspace/myhouse/src/main/webapp/static/images/test.zip";
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output));
		ZipOutputStream zos = new ZipOutputStream(bos);
		
		for (String each : list) {

			ZipEntry entry = new ZipEntry(each);
			zos.putNextEntry(entry);
			
			File tmp = new File("/Users/linhui/Documents/workspace/myhouse/src/main/webapp/images/"+each);
			System.out.println(tmp.exists());
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(tmp));
			
			byte[] b = new byte[1024];
			while (bis.read(b, 0, 1024) != -1) {
				zos.write(b, 0, 1024);
			}
			bis.close();
			zos.closeEntry();
		}
		zos.flush();
		zos.close();

	}
}

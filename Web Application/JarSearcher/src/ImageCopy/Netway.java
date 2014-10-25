package ImageCopy;

import java.io.*;

public class Netway {

	public static void main(String args[]){

		try { 

			File sourceFile=new File("D:\\input\\1.jpg");
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile), 4096);
			File targetFile = new File("D:\\Output\\"+sourceFile.getName()); 
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile), 4096);
			int theChar;
			while ((theChar = bis.read()) != -1) {
				bos.write(theChar);
			}
			bos.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

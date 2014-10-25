package ImageCopy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageCopier {
	
	public String copyImage(String sourcePath)
	{
		File source = new File(sourcePath);
		File destination = new File("D:\\Output\\"+source.getName());
		System.out.println(sourcePath);
		System.out.println(destination.getAbsolutePath());
		FileInputStream fis;
		FileOutputStream fos;
		try 
		{
			fos = new FileOutputStream(destination);
			fis = new FileInputStream(source);
			BufferedInputStream bis = new BufferedInputStream(fis);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int c = bis.read();
			while(c!=-1)
			{
				System.out.println(c);
				bos.write(c);
				c = bis.read();
			}
			bis.close();
			bos.close();
			fis.close();
			fos.close();
			return destination.getAbsolutePath();
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
			return "error";
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return "error";
		}
	}
}

package jarSearcher_p01;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;

public class FileSearcher 
{
	private boolean chkForInnerJars=false;
	private List<String> files = new ArrayList<String>();
	private JarFile jarOrwarFile;
	private List<JarEntry> jarEntries; 
	private int jarCounter=1,searchCounter=1;
	private Map<Integer,String> map = new HashMap<Integer,String>();
	private static Map<String,String> configMapper = new HashMap<String,String>();
	private String parentFilePath,searchFile,resultLocation;
	public void SearchFiles() throws IOException
	{
		fetchFromConfig();
		File file = new File(parentFilePath);
		if(file.isDirectory())
		{
			searchDirectories(file);
		}
		else
		{
			searchThroughJarsandWars(parentFilePath);
		}
		writeLogFiles();
		removeCreatedJars();
	}
	
	private void writeLogFiles() throws IOException 
	{
		int noOfInstances=1;
		FileWriter writer = new FileWriter(resultLocation+"\\"+"Search Logs.txt",true);
		BufferedWriter write = new BufferedWriter(writer);
		write.append("Search Results for "+searchFile+" on "+new Date()+"\n" );
		write.append("-----------------------------------------------------------------\n");
		if(files.isEmpty())
		{
			System.out.println("No such files ");
			write.append("No such files ");
		}
		for(String name : files)
		{
			System.out.println(noOfInstances+" : " +name);
			write.append(noOfInstances+" : " +name+"\n");
			noOfInstances++;
		}
		write.append("-----------------------------------------------------------------\n");
		write.append("-----------------------------------------------------------------\n");
		write.close();
		writer.close();
	}
	
	private void removeCreatedJars()
	{
		File tempFile=new File("D:\\JarExtractor");
		File[] files = tempFile.listFiles();
		System.out.println(files.length);
		for(File f : files)
		{
			f.delete();
		}
	}
	private void fetchFromConfig() throws IOException 
	{
		File f = new File("D:\\My Dumps\\My Training\\JarSearcher\\src\\jarSearcher_p01\\config.properties");
		FileInputStream fis = new FileInputStream(f);
		Properties prop = new Properties();
		prop.load(fis);
		Set<Object> keys = prop.keySet();
		for(Object key : keys)
		{
			String k = key.toString();
			String value = prop.getProperty(k);
			configMapper.put(k.toLowerCase(), value.toLowerCase());
		}
		this.parentFilePath = configMapper.get("searchlocation");
		this.searchFile = configMapper.get("searchfile");
		this.resultLocation = configMapper.get("resultlocation");
	}
	private void searchDirectories(File file) throws IOException
	{
		File[] files = file.listFiles();
		for(File f : files)
		{
			if(!f.isDirectory())
			{
				if(f.getName().endsWith(".jar")||f.getName().endsWith(".war")||f.getName().endsWith(".ear"))
					searchThroughJarsandWars(f.getAbsolutePath());
			}
			else
			{				
				searchDirectories(f);
			}
		}
	}
	
	private void searchThroughJarsandWars(String parentFilePath) throws IOException 
	{
		try
		{
			System.out.println("Searching through "+parentFilePath);
			jarOrwarFile = new JarFile(parentFilePath);
		}
		catch(ZipException e)
		{
			System.err.println("UNABLE TO OPEN ::: "+parentFilePath);
			files.add(jarOrwarFile.getName()+" -- ERROR FILE DETECTED");
			return;
		}
		jarEntries = Collections.list(jarOrwarFile.entries());
		for (JarEntry entry : jarEntries) 
		{  
			if(!entry.isDirectory())
			{
				String str = jarOrwarFile.getName()+" - "+entry.getName();
				if(str.endsWith(".jar"))
				{
					chkForInnerJars = true;
					File tempFile=new File("D:\\JarExtractor\\"+jarCounter+".jar");
					BufferedOutputStream jos = new BufferedOutputStream(new FileOutputStream(tempFile));
					InputStream is = jarOrwarFile.getInputStream(entry);
		            byte[] buffer = new byte[4096];
		            int bytesRead = 0;
		            while ((bytesRead = is.read(buffer)) != -1) 
		            {
		               jos.write(buffer, 0, bytesRead);
		            }
		            is.close();
		            jos.flush();
		            jos.close();
					map.put(jarCounter++, str);
				}
				else if(str.endsWith(".ear"))
				{
					chkForInnerJars = true;
					File tempFile=new File("D:\\JarExtractor\\"+jarCounter+".ear");
					BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(tempFile));
					InputStream is = jarOrwarFile.getInputStream(entry);
		            byte[] buffer = new byte[4096];
		            int bytesRead = 0;
		            while ((bytesRead = is.read(buffer)) != -1) 
		            {
		               fos.write(buffer, 0, bytesRead);
		            }
		            is.close();
		            fos.flush();
		            fos.close();
					map.put(jarCounter++, str);
				}
				else 
				{
					if(entry.getName().toLowerCase().contains(searchFile.toLowerCase()))
					{
						System.err.println("Found in jar:: "+ jarOrwarFile.getName());
						files.add(jarOrwarFile.getName()+" -- "+entry.getName());
					}
				}
			}
		}
		if(chkForInnerJars)
			SearchInsideInnerJars();
	}
	
	
	
	public void SearchInsideInnerJars() throws IOException
	{
		System.out.println("Searching through "+parentFilePath);
		chkForInnerJars = false;
		for(;searchCounter<jarCounter;searchCounter++)
		{
			JarFile jar = new JarFile("D:\\JarExtractor\\"+searchCounter+".jar");
			List<JarEntry> jarEntries = Collections.list(jar.entries());
			for(JarEntry entry : jarEntries)
			{
				if(!entry.isDirectory())
				{
					String str = jar.getName()+" - "+entry.getName();
					if(str.endsWith(".jar"))
					{
						chkForInnerJars = true;
						File tempFile=new File("D:\\JarExtractor\\"+jarCounter+".jar");
						BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(tempFile));
						InputStream is = jar.getInputStream(entry);
			            byte[] buffer = new byte[4096];
			            int bytesRead = 0;
			            while ((bytesRead = is.read(buffer)) != -1) 
			            {
			               fos.write(buffer, 0, bytesRead);
			            }
			            is.close();
			            fos.flush();
			            fos.close();
						map.put(jarCounter++, str);
					}
					else if(str.endsWith(".ear"))
					{
						chkForInnerJars = true;
						File tempFile=new File("D:\\JarExtractor\\"+jarCounter+".ear");
						BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(tempFile));
						InputStream is = jar.getInputStream(entry);
			            byte[] buffer = new byte[4096];
			            int bytesRead = 0;
			            while ((bytesRead = is.read(buffer)) != -1) 
			            {
			               fos.write(buffer, 0, bytesRead);
			            }
			            is.close();
			            fos.flush();
			            fos.close();
						map.put(jarCounter++, str);
					}
					else 
					{
						if(entry.getName().toLowerCase().contains(searchFile.toLowerCase()))
						{
							System.err.println("Found in jar:: "+ map.get(searchCounter));
							files.add(map.get(searchCounter)+" -- "+entry.getName());
						}
					}
				}
			}
		}
		if(chkForInnerJars)
			SearchInsideInnerJars();
	}
	public Map<Integer, String> getMap() {
		return map;
	}

	public void setMap(Map<Integer, String> map) {
		this.map = map;
	}

	public List<String> getFiles() {
		return files;
	}
	public void addFiles(String file) {
		this.files.add(file);
	}
}

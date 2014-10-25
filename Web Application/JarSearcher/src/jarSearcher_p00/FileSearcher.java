package jarSearcher_p00;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileSearcher 
{
	private boolean chkForInnerJars=false;
	private List<String> files = new ArrayList<String>();
	private JarFile jarOrwarFile;
	private List<JarEntry> jarEntries; 
	private int jarCounter=1,searchCounter=1;
	private Map<Integer,String> map = new HashMap<Integer,String>();
	private static Map<String,String> configMapper = new HashMap<String,String>();
	private String parentFilePath,searchFile;
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
	}
	private void fetchFromConfig() throws IOException 
	{
		File f = new File("D:\\My Dumps\\My Training\\BugFix\\src\\SelfExtractor\\config.properties");
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
	}
	private void searchDirectories(File file) throws IOException
	{
		File[] files = file.listFiles();
		for(File f : files)
		{
			if(!f.isDirectory())
			{
				if(f.getName().endsWith(".jar")||f.getName().endsWith(".war"))
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
		System.out.println("Searching through "+parentFilePath);
		jarOrwarFile = new JarFile(parentFilePath);
		jarEntries = Collections.list(jarOrwarFile.entries());
		for (JarEntry entry : jarEntries) 
		{  
			if(!entry.isDirectory())
			{
				String str = entry.getName();
				System.out.println(str);
				if(str.endsWith(".jar"))
				{
					chkForInnerJars = true;
					File tempFile=new File("D:\\JarExtractor\\"+jarCounter+".jar");
					FileOutputStream fos = new FileOutputStream(tempFile);
					InputStream is = jarOrwarFile.getInputStream(entry);
					while(is.available() > 0)
					{
						fos.write(is.read());
					}
					map.put(jarCounter, str);
					System.out.println("created   : "+entry.getName()+" : "+ (jarCounter++));
				}
				else 
				{
					if(entry.getName().toLowerCase().contains(searchFile.toLowerCase()))
					{
						files.add(entry.getName());
					}
				}
			}
		}
		if(chkForInnerJars)
			SearchInsideInnerJars();
	}
	
	
	
	public void SearchInsideInnerJars() throws IOException
	{
		chkForInnerJars = false;
		for(;searchCounter<jarCounter;searchCounter++)
		{
			JarFile jar = new JarFile("D:\\JarExtractor\\"+searchCounter+".jar");
			List<JarEntry> jarEntries = Collections.list(jar.entries());
			for(JarEntry entry : jarEntries)
			{
				if(!entry.isDirectory())
				{
					String str = entry.getName();
					if(str.endsWith(".jar"))
					{
						chkForInnerJars = true;
						File tempFile=new File("D:\\JarExtractor\\"+jarCounter+".jar");
						FileOutputStream fos = new FileOutputStream(tempFile);
						InputStream is = jarOrwarFile.getInputStream(entry);
						while(is.available() > 0)
						{
							fos.write(is.read());
						}
						map.put(jarCounter, str);
						System.out.println("created   : "+entry.getName()+" : "+ (jarCounter++));
					}
					else 
					{
						if(entry.getName().toLowerCase().contains(searchFile.toLowerCase()))
						{
							System.out.println("Found in jar:: "+ map.get(searchCounter));
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

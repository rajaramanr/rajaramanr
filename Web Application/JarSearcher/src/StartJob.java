import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class StartJob {

	/**
	 * @param args
	 */
	private static String auto_am_build_location = "";
	private static String auto_ims_build_location = "";
	private static String auto_install_location = "";
	private static String auto_perforce_install_location = "";
	private static String auto_start_ims_server_location = "";
	private static String auto_start_oc_server_location = "";
	private Map<String,String> configMapper = new HashMap<String,String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = null;
		FileWriter writer = null;
		StartJob job = new StartJob();
		try {
			job.fetchFromConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Your Choice : "+args[0]);
		try 
		{
			file = new File("c:\\Auto\\"+args[0]+".cmd");
			writer = new FileWriter(file,true);
			BufferedWriter write = new BufferedWriter(writer);
			if(args[0].equals("start_server_ims"))
			{
				write.append("@ECHO off\r\n");
				write.append("cd "+auto_start_ims_server_location+"\r\n");
				if(file.getAbsolutePath().substring(0,2).toLowerCase().equals((auto_start_ims_server_location.substring(0,2).toLowerCase())))
				{
				}
				else {
					write.append(auto_start_ims_server_location.substring(0,2)+"\r\n");
				}
				write.append("start /wait "+auto_start_ims_server_location+" startIms.cmd\r\n");
			}
			else if(args[0].equals("start_server_oc"))
			{
				write.append("@ECHO off\r\n");
				write.append("cd "+auto_start_oc_server_location+"\\r\n");
				if(file.getAbsolutePath().substring(0,2).toLowerCase().equals(auto_start_oc_server_location.substring(0,2)))
				{
				}
				else {
					write.append(auto_start_oc_server_location.substring(0,2)+"\\r\n");
				}
				write.append("start /wait "+auto_start_oc_server_location+" startIms.cmd \\r\n");
			}
			else if(args[0].equals("build_am"))
			{
				write.append("@ECHO off\r\n");
				write.append("echo am_building\r\n");
				write.append("cd "+auto_am_build_location+"\r\n");
				if(file.getAbsolutePath().substring(0,2).toLowerCase().equals(auto_am_build_location.substring(0,2)))
				{
				}
				else {
					write.append(auto_am_build_location.substring(0,2)+"\r\n");
				}
				write.append("mvn\r\n");
			}
			else if(args[0].equals("build_ims"))
			{
				write.append("@ECHO off \r\n");
				write.append("echo am_building\r\n");
				write.append("cd "+auto_ims_build_location+"\r\n");
				if(file.getAbsolutePath().substring(0,2).toLowerCase().equals(auto_ims_build_location.substring(0,2)))
				{
				}
				else {
					write.append(auto_ims_build_location.substring(0,2)+"\r\n");
				}
				write.append("mvn\r\n");
			}
			else if(args[0].equals("install"))
			{
				write.append("@ECHO off\r\n");
				write.append("cd "+auto_install_location+"\r\n");
				if(file.getAbsolutePath().substring(0,2).toLowerCase().equals(auto_install_location.substring(0,2)))
				{
				}
				else {
					write.append(auto_install_location.substring(0,2)+"\r\n");
				}
				write.append("mvn InstallAM:installAll\r\n");
			}
			else if(args[0].equals("update_from_p4"))
			{
				write.append("@ECHO off\r\n");
				write.append("cd "+auto_perforce_install_location+"\r\n");
			}
			write.flush();
			write.close();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void fetchFromConfig() throws IOException 
	{
		File f = new File("C:\\Auto\\config.properties");
		System.out.println(f.getAbsolutePath());
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
		auto_am_build_location = configMapper.get("am_build_location");
		auto_ims_build_location = configMapper.get("ims_build_location");
		auto_install_location = configMapper.get("install_location");
		auto_perforce_install_location = configMapper.get("perforce_install_location");
		auto_start_ims_server_location = configMapper.get("start_ims_server_location");
		auto_start_oc_server_location = configMapper.get("start_oc_server_location");
	}
}

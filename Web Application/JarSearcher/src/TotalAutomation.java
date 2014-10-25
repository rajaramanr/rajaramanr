import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class TotalAutomation {
	private static String auto_am_build_location = "";
	private static String auto_ims_build_location = "";
	private static String auto_install_location = "";
	private static String auto_perforce_install_location = "";
	private static String auto_start_ims_server_location = "";
	private static String auto_start_oc_server_location = "";
	private static String auto_sync_this_folder = "";
	private static String auto_get_userName = "";
	private static String auto_get_password = "";
	private static String auto_get_workspace_name = "";
	private static String auto_sync_to_version = "";
	private static String auto_getHostAndPort = "";
	private Map<String, String> configMapper = new HashMap<String, String>();
	private static Map<Integer, String> createMapper = new HashMap<Integer, String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = null;
		FileWriter writer = null;
		TotalAutomation job = new TotalAutomation();
		try {
			job.fetchFromConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createMapper.put(1, "start_server_ims");
		createMapper.put(2, "start_server_oc");
		createMapper.put(3, "build_am");
		createMapper.put(4, "build_ims");
		createMapper.put(5, "install");
		createMapper.put(6, "update_from_p4");
		System.out.println("Automation Script starts");
		System.out.println("Creation starts");
		for(int i : createMapper.keySet())
		try {
			file = new File("c:\\Auto\\"+createMapper.get(i)+".cmd");
			writer = new FileWriter(file, true);
			BufferedWriter write = new BufferedWriter(writer);
			if (createMapper.get(i).equals("start_server_ims")) {
				write.append("@ECHO off\r\n");
				write.append("cd " + auto_start_ims_server_location + "\r\n");
				if (file.getAbsolutePath().substring(0, 2).toLowerCase()
						.equals((auto_start_ims_server_location.substring(0, 2)
								.toLowerCase()))) {
				} else {
					write.append(auto_start_ims_server_location.substring(0, 2)
							+ "\r\n");
				}
				write.append("start /wait " + auto_start_ims_server_location
						+ " startIms.cmd\r\n");
			} else if (createMapper.get(i).equals("start_server_oc")) {
				write.append("@ECHO off\r\n");
				write.append("cd " + auto_start_oc_server_location + "\\r\n");
				if (file.getAbsolutePath().substring(0, 2).toLowerCase()
						.equals(auto_start_oc_server_location.substring(0, 2))) {
				} else {
					write.append(auto_start_oc_server_location.substring(0, 2)
							+ "\\r\n");
				}
				write.append("start /wait " + auto_start_oc_server_location
						+ " startIms.cmd \\r\n");
			} else if (createMapper.get(i).equals("build_am")) {
				write.append("@ECHO off\r\n");
				write.append("echo am_building\r\n");
				write.append("cd " + auto_am_build_location + "\r\n");
				if (file.getAbsolutePath().substring(0, 2).toLowerCase()
						.equals(auto_am_build_location.substring(0, 2))) {
				} else {
					write.append(auto_am_build_location.substring(0, 2)
							+ "\r\n");
				}
				write.append("mvn\r\n");
			} else if (createMapper.get(i).equals("build_ims")) {
				write.append("@ECHO off \r\n");
				write.append("echo am_building\r\n");
				write.append("cd " + auto_ims_build_location + "\r\n");
				if (file.getAbsolutePath().substring(0, 2).toLowerCase()
						.equals(auto_ims_build_location.substring(0, 2))) {
				} else {
					write.append(auto_ims_build_location.substring(0, 2)
							+ "\r\n");
				}
				write.append("mvn\r\n");
			} else if (createMapper.get(i).equals("install")) {
				write.append("@ECHO off\r\n");
				write.append("cd " + auto_install_location + "\r\n");
				if (file.getAbsolutePath().substring(0, 2).toLowerCase()
						.equals(auto_install_location.substring(0, 2))) {
				} else {
					write.append(auto_install_location.substring(0, 2) + "\r\n");
				}
				write.append("mvn InstallAM:installAll\r\n");
			} else if (createMapper.get(i).equals("update_from_p4")) {
				write.append("@ECHO off\r\n");
				write.append("cd " + auto_perforce_install_location + "\r\n");
				write.append("echo "+auto_get_password+" | p4 -p "+ auto_getHostAndPort +" -c "+ auto_get_workspace_name+" -u "+auto_get_userName+" login -a \r\n");
				if(auto_sync_to_version.equals("latest"))
					write.append("p4 -p "+ auto_getHostAndPort +" -c "+ auto_get_workspace_name+" -u "+auto_get_userName+" sync "+auto_sync_this_folder+"...#head");
				else
					write.append("p4 -p "+ auto_getHostAndPort +" -c "+ auto_get_workspace_name+" -u "+auto_get_userName+" sync "+auto_sync_this_folder+"...#"+auto_sync_to_version);
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

	private void fetchFromConfig() throws IOException {
		File f = new File("C:\\Auto\\config.properties");
		System.out.println(f.getAbsolutePath());
		FileInputStream fis = new FileInputStream(f);
		Properties prop = new Properties();
		prop.load(fis);
		Set<Object> keys = prop.keySet();
		for (Object key : keys) {
			String k = key.toString();
			String value = prop.getProperty(k);
			configMapper.put(k.toLowerCase(), value.toLowerCase());
		}
		auto_am_build_location = configMapper.get("am_build_location");
		auto_ims_build_location = configMapper.get("ims_build_location");
		auto_install_location = configMapper.get("install_location");
		auto_perforce_install_location = configMapper
				.get("perforce_install_location");
		auto_start_ims_server_location = configMapper
				.get("start_ims_server_location");
		auto_start_oc_server_location = configMapper
				.get("start_oc_server_location");
		auto_sync_this_folder = configMapper.get("sync_this_folder");
		auto_sync_to_version = configMapper.get("version"); 
		auto_get_userName = configMapper.get("p4username");
		auto_get_password = configMapper.get("p4password");
		auto_get_workspace_name = configMapper.get("p4workspace");
		auto_getHostAndPort = configMapper.get("p4clienthostandport");
	}
}

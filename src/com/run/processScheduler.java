package com.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class processScheduler {

	final String PROCESS = "com.tptech.SmtOscar_Main";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		processScheduler main = new processScheduler();
		main.init();
	}
	
	private void init(){
				
		String pid = "";		
		pid = getPid();
		
		if( pid == null || pid.equals("")){			
			startProcess();
		}
		
	}
	
	
	public String getPid(){
		
		ArrayList<String> input = new ArrayList<String>();
		
		String line;
		String pid = "";
		try {
			Process process = Runtime.getRuntime().exec("jps -l");
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			System.out.println("Checking Process..");
			while((line = in.readLine()) != null){
				input.add(line);
				String[] arrLine = line.split(" ");
				if(arrLine.length == 2){
					if( arrLine[1] .equals(PROCESS)){ // Find Process ID
						pid = arrLine[0];
						System.out.println("PID = "+ pid);						
						break;
					}
				}			
			}
			
			BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while (true) {
				String errLine = err.readLine();				
				if (errLine == null){
					break;
				}else{
					System.out.println("[ERR]==="+errLine);
				}
			}
			process.waitFor();
			if( pid == null || pid.equals("")){
				System.out.println("Can not find PID.");
			}
			System.out.println("Checking Process End.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}
	
	
	private void startProcess(){
		
		try {
			System.out.println("START SmtOscar Process ..");
			
			//Process process = Runtime.getRuntime().exec("./startOpirus.bat");
			Process process = Runtime.getRuntime().exec("java com.tptech.SmtOscar_Main");
			System.out.println("START Process End.");
			//System.exit(0);
			/*BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while (true) {
				String errLine = err.readLine();				
				if (errLine == null){
					break;
				}else{
					System.out.println(errLine);
				}
			}
			
			*/
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

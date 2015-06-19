/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hf_master;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author User
 */
public class bobbyXML {

    public static void main(String[] args) throws IOException {

	String str = " <columnname:the_actual_column_name a bunch of crap = ";
	String str2 = " <shared:patient_id cde=\"\" owner=\"TSS\" procurement_status=\"Completed\" restricted=\"false\" xsd_ver=\"2.4\">A1PV</shared:patient_id>";

	String pattern2 = "";
	String patternString = "(.*)(<\\w*:)(\\w*)(.*>)(.*)(<.*)";				//".*<(\\w*):(\\w*)(^>*)>(\\w*)<.*";
	Pattern pattern = Pattern.compile(patternString);
	Matcher m2 = pattern.matcher(str2);

	if (m2.find()) {
//	    System.out.println("0: "+ m2.group(0));
//	    System.out.println("1: "+ m2.group(1));
//	    System.out.println("2: "+ m2.group(2));
	    System.out.println("3: " + m2.group(3));	//
//	    System.out.println("4: "+ m2.group(4));
	    System.out.println("5: " + m2.group(5));	//
//	    System.out.println("6: "+ m2.group(6));
	}

//	System.exit(0);
	//make list of maps<String col, String value>
	//for each file in a,
	//	fill map
	//	add map to list
	//make new set of columns.  convert to list of columns<String>
	//print header of columns
	//for each map
	//	for each column key
	//	    write value + "," to output line
	//	print map's output string data line

	File dir1 = new File("C:\\Users\\User\\Downloads\\TCGA_SKCMxmls\\TCGA_SKCMxmls");
	File dir2 = new File("C:\\Users\\User\\Downloads\\ThyroidCancerXMLs - Copy\\ThyroidCancerXMLs - Copy");

	List<File> files_TCGA_SKCMxmls = Arrays.asList(dir1.listFiles());
	
	System.out.println(files_TCGA_SKCMxmls.size());
	System.out.println(Arrays.asList(dir1.listFiles()).size());
	System.out.println(Arrays.asList(dir2.listFiles()).size());
	System.exit(0);
	
	
//	File[] filesAr1 = dir1.listFiles();
//	File[] filesAr2 = dir2.listFiles();
//
//	List<File> files1 = Arrays.asList(filesAr1);
//	List<File> files2 = Arrays.asList(filesAr2);
//	files.addAll(files1);
//	files.addAll(files2);

	List<File> dirs = new ArrayList();
	dirs.add(dir1);
	dirs.add(dir2);

	for (File dir : dirs) {

	    List<File> files = Arrays.asList(dir.listFiles());
	    List<Map<String, String>> maps = new ArrayList();

	    int counter = 0;
	    for (File file : files) {
		System.out.println(counter++ + " " + file.getAbsolutePath());
//	    if (counter > 10) break;
		Map<String, String> map = new HashMap();
//	     System.out.println(file.getTotalSpace());
		List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		for (String line : lines) {
		    Matcher m = pattern.matcher(line);
		    if (m.find()) {
			map.put(m.group(3), m.group(5));
//		    System.out.println(m.group(3));
		    }
		}
//	    if (map.keySet().)
		maps.add(map);

		boolean foundValue = false;
		for (String value : map.values()) {
		    if (!value.isEmpty())
			foundValue = true;
		}
		if (!foundValue)
		    System.out.println("no values found");

//	    System.out.println(map.keySet().size());
	    }
	    Set<String> columnsSet = new HashSet();
	    for (Map map : maps) {
		columnsSet.addAll(map.keySet());
//	    System.out.println(columnsSet.size());
	    }
	    List<String> columns = new ArrayList();
	    columns.addAll(columnsSet);
	    Collections.sort(columns);
	    StringBuilder sb = new StringBuilder();
	    for (String col : columns) {
		sb.append(col);
		sb.append("\t");
	    }
	    System.out.println(sb.toString());

	    List<String> outputLines = new ArrayList();
	    outputLines.add(sb.toString());

	    for (Map map : maps) {
		sb = new StringBuilder();
		for (String colKey : columns) {
		    sb.append(map.containsKey(colKey) ? map.get(colKey) : "");
		    sb.append("\t");
		}
		outputLines.add(sb.toString());
	    }
	    String outputFileName = "C:\\temp\\bobbyOutput " + dir.getName() + ".txt";
	    Files.write(Paths.get(outputFileName), outputLines, StandardCharsets.UTF_8);

	}
	//make list of maps<String col, String value>
	//for each file in a,
	//	fill map
	//	add map to list
	//make new set of columns.  convert to list of columns<String>
	//print header of columns
	//for each map
	//	for each column key
	//	    write value + "," to output line
	//	print map's output string data line

    }

}

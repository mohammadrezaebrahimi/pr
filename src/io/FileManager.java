package io;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	
	final static Charset ENCODING = StandardCharsets.UTF_8;

	public static byte[] readSmallTextFile(String path) throws IOException
	{
		byte[] file = Files.readAllBytes(Paths.get(path));
		return file;
	}
	
	 void writeSmallTextFile(List<String> aLines, String aFileName) throws IOException {
		    Path path = Paths.get(aFileName);
		    Files.write(path, aLines, ENCODING);
		  }
	
	public List<String> readTextFileByLine(String aFileName) throws IOException {
	    Path path = Paths.get(aFileName);
	    return Files.readAllLines(path, ENCODING);
	  }
	
	public static List<String> readLargeFile(String path) throws IOException
	{
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), ENCODING)){
	      String line = null;
	      List<String> lines = new ArrayList<String>();
	      while ((line = reader.readLine()) != null) {
	        //process each line in some way
	    	  System.out.println(line);
	        //Do something
	    	  lines.add(line);
	      }
	      return lines;
	    }
	}
	
	public static Double[][] readLargeFileAsArray(String path) throws IOException
	{
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), ENCODING)){
	      String line = null;
	      List<String> lines = new ArrayList<String>();
	      while ((line = reader.readLine()) != null) {
	        //process each line in some way
	    	  System.out.println(line);
	        //Do something
	    	  lines.add(line);
	      }
	      //TODO: return lines;
	      return null;
	    }
	}
	
	public static int[][] readBinaryFiles(String path) throws IOException{
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), ENCODING)){
		      String line = null;
		      List<String> lines = new ArrayList<String>();
		      while ((line = reader.readLine()) != null) {
		        //process each line in some way
		    	  //System.out.println(line);
		        //Do something
		    	  lines.add(line);
		      }
		      //copy the strings in to twoDArr
		      int[][] twoDArr = new int[lines.get(0).length()][lines.size()];
		      int i=0;
		      for (String s : lines) {
		    	  for (int j=0; j<s.length(); j++) {
		    		  twoDArr[i][j] = (s.charAt(j)=='1')? 1:0;
				}
		    	  i++;
		      }
		      return twoDArr;
		}
	}
	
	public static void printBinaryImage(int[][] image){
		for (int i = 0;  i< image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				System.out.print(image[i][j]);
				if(j==image[0].length-1)
					System.out.print("\n");
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		//int [][] a = readBinaryFiles("E:\\University\\PR\\Assignment2\\image8.txt");
		int [][] a = readBinaryFiles("src/image8.txt");
		printBinaryImage(a);
	}
	

}

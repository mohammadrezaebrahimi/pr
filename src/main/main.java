package main;

import io.FileManager;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

public class main {
	
	
	static int[][] paddledImage = null , smoothedImage =null , peeledImage = null , normalizedImage =null;
	
	public static void main(String[] args) throws IOException {
		///////////////////////GrayScaleImages
		//FileManager.readLargeFile("E:\\University\\PR\\Lab\\1\\Binary Image Data.txt");
		//smooth the file

		///////////////////////////Binary Images//////////////////////
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	UserInterface ex = new UserInterface();
            	ex.initUI();
            }
        });
		//printBinaryImage(paddledImage);
		/*
		System.out.println("The center of gravity for this pattern is: X=" + CalculateXCenter(normalizedImage)+ 
				", Y=" + CalculateYCenter(normalizedImage));
		int[][] peeledImage = extractSkeleton(normalizedImage);
		printBinaryImage(peeledImage);*/
	}


	public static void getInputFilePath(String path) throws IOException {
		int[][] image = FileManager.readBinaryFiles(path);
		paddledImage = paddleImage(image);
		
	}

	public static int[][] skeletonization(int[][] outputMatrix) throws IOException{
		
		peeledImage = extractSkeleton(outputMatrix);
		BinaryImageToFile bitf = new BinaryImageToFile();
		bitf.writetoFile(normalizedImage,"SKELETONIZED IMAGE.txt");
		String name = "SKELETONIZED IMAGE.txt";
		fileImage(name);
		
		System.out.println("#########SKELETONIZED Image:#######");
		printBinaryImage(peeledImage);
		return peeledImage;
	}
	
	
	public static int[][] normalization(int[][] outputMatrix) throws IOException{
		
		normalizedImage = normalize(outputMatrix);
		
		BinaryImageToFile bitf = new BinaryImageToFile();
		bitf.writetoFile(normalizedImage,"NORMALIZED IMAGE.txt");
		String name = "NORMALIZED IMAGE.txt";
		fileImage(name);
		System.out.println("#########Normalized Image:#######");
		printBinaryImage(normalizedImage);
		return normalizedImage;
		
	}

	public static int[][] smoothing() throws IOException{
		 smoothedImage = smooth(paddledImage);
		BinaryImageToFile bitf = new BinaryImageToFile();
		bitf.writetoFile(smoothedImage,"SMOOTHED IMAGE.txt");
		String name = "SMOOTHED IMAGE.txt";
		fileImage(name);
		System.out.println("#########Smoothed Image:#######");
		printBinaryImage(smoothedImage);
		return smoothedImage;
		
	}
	private static void fileImage(String name) throws IOException {

		int h = 0, w = 0;
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(name))));
        //calculate dimension of the binary input image file
        w = br1.readLine().length();
        h++;
        while (br1.readLine() != null) {
            h++;
        }
        BinaryToImage obj1 = new BinaryToImage(w, h, name);
        obj1.convertToImage(obj1);
	}


	///////////////////////BinaryImageUtilities///////////////////////
	private static int[][] paddleImage(int[][]image){
		int newRow = image.length+2;
		int newCol = image[0].length+2;
		int[][] newImage = new int[newRow][newCol];
		
		//copy the original image to the paddledImage
		for (int i = 0; i < newImage.length; i++) {
			for (int j = 0; j < newImage[0].length; j++) {
				if (i==0 || j==0 || i==newImage.length-1 || j==newImage[0].length-1)
					newImage[i][j] = 0;
				else
					newImage[i][j] = image[i-1][j-1];
			}			
		}
		return newImage;
	}
	
	public static int[][] smooth(int[][] image)
	{
		int[][] Result1 = duplicateImage(image);
		//Do for every pixel of Image
		for (int i =1; i<image.length-1; i++)
		{
			for (int j =1; j<image[0].length-1; j++)
			{					
				//get the pixel neighbors in the form of matrix
				int[][] neighbors = getNeighborsinBinaryImage(image, i, j);

				//Do the actual smoothing within the mask
								
				boolean a = (neighbors[0][0]==1)? true:false;
				boolean b = (neighbors[0][1]==1)? true:false;
				boolean c = (neighbors[0][2]==1)? true:false;
				boolean d = (neighbors[1][0]==1)? true:false;
				boolean x = (neighbors[1][1]==1)? true:false;
				boolean e = (neighbors[1][2]==1)? true:false;
				boolean f = (neighbors[2][0]==1)? true:false;
				boolean g = (neighbors[2][1]==1)? true:false;
				boolean h = (neighbors[2][2]==1)? true:false;
				
				////////////First Filter: Fill/Thin islands (two iland in image 8 should be filled)
				boolean X = (!x&(a&b&c&d&e&f&g&h)) | (x&(a|b|c|d|e|f|g|h));
				int targetOriginalValue = (X==true)? 1:0;
				//replace new target value
				Result1[i][j] = targetOriginalValue;
			}
		}
		
	////////////Second Mask: Filling (All Direct/Diagonal Neighbors should be 1)
	int[][] Result2 = duplicateImage(Result1);
	for (int i =1; i<image.length-1; i++)
	{
		for (int j =1; j<image[0].length-1; j++)
		{					
			//get the pixel neighbors in the form of matrix
			int[][] neighbors = getNeighborsinBinaryImage(Result1, i, j);

			//Do the actual smoothing within the mask
							
			boolean a = (neighbors[0][0]==1)? true:false;
			boolean b = (neighbors[0][1]==1)? true:false;
			boolean c = (neighbors[0][2]==1)? true:false;
			boolean d = (neighbors[1][0]==1)? true:false;
			boolean x = (neighbors[1][1]==1)? true:false;
			boolean e = (neighbors[1][2]==1)? true:false;
			boolean f = (neighbors[2][0]==1)? true:false;
			boolean g = (neighbors[2][1]==1)? true:false;
			boolean h = (neighbors[2][2]==1)? true:false;
							
			boolean X2 = x|(b&d|b&e|d&g|e&g|d&e|b&g|a&h|c&g);
			int targetOriginalValue2 = (X2==true)? 1:0;
			//replace new target value
			Result2[i][j] = targetOriginalValue2;				
		}
	}
	//Third mask: second iteration of heavy filling
	int[][] Result3 = duplicateImage(Result2);
	for (int i =1; i<image.length-1; i++)
	{
		for (int j =1; j<image[0].length-1; j++)
		{					
			//get the pixel neighbors in the form of matrix
			int[][] neighbors = getNeighborsinBinaryImage(Result2, i, j);

			//Do the actual smoothing within the mask
			boolean a = (neighbors[0][0]==1)? true:false;
			boolean b = (neighbors[0][1]==1)? true:false;
			boolean c = (neighbors[0][2]==1)? true:false;
			boolean d = (neighbors[1][0]==1)? true:false;
			boolean x = (neighbors[1][1]==1)? true:false;
			boolean e = (neighbors[1][2]==1)? true:false;
			boolean f = (neighbors[2][0]==1)? true:false;
			boolean g = (neighbors[2][1]==1)? true:false;
			boolean h = (neighbors[2][2]==1)? true:false;
							
			boolean X3 = x|(b&d|b&e|d&g|e&g|d&e|b&g|a&h|c&g);
			int targetOriginalValue2 = (X3==true)? 1:0;
			//replace new target value
			Result3[i][j] = targetOriginalValue2;				
		}
	}
	//Fourth mask: third iteration of heavy filling
		int[][] Result4 = duplicateImage(Result3);
		for (int i =1; i<image.length-1; i++)
		{
			for (int j =1; j<image[0].length-1; j++)
			{					
				//get the pixel neighbors in the form of matrix
				int[][] neighbors = getNeighborsinBinaryImage(Result3, i, j);

				//Do the actual smoothing within the mask
				boolean a = (neighbors[0][0]==1)? true:false;
				boolean b = (neighbors[0][1]==1)? true:false;
				boolean c = (neighbors[0][2]==1)? true:false;
				boolean d = (neighbors[1][0]==1)? true:false;
				boolean x = (neighbors[1][1]==1)? true:false;
				boolean e = (neighbors[1][2]==1)? true:false;
				boolean f = (neighbors[2][0]==1)? true:false;
				boolean g = (neighbors[2][1]==1)? true:false;
				boolean h = (neighbors[2][2]==1)? true:false;
								
				boolean X3 = x|(b&d|b&e|d&g|e&g|d&e|b&g|a&h|c&g);
				int targetOriginalValue2 = (X3==true)? 1:0;
				//replace new target value
				Result4[i][j] = targetOriginalValue2;				
			}
		}
	//Fourth Mask: First Light Thinning
	int[][] Result5 = duplicateImage(Result4);
	for (int i =1; i<image.length-1; i++)
	{
		for (int j =1; j<image[0].length-1; j++)
		{					
			//get the pixel neighbors in the form of matrix
			int[][] neighbors = getNeighborsinBinaryImage(Result4, i, j);

			//Do the actual smoothing within the mask
							
			boolean a = (neighbors[0][0]==1)? true:false;
			boolean b = (neighbors[0][1]==1)? true:false;
			boolean c = (neighbors[0][2]==1)? true:false;
			boolean d = (neighbors[1][0]==1)? true:false;
			boolean x = (neighbors[1][1]==1)? true:false;
			boolean e = (neighbors[1][2]==1)? true:false;
			boolean f = (neighbors[2][0]==1)? true:false;
			boolean g = (neighbors[2][1]==1)? true:false;
			boolean h = (neighbors[2][2]==1)? true:false;
							
			boolean X4 = x&((a|b|d) & (e|g|h) | (b|c|e)&(d|f|g));
			int targetOriginalValue2 = (X4==true)? 1:0;
			//replace new target value
			Result5[i][j] = targetOriginalValue2;				
		}
	}
	//Fifth mask: 2nd iteration of Light Thinning
	int[][] Result6 = duplicateImage(Result5);
	for (int i =1; i<image.length-1; i++)
	{
		for (int j =1; j<image[0].length-1; j++)
		{					
			//get the pixel neighbors in the form of matrix
			int[][] neighbors = getNeighborsinBinaryImage(Result5, i, j);

			//Do the actual smoothing within the mask
							
			boolean a = (neighbors[0][0]==1)? true:false;
			boolean b = (neighbors[0][1]==1)? true:false;
			boolean c = (neighbors[0][2]==1)? true:false;
			boolean d = (neighbors[1][0]==1)? true:false;
			boolean x = (neighbors[1][1]==1)? true:false;
			boolean e = (neighbors[1][2]==1)? true:false;
			boolean f = (neighbors[2][0]==1)? true:false;
			boolean g = (neighbors[2][1]==1)? true:false;
			boolean h = (neighbors[2][2]==1)? true:false;
							
			boolean X5 = x&((a|b|d) & (e|g|h) | (b|c|e)&(d|f|g));
			int targetOriginalValue2 = (X5==true)? 1:0;
			//replace new target value
			Result6[i][j] = targetOriginalValue2;				
		}
	}

//		//**************************Filling & Thinning Simultaneously***********
//		////////////Second Mask: with at least 2 identical pixels
//		////////////among Direct/Diagonal Neighbors
//		int[][] Result2 = duplicateImage(Result1);
//		for (int i =1; i<image.length-1; i++)
//		{
//			for (int j =1; j<image[0].length-1; j++)
//			{					
//				//get the pixel neighbors in the form of matrix
//				int[][] neighbors = getNeighborsinBinaryImage(Result1, i, j);
//
//				//Do the actual smoothing within the mask
//								
//				boolean a = (neighbors[0][0]==1)? true:false;
//				boolean b = (neighbors[0][1]==1)? true:false;
//				boolean c = (neighbors[0][2]==1)? true:false;
//				boolean d = (neighbors[1][0]==1)? true:false;
//				boolean x = (neighbors[1][1]==1)? true:false;
//				boolean e = (neighbors[1][2]==1)? true:false;
//				boolean f = (neighbors[2][0]==1)? true:false;
//				boolean g = (neighbors[2][1]==1)? true:false;
//				boolean h = (neighbors[2][2]==1)? true:false;
//								
//				boolean X2 = x&(b&d|b&e|d&g|e&g|d&e|b&g)|
//						     (!x)&((b|d)&(b|e)&(d|g)&(e|g)&(d|e)&(b|g));
//				int targetOriginalValue2 = (X2==true)? 1:0;
//				//replace new target value
//				Result2[i][j] = targetOriginalValue2;				
//			}
//		}
//		
//		int[][] Result3 = duplicateImage(Result2);
//		for (int i =1; i<image.length-1; i++)
//		{
//			for (int j =1; j<image[0].length-1; j++)
//			{					
//				//get the pixel neighbors in the form of matrix
//				int[][] neighbors = getNeighborsinBinaryImage(Result2, i, j);
//
//				//Do the actual smoothing within the mask
//								
//				boolean a = (neighbors[0][0]==1)? true:false;
//				boolean b = (neighbors[0][1]==1)? true:false;
//				boolean c = (neighbors[0][2]==1)? true:false;
//				boolean d = (neighbors[1][0]==1)? true:false;
//				boolean x = (neighbors[1][1]==1)? true:false;
//				boolean e = (neighbors[1][2]==1)? true:false;
//				boolean f = (neighbors[2][0]==1)? true:false;
//				boolean g = (neighbors[2][1]==1)? true:false;
//				boolean h = (neighbors[2][2]==1)? true:false;
//								
//				boolean X3 = x&(a|b|c|d|e|f|g|h);
//				int targetOriginalValue2 = (X3==true)? 1:0;
//				//replace new target value
//				Result3[i][j] = targetOriginalValue2;				
//			}
//		}

		return Result6;
	}
	
	public static int[][] normalize(int[][] image){
		int[][] plane = new int[24][25];
		//double strechFactor = 1.5;
		double Sx = 25.0/image[0].length;
		double Sy = 24.0/image.length;
		for (int i =0; i<plane.length; i++)
		{
			for (int j =0; j<plane[0].length; j++)
			{					
				int correspondingI = (int)Math.floor((i/Sy));
				int correspondingJ = (int)Math.floor(j/Sx);
				
				plane[i][j] = image[correspondingI][correspondingJ]; 
			}
		}
		return plane;
	}
	
	public static int CalculateXCenter(int[][] image){
		double M00 = sumOfElements(image); //M00 is actually the sum of pixel values 
		double M10 = 0;
		for (int i =0; i<image.length; i++)
		{
			for (int j =0; j<image[0].length; j++)
			{					
				 M10+= i * image[i][j];
			}
		}
		if (M00==0)
				return 0;
		else return (int)Math.floor(Math.round(M10/M00));
	}
	
	public static int CalculateYCenter(int[][] image){
		double M00 = sumOfElements(image); //M00 is actually the sum of pixel values 
		double M01 = 0;
		for (int i =0; i<image.length; i++)
		{
			for (int j =0; j<image[0].length; j++)
			{					
				 M01+= j * image[i][j];
			}
		}
		if (M00==0)
				return 0;
		else return (int)Math.floor(Math.round(M01/M00));
	}
	
	public static int[][] extractSkeleton(int[][] image){
		int[][] skeleton = duplicateImage(image);
		boolean flag = true; 
		while(flag) {
			flag = false;
			List<int[]> deletionList = new ArrayList<int[]>();
			//////////////////////////////////STEP1
			//Do for every pixel of Image
			for (int i =1; i<image.length-1; i++)
			{
				for (int j =1; j<image[0].length-1; j++)
				{					
					//get the pixel neighbors in the form of matrix
					int[][] neighbors = getNeighborsinBinaryImage(skeleton, i, j);

					//////evaluate the mask based on Zhang Suen first step criteria
					//calculate BP
					int BP = calculateBP(neighbors);
					//calculate AP				
					int AP = calculateAP(neighbors);
					//check first-group criteria
					if (BP>=2 && BP<=6){
						if (AP == 1){
							boolean P9 = (neighbors[0][0]==1)? true:false;
							boolean P2 = (neighbors[0][1]==1)? true:false;
							boolean P3 = (neighbors[0][2]==1)? true:false;
							boolean P8 = (neighbors[1][0]==1)? true:false;
							boolean x = (neighbors[1][1]==1)? true:false;
							boolean P4 = (neighbors[1][2]==1)? true:false;
							boolean P7 = (neighbors[2][0]==1)? true:false;
							boolean P6 = (neighbors[2][1]==1)? true:false;
							boolean P5 = (neighbors[2][2]==1)? true:false;
							if(!(P2&P4&P6)){
								if (!(P4&P6&P8)){
									//mark pixel for deletion
									int deleteCandidate[] = new int[2];
									deleteCandidate[0]=i;
									deleteCandidate[1] = j;
									deletionList.add(deleteCandidate);
								}
							}
						}
					}
				}
			}
			//delete candidate pixels
			for (int[] pixel : deletionList) {
				//stop criterion
				if(skeleton[pixel[0]][pixel[1]]==1)
					flag =true;
				skeleton[pixel[0]][pixel[1]] =0;
			}
			//make list empty
			deletionList.clear();
			//////////////////////////////////STEP2
			//Do for every pixel of Image
			for (int i =1; i<image.length-1; i++)
			{
				for (int j =1; j<image[0].length-1; j++)
				{					
					//get the pixel neighbors in the form of matrix
					int[][] neighbors = getNeighborsinBinaryImage(skeleton, i, j);

					//////evaluate the mask based on Zhang Suen second step criteria
					//calculate BP
					int BP = calculateBP(neighbors);
					//calculate AP				
					int AP = calculateAP(neighbors);
					//check first-group criteria
					if (BP>=2 && BP<=6){
						if (AP == 1){
							boolean P9 = (neighbors[0][0]==1)? true:false;
							boolean P2 = (neighbors[0][1]==1)? true:false;
							boolean P3 = (neighbors[0][2]==1)? true:false;
							boolean P8 = (neighbors[1][0]==1)? true:false;
							boolean x = (neighbors[1][1]==1)? true:false;
							boolean P4 = (neighbors[1][2]==1)? true:false;
							boolean P7 = (neighbors[2][0]==1)? true:false;
							boolean P6 = (neighbors[2][1]==1)? true:false;
							boolean P5 = (neighbors[2][2]==1)? true:false;
							if(!(P2&P4&P8)){
								if (!(P2&P6&P8)){
									//mark pixel for deletion
									int deleteCandidate[] = new int[2];
									deleteCandidate[0]=i;
									deleteCandidate[1] = j;
									deletionList.add(deleteCandidate);
								}
							}
						}
					}
				}
			}
			//delete candidate pixels
			for (int[] pixel : deletionList) {
				//stop criterion
				if(skeleton[pixel[0]][pixel[1]]==1)
					flag =true;
				skeleton[pixel[0]][pixel[1]] =0;
			}
		}//while
		return skeleton;
	}
	
	private static int calculateBP(int[][] mask){
		int sum =0;
		for (int i = 0; i < mask.length; i++) {
			for (int j = 0; j < mask[0].length; j++) {
				if (i==1 && j==1)// do not take into account the target in neighbors
					continue;
				else
					sum+=mask[i][j];
			}
		}

		return sum;
	}
	
	private static int calculateAP(int[][] mask){
		int counter =0;
		String sequence ="";
		sequence += mask[0][0];
		sequence += mask[0][1];
		sequence += mask[0][2];
		sequence += mask[1][2];
		sequence += mask[2][2];
		sequence += mask[2][1];
		sequence += mask[2][0];
		sequence += mask[1][0];
		sequence += mask[0][0];
		// calculate number of 0->1s among the sequence of neighbors
		String subStr = "01";
		return (sequence.length() - sequence.replace(subStr, "").length()) / subStr.length();
	}


	
	private static int[][] getNeighborsinBinaryImage(int[][] image, int i, int j){
		int[][] neighbors = new int[3][3];
		
		neighbors[0][0] = image[i-1][j-1];
		neighbors[0][1] = image[i-1][j];
		neighbors[0][2] = image[i-1][j+1];
		neighbors[1][0] = image[i][j-1];
		neighbors[1][1] = image[i][j];
		neighbors[1][2] = image[i][j+1];
		neighbors[2][0] = image[i+1][j-1];
		neighbors[2][1] = image[i+1][j];
		neighbors[2][2] = image[i+1][j+1];
		
		return neighbors;
	}
	
	private static int[][] duplicateImage(int[][] image){
		int[][] res = new int[image.length][image[0].length];
		for (int i = 0; i < image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				res[i][j] = image[i][j];
			}
		}
		return res;
	}	

	private static void printBinaryImage(int[][] image){
		for (int i = 0;  i< image.length; i++) {
			for (int j = 0; j < image[0].length; j++) {
				System.out.print(image[i][j]);
				if(j==image[0].length-1)
					System.out.print("\n");

			}
		}
	}
	
	private static double sumOfElements(int[][] image){
		double sum=0;
		for (int i=0; i<image.length; i++)
			for (int j=0; j<image[0].length; j++){
				sum+= image[i][j];
			}
		return sum;
	}
	
	
	// slant correction 
	public static int[][] slant_calculation(int[][] matrix, int r, int c) throws IOException {
        int B1 = 0;
        
        int B2 = start_point_image(matrix, r, c);
        int B3 = last_point_image(matrix, r, c);
        int B4 = r - 1;
        double A = B3 - B2;
        double B = (B4 + B1 - B3 - B2) / 2;
        double theta = Math.atan((B / A));
        int[][] slant_matrix = slant_operation(matrix, r, c, theta);
        
        BinaryImageToFile bitf = new BinaryImageToFile();
		bitf.writetoFile(slant_matrix,"SLANT CORRECTED IMAGE.txt");
		String name = "SLANT CORRECTED IMAGE.txt";
		fileImage(name);
		
		System.out.println("#########SLANT CORRECTED Image:#######");
		
		printBinaryImage(slant_matrix);
		
        return slant_matrix;
        
    }
    
    public static int start_point_image(int[][] matrix, int r, int c) {
        for (int i = r - 1; i >= 0; --i) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j] == 1) {
                    return ((r - 1) - i);
                }
            }
        }
        return 0;
    }
    
    public static int last_point_image(int[][] matrix, int r, int c) {
        int last = 0;
        for (int i = r - 1; i >= 0; --i) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j] == 1) {
                    last = i;
                    break;
                }
            }
        }
        return ((r - 1) - last);
    }
    
    public static int[][] slant_operation(int[][] matrix, int r, int c,
                                          double theta) {
        int[][] slant_matrix = new int[r][c];
        for (int x = 0; x < r; x++) {
            for (int y = 0; y < c; y++) {
                if (matrix[x][y] == 1) {
                    int new_x = (int) (x - y * Math.tan(theta));
                    slant_matrix[new_x][y] = 1;
                }
                
            }
        }
        return slant_matrix;
    }

}

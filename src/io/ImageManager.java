package io;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.BinaryImageToFile;
import main.BinaryToImage;
import main.main;
import mnist.tools.*;

public class ImageManager {
	
	public static BufferedImage toBufferedImage(Image image) {
	    if (image instanceof BufferedImage) {
	        // Return image unchanged if it is already a BufferedImage.
	        //return (BufferedImage) image;
	    }

	    // Ensure image is loaded.
	    //image = new ImageIcon(image).getImage();

	    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),  BufferedImage.TYPE_USHORT_GRAY);
	    Graphics g = bufferedImage.createGraphics();
	    g.drawImage(image, 0, 0, null);
	    g.dispose();

	    return bufferedImage;
	}	
	
	//public static void main(String[] args) throws IOException {
//		///////////////////////////////////////JPG and PNG////////////////////////////////
//		String path ="E:\\University\\PR\\EnglishHnd\\English\\Hnd\\Img\\Sample001\\img001-001.png";
//		//String path ="C:\\zero.jpg";
//		BufferedImage img = null;
//		try {
//		    img = ImageIO.read(new File(path));
//		    //if it is not buffered image
//		    BufferedImage bufImage = toBufferedImage(img);		    
//		} catch (IOException e) {
//			System.out.println("Error: Could not load the Image!!!!!!!");
//		}
//		int w = img.getWidth(); //1200
//		int h = img.getHeight(); //900
//
//		List<String> LinesList = new ArrayList<>();
//		int[][] array = new int[h][w];
//		for (int j = 0; j < h; j++) {
//			String line = "";
//		    for (int k = 0; k < w; k++) {
//		        array[j][k] = (byte) (img.getRGB(k, j) == -1 ? 0 : 1);
//		        line+=Integer.toString(array[j][k], 10);
//		        if (j==100)
//		        	System.out.println(line);
//		    }
//		LinesList.add(line);
//		}
//		FileManager fm = new FileManager();
//		fm.writeSmallTextFile(LinesList, "C:\\img.txt");
//////////////////////////////////////////////////////////////////MNIST//////////////
		
	//	startMNISTreaderingProcess();
		
	//}
	public  void startMNISTreaderingProcess() throws IOException {
		// TODO Auto-generated method stub
		MnistManager mnistmgr = new MnistManager("E:\\MNIST\\train-images.idx3-ubyte",
				"E:\\MNIST\\train-labels.idx1-ubyte");
		MnistImageFile mnistImgFile = mnistmgr.getImages();
		
		int[][] img = mnistImgFile.readImage();
		for (int i=0; i<1; i++){
			mnistImgFile.nextImage();
			img = mnistImgFile.readImage();
			int[][] binarized = binarize(img);
			
			
			// make a dir for MNIST images
			
				
			// write this to file
			BinaryImageToFile bitf = new BinaryImageToFile();
			bitf.writetoFile(binarized,"MNISTIMAGE.txt");
			int h = 0, w = 0;
	        BufferedReader br1 = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream("MNISTIMAGE.txt"))));
	        //calculate dimension of the binary input image file
	        w = br1.readLine().length();
	        h++;
	        while (br1.readLine() != null) {
	            h++;
	        }
	        
	      // converting input MNIST binary image file to jpg image 
	        BinaryToImage obj1 = new BinaryToImage(w, h, "MNISTIMAGE.txt");
	        //obj1.convertToImage(obj1);
	        System.out.println("#######MNIST IMAGE###########");
	        FileManager.printBinaryImage(binarized);
	        
	        main im1 = new main();
	        int[][] outputMatrix = null;
	        
	        im1.getInputFilePath("MNISTIMAGE.txt");
	        
	        outputMatrix = im1.smoothing();
	        
	        outputMatrix = im1.slant_calculation(outputMatrix, outputMatrix.length, outputMatrix[0].length);
	        
	        outputMatrix = im1.normalization(outputMatrix);
	        
	        outputMatrix = im1.skeletonization(outputMatrix);
		}
		
	}

	public  int[][] binarize(int[][]img){
		int[][] binarized = new int[28][28];
		for (int i=0; i<img.length; i++)
			for (int j=0; j<img[0].length; j++){
				binarized[i][j] = (img[i][j]>0)? 1:0;
			}		
		return binarized;
	}
}


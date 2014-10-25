package preprocess;

import utility.Matrixx;
import utility.MatrixOperations;

public class Smoothing {

		
	/**
	 * After the execution of this function the backup matrix would have the new values  
	 * Note: for now we just support 3*3 masks
	 * @param image
	 * @param mask
	 * @return
	 */
	public static Matrixx smooth(Matrixx image, Matrixx mask)
	{
		Matrixx finalResult = null;
		image.duplicate();
		double newValue =0;
		//Do for every pixel of Image
		for (int i =0; i<image.getnRows(); i++)
		{
			for (int j =0; j<image.getnCols(); j++)
			{
				//identify if the pixel is located on the margins
				if (i==0){//located on first row
					
					//truncate mask matrixx (remove the first row)
					Matrixx upperMask = new Matrixx(mask.getnRows()-1, mask.getnCols());
					for (int k=1; k<mask.getnRows(); k++){
						for(int l=0; l<mask.getnCols(); l++){
						upperMask.getTwoDArray()[k][l] = mask.getTwoDArray()[k][l];
						}			
					}
					//get the pixel neighbors in the form of matrix
					Matrixx neighbors = getNeighbors(image, i, j);
					
					//calculate the weights
					double weight = upperMask.sumOfElements();
										
					//do the one-by-one multiplication
					Matrixx productResult = MatrixOperations.scallerProduct(weight, (MatrixOperations.dotProduct(upperMask, neighbors)));
					
					newValue = productResult.AVGOfElements();
					
					//substitute the new pixel value with the original one in the image
					image.getBackUPTwoDArray()[i][j] = newValue;					
					
				}else if (i==image.getnRows()-1){//located on last row
					
					//truncate mask matrixx (remove the last row)
					Matrixx lowerMask = new Matrixx(mask.getnRows()-1, mask.getnCols());
					for (int k=0; k<mask.getnRows()-1; k++){
						for(int l=0; l<mask.getnCols(); l++){
						lowerMask.getTwoDArray()[k][l] = mask.getTwoDArray()[k][l];
						}						
					}
					//get the pixel neighbors in the form of matrix
					Matrixx neighbors = getNeighbors(image, i, j);
					
					//calculate the weights
					double weight = lowerMask.sumOfElements();
										
					//do the one-by-one multiplication
					Matrixx productResult = MatrixOperations.scallerProduct(weight, (MatrixOperations.dotProduct(lowerMask, neighbors)));
					
					newValue = productResult.AVGOfElements();
					
					//substitute the new pixel value with the original one in the image
					image.getBackUPTwoDArray()[i][j] = newValue;					

				}
				else if (j==0){//located on first column

					//truncate mask matrixx (remove the first column)
					Matrixx leftMask = new Matrixx(mask.getnRows(), mask.getnCols()-1);
					for (int k=0; k<mask.getnRows(); k++){
						for(int l=1; l<mask.getnCols(); l++){
						leftMask.getTwoDArray()[k][l] = mask.getTwoDArray()[k][l];
						}						
					}
					//get the pixel neighbors in the form of matrix
					Matrixx neighbors = getNeighbors(image, i, j);
					
					//calculate the weights
					double weight = leftMask.sumOfElements();
										
					//do the one-by-one multiplication
					Matrixx productResult = MatrixOperations.scallerProduct(weight, (MatrixOperations.dotProduct(leftMask, neighbors)));
					
					newValue = productResult.AVGOfElements();
					
					//substitute the new pixel value with the original one in the image
					image.getBackUPTwoDArray()[i][j] = newValue;

				}
				else if (j==image.getnCols()-1){//located on last column
					
					//truncate mask matrixx (remove the last column)
					Matrixx rightMask = new Matrixx(mask.getnRows(), mask.getnCols()-1);
					for (int k=0; k<mask.getnRows(); k++){
						for(int l=0; l<mask.getnCols()-1; l++){
						rightMask.getTwoDArray()[k][l] = mask.getTwoDArray()[k][l];
						}
					}
					//get the pixel neighbors in the form of matrix
					Matrixx neighbors = getNeighbors(image, i, j);
					
					//calculate the weights
					double weight = rightMask.sumOfElements();
										
					//do the one-by-one multiplication
					Matrixx productResult = MatrixOperations.scallerProduct(weight, (MatrixOperations.dotProduct(rightMask, neighbors)));
					
					newValue = productResult.AVGOfElements();
					
					//substitute the new pixel value with the original one in the image
					image.getBackUPTwoDArray()[i][j] = newValue;

				}
				else{ //pixel is not located on the margins
					//get the pixel neighbors in the form of matrix
					Matrixx neighbors = getNeighbors(image, i, j);
					
					//calculate the weights
					double weight = mask.sumOfElements();
										
					//do the one-by-one multiplication
					Matrixx productResult = MatrixOperations.scallerProduct(weight, (MatrixOperations.dotProduct(mask, neighbors)));
					
					newValue = productResult.AVGOfElements();
					
					//substitute the new pixel value with the original one in the image
					image.getBackUPTwoDArray()[i][j] = newValue;
				}
			}
		}
		return new Matrixx(image.getBackUPTwoDArray()) ;
	}
	
	/**
	 * get 3 by 3 neighbors including the target also
	 * @param image
	 * @param i
	 * @param j
	 * @return
	 */
	private static Matrixx getNeighbors(Matrixx image, int i, int j){
		Matrixx neighbors = null;
		
		//margin pixels
		if (i==0 && j!=0 && j!=image.getnCols()-1){	//first row except corners	
			neighbors = new Matrixx(2 ,3 );
			neighbors.getTwoDArray()[0][0] = image.getTwoDArray()[i][j-1];
			neighbors.getTwoDArray()[0][1] = image.getTwoDArray()[i][j];
			neighbors.getTwoDArray()[0][2] = image.getTwoDArray()[i][j+1];
			neighbors.getTwoDArray()[1][0] = image.getTwoDArray()[i+1][j-1];
			neighbors.getTwoDArray()[1][1] = image.getTwoDArray()[i+1][j];
			neighbors.getTwoDArray()[1][2] = image.getTwoDArray()[i+1][j+1];
		}
		else if (i==image.getnRows()-1 && j!=0 && j!=image.getnCols()-1){ // last row except corners
			neighbors = new Matrixx(2, 3);
			neighbors.getTwoDArray()[0][0] = image.getTwoDArray()[i-1][j-1];
			neighbors.getTwoDArray()[0][1] = image.getTwoDArray()[i-1][j];
			neighbors.getTwoDArray()[0][2] = image.getTwoDArray()[i-1][j+1];
			neighbors.getTwoDArray()[1][0] = image.getTwoDArray()[i][j-1];
			neighbors.getTwoDArray()[1][1] = image.getTwoDArray()[i][j];
			neighbors.getTwoDArray()[1][2] = image.getTwoDArray()[i][j+1];
		}
		else if (j==0 && i!=0 && i!=image.getnRows()-1){ //first column except corners
			neighbors = new Matrixx(3, 2);
			neighbors.getTwoDArray()[0][0] = image.getTwoDArray()[i-1][j];
			neighbors.getTwoDArray()[0][1] = image.getTwoDArray()[i-1][j+1];
			neighbors.getTwoDArray()[1][0] = image.getTwoDArray()[i][j];
			neighbors.getTwoDArray()[1][1] = image.getTwoDArray()[i][j+1];
			neighbors.getTwoDArray()[2][0] = image.getTwoDArray()[i+1][j];
			neighbors.getTwoDArray()[2][1] = image.getTwoDArray()[i+1][j+1];
		}
		else if (j==image.getnCols()-1 && i!=0 && i!=image.getnRows()-1){ //last column except corners
			neighbors = new Matrixx(3, 2);
			neighbors.getTwoDArray()[0][0] = image.getTwoDArray()[i-1][j-1];
			neighbors.getTwoDArray()[0][1] = image.getTwoDArray()[i-1][j];
			neighbors.getTwoDArray()[1][0] = image.getTwoDArray()[i][j-1];
			neighbors.getTwoDArray()[1][1] = image.getTwoDArray()[i][j];
			neighbors.getTwoDArray()[2][0] = image.getTwoDArray()[i+1][j-1];
			neighbors.getTwoDArray()[2][1] = image.getTwoDArray()[i+1][j];
		}
		//Corner pixels
		else if (i==0 && j==0){
			neighbors = new Matrixx(2, 2);
			neighbors.getTwoDArray()[0][0]= image.getTwoDArray()[i][j];
			neighbors.getTwoDArray()[0][1]= image.getTwoDArray()[0][1];
			neighbors.getTwoDArray()[1][0]= image.getTwoDArray()[1][0];
			neighbors.getTwoDArray()[1][1]= image.getTwoDArray()[1][1];
		}
		else if (i==0 && j==image.getnCols()-1){
			neighbors = new Matrixx(2, 2);
			neighbors.getTwoDArray()[0][0]= image.getTwoDArray()[0][j-1];
			neighbors.getTwoDArray()[0][1]= image.getTwoDArray()[i][j];
			neighbors.getTwoDArray()[1][0]= image.getTwoDArray()[1][j-1];
			neighbors.getTwoDArray()[1][1]= image.getTwoDArray()[1][j];
		}
		else if (i==image.getnRows()-1 && j==0){
			neighbors = new Matrixx(2, 2);
			neighbors.getTwoDArray()[0][0]= image.getTwoDArray()[i-1][0];
			neighbors.getTwoDArray()[0][1]= image.getTwoDArray()[i-1][1];
			neighbors.getTwoDArray()[1][0]= image.getTwoDArray()[i][j];
			neighbors.getTwoDArray()[1][1]= image.getTwoDArray()[i][1];
		}
		else if (i==image.getnRows()-1 && j==image.getnCols()-1){
			neighbors = new Matrixx(2, 2);
			neighbors.getTwoDArray()[0][0]= image.getTwoDArray()[i-1][j-1];
			neighbors.getTwoDArray()[0][1]= image.getTwoDArray()[i-1][j];
			neighbors.getTwoDArray()[1][0]= image.getTwoDArray()[i][j-1];
			neighbors.getTwoDArray()[1][1]= image.getTwoDArray()[i][j];
		}
		//non margin pixels
		else if (i!=0 && i!=image.getnRows()-1 && j!=0 && j!=image.getnCols()-1){ // non-margin	
			neighbors = new Matrixx(3, 3);
			neighbors.getTwoDArray()[0][0] = image.getTwoDArray()[i-1][j-1];
			neighbors.getTwoDArray()[0][1] = image.getTwoDArray()[i-1][j];
			neighbors.getTwoDArray()[0][2] = image.getTwoDArray()[i-1][j+1];
			neighbors.getTwoDArray()[1][0] = image.getTwoDArray()[i][j-1];
			neighbors.getTwoDArray()[1][1] = image.getTwoDArray()[i][j];
			neighbors.getTwoDArray()[1][2] = image.getTwoDArray()[i][j+1];
			neighbors.getTwoDArray()[2][0] = image.getTwoDArray()[i+1][j-1];
			neighbors.getTwoDArray()[2][1] = image.getTwoDArray()[i+1][j];
			neighbors.getTwoDArray()[2][2] = image.getTwoDArray()[i+1][j+1];
		}
		return neighbors;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	

}

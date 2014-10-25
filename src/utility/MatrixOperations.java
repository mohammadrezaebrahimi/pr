package utility;
import utility.Matrixx;

public class MatrixOperations {

	public static Matrixx multiply(Matrixx first, Matrixx second){
		
		int aRows = first.getnRows();
        int aColumns = first.getnCols();
        int bRows = second.getnRows();
        int bColumns = second.getnCols();
        
        if (aColumns == bRows){
        	Matrixx result = new Matrixx(aRows, bColumns);
        	for (int i=0; i< aRows; i++){
        		for (int j=0; j<bColumns; j++){
        			double sum = 0;
        			for (int k=0; k<aColumns;k++){        				
        				sum += first.getTwoDArray()[i][k]*second.getTwoDArray()[k][j];        				
        			}
        			result.getTwoDArray()[i][j]=sum;
        		}
        	}
        	return result;
        }
        else
        {
        	System.err.println("ATTENSION: Dimention not match!!!");
        	return null;
        }        	
	}
	
	/**
	 * two matrixes should have the same dimensions
	 * @param first
	 * @param second
	 * @return
	 */
	public static Matrixx dotProduct(Matrixx first, Matrixx second)
	{
		Matrixx result = new Matrixx(first.getnRows(), first.getnCols());
		for (int i =0; i<second.getnRows(); i++)
		{
			for (int j =0; j<second.getnCols(); j++)
			{
				result.getTwoDArray()[i][j] = first.getTwoDArray()[i][j]*second.getTwoDArray()[i][j];
			}
		}
		return result;
	}
	
	public static Matrixx scallerProduct(double weight, Matrixx mat){
		Matrixx result = new Matrixx(mat.getnRows(), mat.getnCols());
		for (int i=0; i<mat.getnRows(); i++)
			for (int j=0; j<mat.getnCols(); j++)
				result.getTwoDArray()[i][j] = weight * mat.getTwoDArray()[i][j]; 
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Double[][] A = { { 4.00, 3.00 }, { 2.00, 1.00 } };
		Double[][] B = { { 1.00 }, { 1.00 } };
		
		Matrixx matA = new Matrixx(2, 2);
		matA.setTwoDArray(A);
		matA.setName("matA");
		Matrixx matB = new Matrixx(2, 1);
		matB.setTwoDArray(B);
		matB.setName("matB");
		
		//MatrixOperations MO = new MatrixOperations();
		Matrixx res =  MatrixOperations.multiply(matA, matB);
		res.setName("res");
		res.print();
			
	}

}

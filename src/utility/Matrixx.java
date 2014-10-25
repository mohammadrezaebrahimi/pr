package utility;

public class Matrixx {
	
	private Double [][] TwoDArray;
	private Double [][] BackUPTwoDArray;
	private int nRows;
	private int nCols;
	private String name;
	
	public Double[][] getTwoDArray() {
		return TwoDArray;
	}

	public void setTwoDArray(Double[][] twoDArray) {
		if (twoDArray.length == TwoDArray.length && twoDArray[0].length == TwoDArray[0].length){
			for(int i=0; i<twoDArray.length;i++){
				for (int j=0; j<twoDArray[0].length;j++){
					TwoDArray[i][j] = twoDArray[i][j];
				}
			}
		}
		else
			System.out.println("ATTENTION: Dimension mismatch!!!");
	}

	public Double[][] getBackUPTwoDArray() {
		return BackUPTwoDArray;
	}

	public int getnRows() {
		return nRows;
	}

	public void setnRows(int nRows) {
		this.nRows = nRows;
	}

	public int getnCols() {
		return nCols;
	}

	public void setnCols(int nCols) {
		this.nCols = nCols;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * constructor
	 * @param nRows
	 * @param nCols
	 */
	public Matrixx(int nRows, int nCols) {
		super();
		if (nRows <=0 || nCols<=0)
			System.out.println("ATTENTION: Entered dimensions are not valid!!!");
		this.nRows = nRows;
		this.nCols = nCols;
		this.TwoDArray = new Double[nRows][nCols];
		this.BackUPTwoDArray = new Double[nRows][nCols];
	}
	
	/**
	 * constructor overload
	 * @param TwoDArray
	 */
	public Matrixx(Double[][]dArray){
		this.nRows = dArray.length;
		this.nCols = dArray[0].length;
		this.TwoDArray = dArray;
		this.BackUPTwoDArray = new Double[nRows][nCols];
	}
	
	public void print(){
		System.out.println("The followings are ORIGINAL elements of the matrix:"+ this.getName());
		for(int i=0; i<this.nRows;i++){
			for (int j=0; j<this.nCols;j++){
				System.out.println(this.getTwoDArray()[i][j]);
			}
		}
		System.out.print("Dimensions: "+ this.getnRows() + " by " + this.getnCols());
	}
	
	public void printBackup(){
		System.out.println("The followings are the (PROBABLY MANIPULATED) elements of the matrix:"+ this.getName());
		for(int i=0; i<this.nRows;i++){
			for (int j=0; j<this.nCols;j++){
				System.out.println(this.getBackUPTwoDArray()[i][j]);
			}
		}
		System.out.print("Dimensions: "+ this.getnRows() + " by " + this.getnCols());
	}

	/**
	 * duplicates the TwoDArray of Matrixx in BackUp2DArray
	 * NOTE: Do not forget to set the duplicate before doing filterings and etc.
	 */
	public void duplicate(){
		for (int i=0; i<this.nRows; i++){
			for (int j=0; j<this.nCols; j++){
				this.BackUPTwoDArray[i][j]=this.TwoDArray[i][j];
			}
		}
	}
	
	public double sumOfElements(){
		double sum=0;
		for (int i=0; i<this.nRows; i++)
			for (int j=0; j<this.nCols; j++){
				sum+= this.getTwoDArray()[i][j];
			}
		return sum;
	}
	
	public double AVGOfElements(){
		return this.sumOfElements()/(this.getnRows()*this.getnCols());
	}

	public static void main(String[] args) {
		
	}
}

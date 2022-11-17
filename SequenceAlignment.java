	
public class SequenceAlignment {

	private String x;
	private String y;
	
	
	private int subProb[][];
	
	//0, 1, 2 for diagonal, vertical, horizontal
	private int direction[][];
	
	private int delta;
	
	public SequenceAlignment(String x, String y) {
		this.x = x;
		this.y = y;
		
		subProb = new int[x.length() + 1][y.length() + 1];
		direction = new int[x.length() + 1][y.length() + 1];
		
	}

	public void computeAlignment(int k) {
		// TODO Auto-generated method stub
		delta = k;
		int m = this.x.length();
		int n = this.y.length();
		
		//fill in the intial values of the subproblem array
		for(int i = 1; i < subProb[0].length; i++) {
			subProb[0][i] = delta + subProb[0][i - 1];
		}
		for(int i = 1; i < subProb.length; i++) {
			subProb[i][0] = delta + subProb[i - 1][0];
		}
				
		//fill in the intial directions for the directional array
		for(int i = 1; i < direction[0].length; i++) {
			direction[0][i] = 2;
		}
		for(int i = 1; i < direction.length; i++) {
			direction[i][0] = 1;
		}
		
		
		//iterates through the 2d array to fill in the correct values for the dp subproblem
		for(int i = 1; i <= m; i++) {
			for(int j = 1; j <= n; j++) {
				
				char xLetter = x.charAt(i - 1);
				char yLetter = y.charAt(j - 1);

				//if the letters are a match
				if(xLetter == yLetter) {
					subProb[i][j] = OPT(i, j, 0);
				}
				//if the letters arent a match but are both vowels
				else if( isVowel(xLetter) && isVowel(yLetter) ) {
					subProb[i][j] = OPT(i, j, 1);
				}
				//if the letters arent a match but are both consonants
				else if( !(isVowel(xLetter)) && !(isVowel(yLetter))) {
					subProb[i][j] = OPT(i, j, 1);
				}
				//if the letters arent a match and arent matching consonants or vowels
				else {
					subProb[i][j] = OPT(i, j, 3);
				}
			}
		}
		
	}

	public String getAlignment() {
		
		int i = x.length();
		int j = y.length() ;
		
		//calls the 2 helper functions to return both the recursively built x and y strings from the directional array
		return printXAlignment(i, j) + " " + printYAlignment(i, j);

	}
	
	public String printXAlignment(int i, int j) {
		
		//recursively builds the x string 
		String printX = "";
		if(i == 0 || j == 0) {
			return printX;
		}
		
		//diagonal
		if(direction[i][j] == 0) {
			printX += printXAlignment(i - 1, j - 1);
			printX += x.charAt(i - 1);
			
		}
		//vertical
		else if(direction[i][j] == 1) {
			printX += printXAlignment(i - 1, j);
			printX += x.charAt(i - 1);
			
		}
		//horizontal
		else {
			printX += printXAlignment(i, j - 1);
			printX += "-";
			
		}
		return printX;
	}
	
	public String printYAlignment(int i, int j) {
		
		//recursively builds the y string 
		String printY = "";
		if(i == 0 || j == 0) {
			return printY;
		}
		
		//diagonal
		if(direction[i][j] == 0) {
			printY += printYAlignment(i - 1, j - 1);
			printY += y.charAt(j - 1);
			
		}
		//vertical
		else if(direction[i][j] == 1) {
			printY += printYAlignment(i - 1, j);
			printY += "-";
			
		}
		//horizontal
		else {
			printY += printYAlignment(i, j - 1);
			printY += y.charAt(j - 1);
			
		}
		return printY;
	}
	
	public int OPT(int i, int j, int violation) {
		
		//sets a, b, and c to to the diagonal, vertical, and horizontal position on the subProblem array and adds their penalty value
		int a = violation + subProb[i - 1][j - 1];
		int b = delta + subProb[i - 1][j];
		int c = delta + subProb[i][j - 1];

		//finds the minimum of them and sets the correct direction of the direction array
		int min = Math.min(a, Math.min(b, c));
		
		if(min == a) 
			direction[i][j] = 0;
		else if(min == b)
			direction[i][j] = 1;
		else
			direction[i][j] = 2;
		
		return min;
		
		
	}
	
	public boolean isVowel(char letter) {
		if(letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u') {
			return true;
		}
		return false;
	}
	
}

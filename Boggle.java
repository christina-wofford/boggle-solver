import java.util.*;
import java.io.*;

public class Boggle
{
	private static String gridSizeString;
	private static int gridSize;
	private static String[][] theGrid;
	private static boolean[][] visitCheck;
	private static BST dictionaryBST;
	private static BST wordsFound;
	//private static int wordCount;
	private static ArrayList wordsFoundAL;
	
	public static void main(String[] args) throws Exception
		{
			Scanner gridFill = new Scanner( new FileReader(args[0]) );
			Scanner dictionary = new Scanner (new FileReader("dictionary.txt") );
			
			gridSizeString = gridFill.next();
			gridSize = Integer.parseInt(gridSizeString);
			theGrid = new String[gridSize][gridSize];
			dictionaryBST = new BST();
			wordsFound = new BST();
			visitCheck = new boolean[gridSize][gridSize];
			wordsFoundAL = new ArrayList<String>();
			
			for(int j = 0; j < gridSize; j++)
				{
					for(int i = 0; i < gridSize; i++)
						{
							theGrid[j][i] = gridFill.next();
						}
				}
				
			while(dictionary.hasNext())
				dictionaryBST.insert(dictionary.next());
				
			//printGrid();			
			startingGeneration();
			Collections.sort(wordsFoundAL);
			for(Object temp: wordsFoundAL)
				System.out.println(temp);
			//wordsFound.inOrderPrint();
			//System.out.println("===========================");
			//System.out.println("produced "+wordCount+" unique words");
		}
		
	private static void printGrid()
		{
			for(int j = 0; j < gridSize; j++)
				{
					for(int i = 0; i < gridSize; i++)
						System.out.print(theGrid[j][i]+"  ");
					System.out.println("");
				}
			System.out.println("===========================");
		}
	 
	 public static void startingGeneration()
		{
			for (int i = 0; i < gridSize; i++)
				for (int j = 0; j < gridSize; j++)
					generateAll("", i, j);
		}
	 public static void generateAll(String word, int i, int j) 
		{ 
			
			if (i < 0 || j < 0 || i > gridSize-1 || j > gridSize-1 || visitCheck[i][j])
				return;
							
			if (dictionaryBST.containsStartingWith(word) == false) 
				return;


			visitCheck[i][j] = true;
				word+=theGrid[i][j];
				
				 if (dictionaryBST.contains(word) && word.length() > 2 && wordsFoundAL.contains(word) == false) 
					{
						wordsFoundAL.add(word);
						//wordCount++;
					}

			
				for (int y = -1; y <= 1; y++)
					for (int z = -1; z <= 1; z++)
						generateAll(word, i + y, j + z);

			visitCheck[i][j] = false;
		}
		
		
}
class BST
{
	private BSTNode root;
	
	//GIVEN
	public void insert( String data )
	{
		root = insertHelper( root, data );
	}
	
	//GIVEN
	private BSTNode insertHelper( BSTNode root, String data )
	{
		if (root == null)
			return new BSTNode(data,null,null);
		int comp = data.compareTo( root.getData() );
		if (comp < 0)
			root.setLeft( insertHelper(root.getLeft(), data ) );
		else
			root.setRight( insertHelper(root.getRight(), data ) );

		return root;
    }

	// GIVEN
	public void inOrderPrint()
	{
		inOrderPrintHelper( root );
		System.out.println();
	}
	// GIVEN
	public void inOrderPrintHelper( BSTNode root)
	{
		if (root==null) return;
		inOrderPrintHelper( root.getLeft() );
		System.out.println(root.getData());
		inOrderPrintHelper( root.getRight() );
	}
	public boolean containsStartingWith(String data)
		{
			return getStartingWith(data) != null;
		}
	
	public boolean contains(String data) 
		{
			return get(data) != null;
		}
 
    public String get(String data) 
		{
			return get(root, data);
		}	

    private String get(BSTNode x, String data)
		{
			if (x == null) 
				return null;
			if(data.compareTo(x.getData()) < 0) 
				return get(x.getLeft(), data);
			else if (data.compareTo(x.getData()) > 0)
				return get(x.getRight(), data);
			else
				return x.getData();
		}
	
	public String getStartingWith(String data)
		{
			return getStartingWith(root, data);
		}
		
	private String getStartingWith(BSTNode x, String data)
		{
			if (x == null)
				return null;
			if(x.getData().startsWith(data))
				return x.getData();
			else if(data.compareTo(x.getData()) < 0 && x.getLeft() != null)
				return getStartingWith(x.getLeft(),data);
			else if(data.compareTo(x.getData()) > 0 && x.getRight() != null)
				return getStartingWith(x.getRight(),data);
			else
				return null;
		}
	
}
 class BSTNode
{
	String data;
	BSTNode left,right;

	public BSTNode( String data, BSTNode left, BSTNode right )
	{
		this.data = data;
		this.left = left;
		this.right=right;
	}

	public BSTNode getLeft()
	{
		return left;
	}

	public BSTNode getRight()
	{
		return right;
	}

	public void setRight( BSTNode right)
	{
		this.right = right;
	}

	public void setLeft( BSTNode left)
	{
		this.left = left;
	}

	public String getData()
	{
		return data;
	}

	public void setData( String data)
	{
		this.data = data;
	}
}
/*
	Initial State:
	The board is empty no Queen on the board. We will put the Queen one by one and reach the goal state.
	
	States:	
	A state is any arrangement of 0 to 8 queens on the board. 
	We can even have a state where any arrangement of 0-8 queens is done with no queen under attack.

	Actions:
	Adding queen to any empty square or adding queen to the leftmost square that is empty such that no other queen attack her and this is the operator.

	Path cost:
	Path cost is 1 per move.

	Goal state:
	Putting the 8 queen on the board such that is not under attack by any other queen on the board.
	Breadth First Search is a Special Case of Uniform Cost Search where cost of each path is equal to 1.
	
	Heuristics function:
	number of queens attacking each other.
*/

import java.util.*;
import java.io.*;
import java.lang.*;

class comp implements Comparator<Board>
{
	public int compare(Board b1, Board b2)
	{
		if(b1.get_path() + b1.getheru() > b2.get_path() + b2.getheru())
			return 1;
		else if(b1.get_path() + b1.getheru() < b2.get_path() + b2.getheru())
			return -1;
		else return 0;
	}
}

class Board
{
	private String board;
	private int path_cost;
	private int heruistic_cost;
	
	Board(String board, int path, int heru)
	{
		this.board = board;
		this.path_cost = path;
		this.heruistic_cost = heru;
	}
	String getboard()
	{
		return this.board;
	}
	int get_path()
	{
		return this.path_cost;
	}
	int getheru()
	{
		return this.heruistic_cost;
	}
	void setpath(int path)
	{
		this.path_cost = path;
	}
	void setheru(int heru)
	{
		this.heruistic_cost = heru;
	}
	void setboard(String board)
	{
		this.board = board;
	}
}

public class Assignment2
{
	int size = 8;
	int optimal = 8;
	
	Board initialise()
	{
		String arr = "";
		for(int i = 0;i<size;i++)
		{
			for(int j=0;j<size;j++)
				arr += "0";
		}
		return new Board(arr,0,0);
	}
	
	int find(String arr)
	{
	    int i;
		for(i=0;i<size;i++)
		{
			int flag = 0;
			for(int j=0;j<size;j++)
			{
				if(arr.charAt(size*i+j) == '1')
				{
					flag = 1;
					break;
				}
			}
			if(flag == 0)
				return i;
		}
		return i;
	}
	
	void display(String arr)
	{
		for(int i = 0;i < size; i++)
		{
			for(int j = 0;j < size; j++)
			{
				if(arr.charAt(size*i+j) == '1')
					System.out.print("1 |");
				else System.out.print("_ |");
			}
		System.out.println();
		}
	}
	
	int get_heruistics(String arr)
	{
		int [][] array = new int[size][size];
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
				array[i][j] = (int) arr.charAt(size*i+j) - 48;
		}
		
		int count = 0;
		for(int i = 0; i < size; i++)
		{
			for(int j = 0;j < size; j++)
			{
				if(array[i][j] == 1)
				{
					int row = i+1;
					int column = j;
					while(row < size)//column checking
					{
						if(array[row][column] == 1)
						{
							array[row][column] = 0;
							count++;
						}
						row++;
					}
						
					for(row = i+1,column = j+1; row < size && column < size ; row++,column++)//right diagonal
					{
						if(array[row][column] == 1)
						{
							array[row][column] = 0;
							count++;
						}
					}
						
					for(row = i+1,column = j-1; row < size && column >= 0 ; row++,column--)//left diagonal
					{
						if(array[row][column] == 1)
						{
							array[row][column] = 0;
							count++;
						}
					}
					break;
				}
			}
		}
		return count;
	}
	
	public static void main(String [] args)
	{
		Assignment2 queen = new Assignment2();
		Board bmain = queen.initialise();
		
		PriorityQueue<Board> queue = new PriorityQueue<Board>(100,new comp());
		queue.add(bmain);
		
		int count = 1;
		
		while(!(queue.isEmpty()))
		{
			//System.out.println("hey1");
			Board b = queue.poll();
			String ans = b.getboard();
			//System.out.println("hey2");
			//System.out.println(b.get_path() + b.getheru());
			if(b.get_path() + b.getheru() > queen.optimal)
				continue;
			//System.out.println("hey3");
			int i = queen.find(ans);
			//System.out.println("hey4");
			if(i == queen.size)
			{
				System.out.println("\n*****" + count + "*****");
				queen.display(ans);
				//System.out.println("hey5");
				count++;
			}
			else
			{
				//System.out.println("hey6");
				for(int j = 0; j < queen.size; j++)
				{
					ans = ans.substring(0,queen.size*i+j) + "1" + ans.substring(queen.size*i+j+1);
					queue.offer(new Board(ans, b.get_path() + 1, queen.get_heruistics(ans)));
					//System.out.println("hey7");`
					ans = b.getboard();
				}
			}
		}
	}
}

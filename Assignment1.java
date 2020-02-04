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

Submitted by:- Namit Goel
Roll no.:- Assignment1
*/
import java.util.*;
public class Assignment1
{
	int queen = 8;
	String initialise(String arr)
	{
		for(int i=0;i<queen;i++)
			for(int j=0;j<queen;j++)
				arr+="0";
		return arr;
	}
	int find(String arr)
	{
	    int i;
		for(i=0;i<queen;i++)
		{
			int flag = 0;
			for(int j=0;j<queen;j++)
			{
				if(arr.charAt(queen*i+j) == '1')
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
	boolean isValid(String arr)
	{
		for(int i=0;i<queen;i++)
		{
			int sum_row = 0;
			int sum_col = 0;
			for(int j=0;j<queen;j++)
			{
				sum_row += (int)arr.charAt(queen*i+j)-48;
				sum_col += (int)arr.charAt(queen*j+i)-48;
			}
			if(sum_row > 1 || sum_col > 1)
				return false;
		}
		for(int i=0;i<queen;i++)
		{
			int sum_left = 0;
			int sum_right = 0;
			for(int j=0;j+i<queen;j++)
			{
				sum_left += (int)arr.charAt(queen*j+j+i)-48;
				sum_right += (int)arr.charAt(queen*(j+i)+j)-48;
			}
			if(sum_left > 1 || sum_right > 1)
				return false;
		}
		for(int i=0;i<2*queen-1;i++)
		{
			int sum_left = 0;
			int sum_right = 0;
			if(i<queen)
			{
			   for(int j=0;i-j>=0;j++)
			   {
				   sum_left += (int)arr.charAt(queen*j+i-j)-48;
			   }
			}
			else
			{
			   for(int j=i-queen+1;j<queen;j++)
			   {
			       sum_right += (int)arr.charAt(queen*j+i-j)-48;
			   }
			}
			if(sum_left > 1 || sum_right > 1)
				return false;
		}
		return true;
	}
	
	void display(String arr)
	{
		for(int i=0;i<queen;i++)
		{
			for(int j=0;j<queen;j++)
			{
				if(arr.charAt(queen*i+j)=='1')System.out.print("Q ");
				else System.out.print("_ ");
			}
		System.out.println();
		}
	}
	
	public static void main(String args[])
	{
		Assignment1 eight = new Assignment1();
		String arr = "";
		arr = eight.initialise(arr);
		LinkedList<String> bfs = new LinkedList<String>();
		bfs.add(arr);
		int countsol = 1;
		while(!(bfs.isEmpty()))
		{
			String result = bfs.poll();
			if(!(eight.isValid(result)))
				continue;
			int i = eight.find(result);
			if(i==eight.queen)
			{
				System.out.println("----arrution "+countsol+"----");
				eight.display(result);
				System.out.println("-------------------\n");
				countsol++;
			}
			else
			{
				for(int j=0;j<eight.queen;j++)
				{
					result = result.substring(0, eight.queen*i+j)+"1"+result.substring(eight.queen*i+j+1);
					bfs.offer(result);
					result = result.substring(0, eight.queen*i+j)+"0"+result.substring(eight.queen*i+j+1);
				}
			}
			
		}
	}
}
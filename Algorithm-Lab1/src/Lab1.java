import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;


public class Lab1 {

	/* This class determines a series of steps which can be followed to move
	 * input among three stacks "InputTrack", "SideTrack" and "ExitTrack" so
	 * that at the end all the numbers from the InputTrack are in sorted order in ExitTrack.
	 *   
	 * @param inputTrack Stack of input values to sort
	 * @return Stack of values from input in sorted order
	 * 
	 * author: Bhilhanan Jeyaram
	 */
	public static Stack<Integer> trainSort(Stack<Integer> inputTrack)
	{
		/*Un-comment this block for calculating time 
		* 
		StopWatch stopWatch=new StopWatch();
		stopWatch.start();
		*/
		
		Stack<Integer> sideTrack = new Stack<Integer>();
		Stack<Integer> exitTrack = new Stack<Integer>();
		ArrayList<Integer> tempArray=new ArrayList<Integer>();
		Integer middleman=0,currVal=0,input=0,side=0;
		int i=0,flag=0,currentTrack=0;
		boolean canChangePeekTrack=false;
		
		
		if(inputTrack==null)
			return exitTrack;
		
		try{
			inputTrack.peek();		//Would throw exception if inputTrack is empty
		}
		catch(EmptyStackException e){
			return exitTrack;
		}
		
		//Pop values from inputTrack to a temporary ArrayList
		ArrayList<Integer> sortedArray=new ArrayList<Integer>();
		
		try{
			while((middleman=inputTrack.pop())!=null){
				tempArray.add(middleman);
				
				//Inserting the element at the right position in another ArrayList "sortedArray"
				findPosition(sortedArray, middleman);	
			}
		}
		catch(EmptyStackException e){
			//System.out.println(e);
		}
		
		
		
		//Push the elements back into inputTrack
		for(int k=tempArray.size()-1;k>=0;k--){
			inputTrack.push(tempArray.get(k));
		}
		
		
		/////////////////////////////////////////////////////////////////
		
		flag=0;
		canChangePeekTrack=false;
		currentTrack=0;		//0-inputTrack, 1-sideTrack
		
			try{
				inputTrack.peek();				
			}catch(EmptyStackException e){
				input=null;
			}
			try{
				side=sideTrack.peek();				
			}catch(EmptyStackException e){
				side=null;
			}
		
			while(!(input==null && side==null)){
				if(canChangePeekTrack==true){
					if(flag==0){
						try{
							currVal=inputTrack.peek();
						}catch(EmptyStackException e){
							flag=0;
						}
						currentTrack=0;
						canChangePeekTrack=false;
					}
					else{
						try{
							currVal=sideTrack.peek();
						}catch(EmptyStackException e){
							flag=0;
						}
						currentTrack=1;
						canChangePeekTrack=false;
					}
				}
				
				if(currentTrack==0){
					currVal=inputTrack.pop();
					if(currVal==sortedArray.get(i)){
						exitTrack.push(currVal);
						i++;
						canChangePeekTrack=true;
						System.out.println("Move "+currVal+" from input to exit");
					}
					else{
						sideTrack.push(currVal);
						System.out.println("Move "+currVal+" from input to siding");
						if(currVal==sortedArray.get(i+1))		//Keeps track of the next element that needs to be pushed in exitTrack
							flag=1;
					}
				}
				else{
					currVal=sideTrack.pop();
					if(currVal==sortedArray.get(i)){
						exitTrack.push(currVal);
						i++;
						canChangePeekTrack=true;
						System.out.println("Move "+currVal+" from siding to exit");
					}
					else{
						inputTrack.push(currVal);
						System.out.println("Move "+currVal+" from siding to input");
						if(currVal==sortedArray.get(i+1))		//Keeps track of the next element that needs to be pushed in exitTrack
							flag=0;
					}
				}
				
				try{
					inputTrack.peek();	
					input=0;
				}catch(EmptyStackException e){
					input=null;
					flag=1;
					currentTrack=1;
				}
				
				try{
					side=sideTrack.peek();
					side=0;
				}catch(EmptyStackException e){
					side=null;
					flag=0;
					currentTrack=0;
				}

			}//end: while loop
			
		
		
		/* Un-comment this block for calculating time
		 * 
		stopWatch.stop();
		System.out.println("Elapsed Time = "+stopWatch.getElapsedTime());
		*/
		
		return exitTrack;
		
		
	}//end: trainSort()
	
	/*
	 * findPosition uses binary search on an arrayList to find the right position
	 * in a sorted list and adds the numnber
	 * 
	 * @param arrList: ArrayList in which numbers are in sorted order
	 * @param num: Integer which is to be inserted in arrList
	 * 
	 */
	public static void findPosition(ArrayList<Integer> arrList,Integer num){
		int left=0;
		int right=arrList.size()-1;
		
		int mid=(left+right)/2;
		if(right<0){		//If arrList is empty
			arrList.add(num);
			return;
		}
		
		while(left<=right){
			mid=(left+right)/2;
			if(arrList.get(mid)<num){
				left=mid+1;
			}
			else if(arrList.get(mid)>=num){
				right=mid-1;
			}
			
		}
		arrList.add(right+1,num);
	}
}// end: findPosition()

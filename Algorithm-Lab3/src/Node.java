


public class Node {
	
	String str;
	int inDegree=0,outDegree=0;
	boolean isTarget=false;
	boolean needsTarget=false;
	boolean visited=false;
	boolean completed=false;
	boolean popped=false;
	Pointer p=null;
	public void incInDegree(){
		inDegree++;
	}
	
	public void setString(String str){
		this.str=str;
	}
	
	public String getString(){
		return str;
	}
	
	public int getInDegree(){
		return inDegree;
	}
	
	public void setIsTarget() throws ParseException{
		if(isTarget){
			throw new ParseException();
		}
		isTarget=true;
	}
	
	public void setNeedsTarget(){
		needsTarget=true;
	}
	
	public boolean isTarget(){
		return isTarget;
	}
	
	public boolean needsTarget(){
		return needsTarget;
	}
	
	public void setPointer(Pointer p){
		this.p=p;
	}
	
	public Pointer getPointer(){
		return p;
	}
	public boolean isVisited(){
		return visited;
	}

}

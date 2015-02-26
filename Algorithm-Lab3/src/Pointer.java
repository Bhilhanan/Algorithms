
public class Pointer {
	Node n=null;
	Pointer in=null;
	int i=0,j=0;
	public Pointer(Node n){
		this.n=n;
		n.incInDegree();
	}
	public void setInPointer(Pointer p){
		in=p;
	}
	public Node getNode(){
		return n;
	}
	
}

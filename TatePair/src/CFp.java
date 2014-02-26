import java.math.BigInteger;


public class CFp {
	private BigInteger p;
	private BigInteger r; 
	private BigInteger i;
	
	public CFp(BigInteger r,BigInteger i,BigInteger p) {
		this.p = p;
		this.r = r.mod(p);
		this.i = i.mod(p);
	}
	public BigInteger getR() {
		return r;
	}
	public BigInteger getI() {
		return i;
	}
	public BigInteger getP(){
		return p;
	}
	   
	public CFp add(CFp Q) {
		return new CFp(this.getR().add(Q.getR()).mod(p),
				              this.getI().add(Q.getI()).mod(p),p);
	}
	   
	public CFp add(BigInteger Q) {
		CFp P = new CFp(Q,BigInteger.ZERO,p);
		return this.add(P);
	}
	   
	public CFp subtract(CFp Q) {
		return new CFp(this.getR().subtract(Q.getR()).mod(p),
		              this.getI().subtract(Q.getI()).mod(p),p);
	}
	   
	public CFp subtract(BigInteger Q) {
		CFp P = new CFp(Q,BigInteger.ZERO,p);
		return this.subtract(P);
	}
	   
	public CFp multiply (CFp Q) {
		BigInteger I = this.getR().multiply(Q.getI())
				           .add(this.getI().multiply(Q.getR())).mod(p);
		BigInteger R = this.getR().multiply(Q.getR())
		                   .subtract(this.getI().multiply(Q.getI())).mod(p);
		return new CFp(R,I,p);
	}
	   
    public CFp multiply (BigInteger Q) {
		CFp P = new CFp(Q,BigInteger.ZERO,p);
		return multiply(P);
	}
	
    public CFp div(CFp Q){
    	CFp Q1 = new CFp(Q.getR(), Q.getI().negate(), Q.getP());
    	Q1 = this.multiply(Q1);
    	BigInteger a = Q.getR().multiply(Q.getR())
    			       .add(Q.getI().multiply(Q.getI()));
    	a = a.modInverse(Q.p);
    	return Q1.multiply(a);
    }
    
    public CFp div(BigInteger Q){
    	CFp P = new CFp(Q,BigInteger.ZERO,p);
    	return this.div(P);
    }
    
	public CFp pow(BigInteger n) {
		String bin = n.toString(2);
		CFp T = this;
		for(int i = 1;i <= bin.length()-1;i ++) {
		   	T = T.multiply(T);
		   	if(bin.charAt(i)=='1'){
		   		T = this.multiply(T);
		   	}
		 }
		 return T;
	} 
	   
	public boolean equals(CFp Q){
		if(this.p.equals(Q.p)&&this.i.equals(Q.i)&&this.r.equals(Q.r)){
			return true;
		}
		else{
		   	return false;
		}
	}
	
	public String toString(){
		
		return new String("[") + r.toString() 
				+ new String(" , ") + i.toString() 
				+ new String(" , ") + p.toString() 
				+ new String("]"); 
	}
}

import java.math.BigInteger;
import java.security.spec.ECPoint;


public class PairParam {
	private BigInteger p ;
	private BigInteger a ;
	private BigInteger b ;
	private BigInteger n ;
	private ECPoint  g  ;
	
	public PairParam(BigInteger p,BigInteger a, BigInteger b, BigInteger n, ECPoint g) {
		this.p = p;
		this.a = a;
		this.b = b;
		this.n = n;
		this.g = g;
	}
		
	public  BigInteger getP(){
		return p;
	}
	
	public BigInteger getA(){
		return a;	
	}
	
	public BigInteger getB(){
		return b;
	}
	
	public BigInteger getOrder(){
		return n;
	}
	
	public ECPoint getGenerator(){
		return g;
	}
}

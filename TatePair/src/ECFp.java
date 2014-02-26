import java.math.BigInteger;
import java.security.spec.ECPoint;


public class ECFp {
	
	private BigInteger p ;
	private BigInteger a ;
	private BigInteger b ;
	private BigInteger n ;
	private ECPoint  g  ;
	private PairParam param;
	public static int  K = 256;
	
	public ECFp(PairParam param){
		this.param = param;
		this.p = param.getP();
		this.a = param.getA();
		this.b = param.getB();
		this.n = param.getOrder();
		this.g = param.getGenerator();
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
	
	public PairParam getPairParam(){
		return param;
	}
	private BigInteger getYByX(BigInteger X){
		BigInteger right;
		//x^3 + a.x + b mod p
		right =  X.modPow(BigIntegerExtend.decimal[3],p)
				   .add(a.multiply(X)).add(b).mod(p);
		return  BigIntegerExtend.sqrtMode(right,p);
    }
 
	public BigInteger getSlope(ECPoint P1,ECPoint P2) {
	 	BigInteger U,D;
	 	if(!contain(P1)||!contain(P2)){
	 		return null;
	 	}
	 	if(!P1.getAffineX().equals(P2.getAffineX())){
	 		U=P2.getAffineY().subtract(P1.getAffineY());
	 		D=P2.getAffineX().subtract(P1.getAffineX());
	 		return U.multiply(D.modInverse(p)).mod(p);
	 	}
	 	else if (P1.getAffineY().equals(P2.getAffineY())) {
	 		U=BigIntegerExtend.decimal[3].multiply(P1.getAffineX()
				  .modPow(BigIntegerExtend.decimal[2],p)).add(a).mod(p);
	 		D=BigIntegerExtend.decimal[2]
				  .multiply(P1.getAffineY()).modInverse(p);
	 		return D.multiply(U).mod(p);
	 	}
	 	else {
	 		return BigIntegerExtend.BIGINTEGER_INFINITY;
	 	}
 	}
 
	public ECPoint add(ECPoint P1,ECPoint P2){
		if(!contain(P1)||!contain(P2))
			return null;
		BigInteger R;
		BigInteger X,Y;
		if(P1.equals(ECPoint.POINT_INFINITY)) {
			return P2;
		}
		else if(!P1.getAffineX().equals(P2.getAffineX())){
			R = getSlope(P1,P2);
		}
		else if(P1.getAffineX().equals(P2.getAffineX())&&
			  P1.getAffineY().equals(P2.getAffineY())) {
		  
			if(P1.getAffineY().equals(BigInteger.ZERO)){
			   return ECPoint.POINT_INFINITY;
			}
			R = getSlope(P1,P2);
		}
		else {
			return ECPoint.POINT_INFINITY;
		}
		X=R.modPow(BigIntegerExtend.decimal[2],p)
				.subtract(P2.getAffineX()).subtract(P1.getAffineX()).mod(p);
		Y=R.multiply(P1.getAffineX().subtract(X))
				.subtract(P1.getAffineY()).mod(p);
		return new ECPoint(X,Y);
	}
 
	public ECPoint subtract(ECPoint P1,ECPoint P2){
		if(!contain(P1)||!contain(P2))
			return null;
		ECPoint P=new ECPoint(P2.getAffineX(),P2.getAffineY().negate().mod(p));
		return add(P1,P);
	}

	public ECPoint multiply(ECPoint P,BigInteger k){
		if( ! contain(P))
			return null;
		String bString = k.toString(2);
		ECPoint f = P;
		for(int i = 1;i < bString.length();i ++){
			f = add(f,f);
			if(bString.charAt(i) == '1'){
				f = add(f,P);
			}
		}
		
		return f;
	}

	public ECPoint inverse(ECPoint P) {
		return this.subtract(ECPoint.POINT_INFINITY,P);
	}

	public boolean contain(ECPoint P){
		if(P == null) {
			return false;
		}
	 
		if(P.equals(ECPoint.POINT_INFINITY)){
			return true;
		}
	 
		BigInteger    X = P.getAffineX();
		BigInteger    Y = P.getAffineY();
		BigInteger left = Y.multiply(Y).mod(p);
		BigInteger right= X.modPow(BigIntegerExtend.decimal[3],p)
			            .add(a.multiply(X)).add(b).mod(p);
		return left.equals(right);
	}


	public ECPoint byte2Point(byte[] m){
		BigInteger certainty,X = null,Y = null;
		certainty = new BigInteger(String.valueOf(K));
	    X = new BigInteger(BigIntegerExtend.bytes2HexString(m),16).multiply(certainty);
	    if(X.compareTo(p) >= 0){
	    	return null;
	    }
	    for(int i = 0; i < K; i++){
	    	Y= getYByX(X);
	    	if(Y != null)
	    		break;
	    	if(i == (K - 1))
	    		return null;
	    	X = X.add(BigIntegerExtend.decimal[1]);
	    }
	    if(Y.compareTo(p.subtract(Y)) < 0){
	    	Y = p.subtract(Y);
	    }
	    return new ECPoint(X,Y);
	}

	public byte[] point2Byte(ECPoint q){
		BigInteger certainty = new BigInteger(String.valueOf(K));
		BigInteger         X = q.getAffineX().divide(certainty);
		String     hexString = X.toString(16);
		return BigIntegerExtend.hexString2Bytes(hexString);
	}

}

import java.math.BigInteger;
import java.security.spec.ECPoint;


public class TatePair {
	private ECFp e;
	
	public TatePair(ECFp e){
		this.e = e;
	}
	
	public ECFp getECFp(){
		return e;
	}
	public   ECCFpPoint Frobenius(ECPoint P) {
		CFp X,Y;
		X = new CFp(BigInteger.ZERO.subtract(P.getAffineX()),BigInteger.ZERO,e.getP());
		Y = new CFp(BigInteger.ZERO,P.getAffineY(),e.getP());
		return new  ECCFpPoint(X,Y);
	}

	private CFp gTP(ECPoint T,ECPoint P, ECCFpPoint Q) {
		CFp U;
		BigInteger s = e.getSlope(T,P);
		if(s == null ) {
			return null;
		}
		else if(!s.equals(BigIntegerExtend.BIGINTEGER_INFINITY)) {
			U = Q.getAffineY().subtract(T.getAffineY())
				 .subtract(Q.getAffineX().subtract(T.getAffineX()).multiply(s));
			return U;
	
		}
		else {
			return Q.getAffineX().subtract(T.getAffineX());
		}
	}

	private CFp fpQ(ECPoint P, ECCFpPoint Q){
		ECPoint T = P;
		CFp f =new CFp(BigInteger.ONE,BigInteger.ZERO,e.getP());
		String  b= e.getOrder().toString(2);
		int n = b.length();
		for (int i = 1 ; i<=n-1; i ++) {
				f = f.multiply(f).multiply(gTP(T,T,Q));
				T = e.add(T,T);
				if(b.charAt(i) == '1') {
						f = f.multiply(gTP(T,P,Q));
						T = e.add(T,P);
				}
		}
		return f;
	}

	public CFp getTate(ECPoint P,ECPoint Q) {
		if(P.equals(ECPoint.POINT_INFINITY)||Q.equals(ECPoint.POINT_INFINITY)){
			return new CFp(BigInteger.ONE,BigInteger.ZERO,e.getP());
		}
		ECCFpPoint mapQ = Frobenius(Q);
		return fpQ(P,mapQ).pow(e.getP().multiply(e.getP()).subtract(BigInteger.ONE).divide(e.getOrder()));
	}
	
}

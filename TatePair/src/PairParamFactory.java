import java.math.BigInteger;
import java.security.spec.ECPoint;

public class PairParamFactory {
	public static  PairParam getPairParam(){
		 
		 BigInteger p = new BigInteger("100000000000000000000000000000000000000070000000000000000000000000000000000000000000006240000000000000000000000000000000000002afb", 16);
		 BigInteger a = BigInteger.ONE;
		 BigInteger b = BigInteger.ZERO;
		 
		 BigInteger n = new BigInteger("10000000000000000000000000000000000000007", 16);
		 BigInteger x = new BigInteger("71499d18ca692cc4c4cc882d773ce746d95c7ffcc15e1672211421662dc5f96998422afc92e6a3ed54c4b54626093056ae910f01a96e30149422b1fe4d7822e1", 16);
		 BigInteger y = new BigInteger("6f47d5c35b538d18baf7f00bd44287b9a91a28e82775a36d020aa279a8980355e4cb0a25eac9f5495d39a7bbfbda230e4ea805f69f7266cafa162f001acc2f8b", 16);
		 ECPoint  g = new ECPoint(x, y);
		 
		 return new PairParam(p, a, b, n, g);
	}

}

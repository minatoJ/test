import java.math.BigInteger;
import java.util.Random;
public class BigIntegerExtend {
		public static final BigInteger[] decimal={
					BigInteger.ZERO    ,BigInteger.ONE     ,new BigInteger("2"),
					new BigInteger("3"),new BigInteger("4"),new BigInteger("5"),
					new BigInteger("6"),new BigInteger("7"),new BigInteger("8"),
					new BigInteger("9"),BigInteger.TEN };
		public static final BigInteger BIGINTEGER_INFINITY =new BigInteger("-1");
		
		public static String bytes2HexString(byte[] b){
			  String byteHexString;
			  StringBuffer stringBuffer = new StringBuffer();
			  for(int i = 0; i < b.length; i++){
				  int val=b[i];
				  byteHexString=Integer.toHexString(val<0?val+256:val);
				  if(byteHexString.length() < 2){
					 stringBuffer.append(0);
				  }
				  stringBuffer.append(byteHexString);
			  }
			  return stringBuffer.toString();
		}
		
		public static byte[] hexString2Bytes(String hex){
			if(hex.length()%2 != 0)
				hex = "0"+hex;
			int byteSize = hex.length()/2;
			byte[] bytes = new byte[byteSize];
			for(int i = 0;i <hex.length();i+=2){
				bytes[i/2] = (byte)Integer.parseInt(hex.substring(i,i+2), 16);
			}
			return bytes;
		}
		
		public static BigInteger getBigIntegerByBit(int bit){
				StringBuffer stringBuffer = new StringBuffer();
				stringBuffer.append(1);
				for(int i = 0; i < bit; i ++){
						stringBuffer.append(0);
				}
				return new BigInteger(stringBuffer.toString(),2);
		}
	
		public static BigInteger getRandom(BigInteger p) {
				BigInteger random;
				do{
						random = new BigInteger(p.toString(2).length(),new Random());
				}while(random.compareTo(p) >0  || random.compareTo(BigInteger.ONE) <= 0);
				return random;
		}
	
		public static BigInteger bytes2HexBigInteger(byte[] b){
				String byteHexString;
				StringBuffer stringBuffer = new StringBuffer();
				for(int i = 0; i < b.length; i++){
						int val=b[i];
						byteHexString=Integer.toHexString(val<0?val+256:val);
						if(byteHexString.length() < 2){
								stringBuffer.append(0);
						}
						stringBuffer.append(byteHexString);
				}
				return new BigInteger(stringBuffer.toString(),16);
		}
		
		public static boolean  isSqrtMode(BigInteger a,BigInteger p){
				BigInteger m = a.modPow(p.subtract(decimal[1]).divide(decimal[2]),p);
				m.mod(p);
				return m.equals(decimal[1]); 
		}
		
		public static BigInteger sqrtMode(BigInteger a,BigInteger p){
				if(! isSqrtMode(a,p)){
					return null;
				}
				else if(p.mod(decimal[4]).equals(decimal[3])){
							return a.modPow(p.add(decimal[1]).divide(decimal[4]),p).mod(p);
				}
				else if(p.mod(decimal[8]).equals(decimal[5])){
							BigInteger X,Y;
							X=a.modPow(p.add(decimal[3]).divide(decimal[8]),p).mod(p);
							if(a.modPow(p.subtract(decimal[1]).divide(decimal[4]),p)
					                        .mod(p).equals(decimal[1])){
										return X;
							}
							else{
										Y=decimal[2].modPow(p.subtract(decimal[1]).divide(decimal[4]),p);
										return X.multiply(Y).mod(p);
							}
				}
				else{
							int t = p.subtract(decimal[1]).getLowestSetBit();
							BigInteger s = p.subtract(decimal[1]).divide(decimal[2].pow(t));
							BigInteger X = a.modPow(s.add(decimal[1]).divide(decimal[2]), p);
							BigInteger n = new BigInteger(160,new Random()).mod(p);
							BigInteger A = a.modInverse(p);
							BigInteger b;
							while(isSqrtMode(n,p)) {
									n = n.add(decimal[1]);
							}
							n = n.mod(p);
							b = n.modPow(s,p);
							for(int i = t-2; i >= 0; i--){
									BigInteger exp=getBigIntegerByBit(i);
									BigInteger bFlag = A.multiply(X.pow(2)).mod(p).modPow(exp,p);
									bFlag.mod(p);
									if(!bFlag.equals(decimal[1])){
											exp = getBigIntegerByBit(t-2-i);
											X = b.modPow(exp,p).multiply(X).mod(p);
									}		
							}
							return X;
				}
		}
}

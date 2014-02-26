
public class ECCFpPoint {
	private CFp X;
	private CFp Y;
	
	public ECCFpPoint(CFp X,CFp Y) {
			this.X = X;
			this.Y = Y;
	}
	
	public CFp getAffineX() {
			return X;
	}
	
	public CFp getAffineY() {
			return Y;
	}
}

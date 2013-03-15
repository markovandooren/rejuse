package be.kuleuven.cs.distrinet.rejuse.logic.ternary;

public enum Ternary {
  TRUE() {
  	public Ternary and(Ternary other) {
  		return other;
  	}
  	
  	public Ternary or(Ternary other) {
  		return TRUE;
  	}  	

  	public Ternary not() {
  		return FALSE;
  	}
  	
  },
  FALSE() {
  	public Ternary and(Ternary other) {
  		return FALSE;
  	}
  	
  	public Ternary or(Ternary other) {
  		return other;
  	}
  	
  	public Ternary not() {
  		return TRUE;
  	}
  },
  UNKNOWN() {
  	public Ternary and(Ternary other) {
  		Ternary result;
  		if(other == FALSE) {
  			result = FALSE;
  		} else {
  			result = UNKNOWN;
  		}
  		return result;
  	}
  	public Ternary or(Ternary other) {
  		Ternary result;
  		if(other == TRUE) {
  			result = TRUE;
  		} else {
  			result = UNKNOWN;
  		}
  		return result;
  	}

  	public Ternary not() {
  		return UNKNOWN;
  	}
  	
  };
  
  public abstract Ternary and(Ternary other);
  
  public abstract Ternary or(Ternary other);
  
  public abstract Ternary not();
  
  /**
   * p XOR q = (p OR q) AND NOT (p AND q) 
   * @param other
   * @return
   */
  public Ternary xor(Ternary other) {
  	return this.or(other).and(this.and(other).not());
  }
  
  /**
   * p NAND q = NOT (p AND q) 
   * @param other
   * @return
   */
  public Ternary nand(Ternary other) {
  	return this.and(other).not();
  }
}

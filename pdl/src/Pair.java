
public class Pair<X, Y>
/*    */   extends Object
/*    */ {
/*    */   private X left;
/*    */   private Y right;
/*    */   
/*    */   public Pair(X left, Y right) {
/* 16 */     this.left = left;
/* 17 */     this.right = right;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Pair(Pair<X, Y> pair) {
/* 24 */     this.left = pair.getLeft();
/* 25 */     this.right = pair.getRight();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 33 */   public X getLeft() { return (X)this.left; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 41 */   public Y getRight() { return (Y)this.right; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   public void setLeft(X left) { this.left = left; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public void setRight(Y right) { this.right = right; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
///*    */   public boolean equals(Object obj) {
///* 62 */     if (obj instanceof Pair) {
///* 63 */       Pair<?, ?> p = (Pair)obj;
///* 64 */       boolean ok_sofar = true;
///*    */       
///* 66 */       if (this.left == null) { ok_sofar = (p.getLeft() == null); }
///* 67 */       else { ok_sofar = this.left.equals(p.getLeft()); }
///*    */       
///* 69 */       if (ok_sofar) {
///* 70 */         if (this.right == null) { ok_sofar = (p.getRight() == null); }
///* 71 */         else { ok_sofar = this.right.equals(p.getRight()); }
///*    */       
///*    */       }
///* 74 */       return ok_sofar;
///* 75 */     }  return false;
///*    */   }
/*    */   
/*    */   public int hashCode() {
/* 79 */     int leftHashCode = (this.left == null) ? 0 : this.left.hashCode();
/* 80 */     int rightHashCode = (this.right == null) ? 0 : this.right.hashCode();
/* 81 */     return leftHashCode + 37 * rightHashCode;
/*    */   }
/*    */ 
/*    */   
/* 85 */   public String toString() { return "Pair(" + this.left + "," + this.right + ")"; }
/*    */ }
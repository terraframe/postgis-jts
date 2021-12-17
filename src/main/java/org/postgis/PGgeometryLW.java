/*    */ package org.postgis;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import org.postgis.binary.BinaryWriter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PGgeometryLW
/*    */   extends PGgeometry
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/* 41 */   BinaryWriter bw = new BinaryWriter();
/*    */ 
/*    */   
/*    */   public PGgeometryLW() {}
/*    */ 
/*    */   
/*    */   public PGgeometryLW(Geometry paramGeometry) {
/* 48 */     super(paramGeometry);
/*    */   }
/*    */   
/*    */   public PGgeometryLW(String paramString) throws SQLException {
/* 52 */     super(paramString);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 56 */     return this.geom.toString();
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 60 */     return this.bw.writeHexed(this.geom);
/*    */   }
/*    */   
/*    */   public Object clone() {
/* 64 */     return new PGgeometryLW(this.geom);
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/PGgeometryLW.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
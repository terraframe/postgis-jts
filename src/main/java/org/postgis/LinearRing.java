/*    */ package org.postgis;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ import org.postgresql.util.PGtokenizer;
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
/*    */ public class LinearRing
/*    */   extends PointComposedGeom
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/*    */   
/*    */   public LinearRing(Point[] paramArrayOfPoint) {
/* 42 */     super(0, paramArrayOfPoint);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LinearRing(String paramString) throws SQLException {
/* 52 */     this(paramString, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected LinearRing(String paramString, boolean paramBoolean) throws SQLException {
/* 62 */     super(0);
/* 63 */     PGtokenizer pGtokenizer = new PGtokenizer(PGtokenizer.removePara(paramString.trim()), ',');
/* 64 */     int i = pGtokenizer.getSize();
/* 65 */     Point[] arrayOfPoint = new Point[i];
/* 66 */     for (byte b = 0; b < i; b++) {
/* 67 */       arrayOfPoint[b] = new Point(pGtokenizer.getToken(b), paramBoolean);
/*    */     }
/* 69 */     this.dimension = (arrayOfPoint[0]).dimension;
/*    */ 
/*    */     
/* 72 */     this.haveMeasure = (arrayOfPoint[0]).haveMeasure;
/* 73 */     this.subgeoms = (Geometry[])arrayOfPoint;
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/LinearRing.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
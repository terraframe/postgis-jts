/*    */ package org.postgis;
/*    */ 
/*    */ import java.sql.SQLException;
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
/*    */ public class MultiPoint
/*    */   extends PointComposedGeom
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/*    */   
/*    */   public MultiPoint() {
/* 36 */     super(4);
/*    */   }
/*    */   
/*    */   public MultiPoint(Point[] paramArrayOfPoint) {
/* 40 */     super(4, paramArrayOfPoint);
/*    */   }
/*    */   
/*    */   public MultiPoint(String paramString) throws SQLException {
/* 44 */     this(paramString, false);
/*    */   }
/*    */   
/*    */   protected MultiPoint(String paramString, boolean paramBoolean) throws SQLException {
/* 48 */     super(4, paramString, paramBoolean);
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/MultiPoint.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
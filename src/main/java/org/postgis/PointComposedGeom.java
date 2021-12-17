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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PointComposedGeom
/*    */   extends ComposedGeom
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/*    */   
/*    */   protected PointComposedGeom(int paramInt) {
/* 43 */     super(paramInt);
/*    */   }
/*    */   
/*    */   protected PointComposedGeom(int paramInt, Point[] paramArrayOfPoint) {
/* 47 */     super(paramInt, (Geometry[])paramArrayOfPoint);
/*    */   }
/*    */   
/*    */   public PointComposedGeom(int paramInt, String paramString) throws SQLException {
/* 51 */     this(paramInt, paramString, false);
/*    */   }
/*    */   
/*    */   public PointComposedGeom(int paramInt, String paramString, boolean paramBoolean) throws SQLException {
/* 55 */     super(paramInt, paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected Geometry createSubGeomInstance(String paramString, boolean paramBoolean) throws SQLException {
/* 59 */     return new Point(paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected Geometry[] createSubGeomArray(int paramInt) {
/* 63 */     return (Geometry[])new Point[paramInt];
/*    */   }
/*    */   
/*    */   protected void innerWKT(StringBuffer paramStringBuffer) {
/* 67 */     this.subgeoms[0].innerWKT(paramStringBuffer);
/* 68 */     for (byte b = 1; b < this.subgeoms.length; b++) {
/* 69 */       paramStringBuffer.append(',');
/* 70 */       this.subgeoms[b].innerWKT(paramStringBuffer);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int numPoints() {
/* 78 */     return this.subgeoms.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Point getPoint(int paramInt) {
/* 85 */     if ((((paramInt >= 0) ? 1 : 0) & ((paramInt < this.subgeoms.length) ? 1 : 0)) != 0) {
/* 86 */       return (Point)this.subgeoms[paramInt];
/*    */     }
/* 88 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Point[] getPoints() {
/* 94 */     return (Point[])this.subgeoms;
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/PointComposedGeom.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
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
/*    */ public class Polygon
/*    */   extends ComposedGeom
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/*    */   
/*    */   public Polygon() {
/* 36 */     super(3);
/*    */   }
/*    */   
/*    */   public Polygon(LinearRing[] paramArrayOfLinearRing) {
/* 40 */     super(3, (Geometry[])paramArrayOfLinearRing);
/*    */   }
/*    */   
/*    */   public Polygon(String paramString) throws SQLException {
/* 44 */     this(paramString, false);
/*    */   }
/*    */   
/*    */   public Polygon(String paramString, boolean paramBoolean) throws SQLException {
/* 48 */     super(3, paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected Geometry createSubGeomInstance(String paramString, boolean paramBoolean) throws SQLException {
/* 52 */     return new LinearRing(paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected Geometry[] createSubGeomArray(int paramInt) {
/* 56 */     return (Geometry[])new LinearRing[paramInt];
/*    */   }
/*    */   
/*    */   public int numRings() {
/* 60 */     return this.subgeoms.length;
/*    */   }
/*    */   
/*    */   public LinearRing getRing(int paramInt) {
/* 64 */     if ((((paramInt >= 0) ? 1 : 0) & ((paramInt < this.subgeoms.length) ? 1 : 0)) != 0) {
/* 65 */       return (LinearRing)this.subgeoms[paramInt];
/*    */     }
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/Polygon.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
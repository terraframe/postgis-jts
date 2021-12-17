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
/*    */ public class MultiPolygon
/*    */   extends ComposedGeom
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/*    */   
/*    */   public MultiPolygon() {
/* 36 */     super(6);
/*    */   }
/*    */   
/*    */   public MultiPolygon(Polygon[] paramArrayOfPolygon) {
/* 40 */     super(6, (Geometry[])paramArrayOfPolygon);
/*    */   }
/*    */   
/*    */   public MultiPolygon(String paramString) throws SQLException {
/* 44 */     this(paramString, false);
/*    */   }
/*    */   
/*    */   protected MultiPolygon(String paramString, boolean paramBoolean) throws SQLException {
/* 48 */     super(6, paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected Geometry[] createSubGeomArray(int paramInt) {
/* 52 */     return (Geometry[])new Polygon[paramInt];
/*    */   }
/*    */   
/*    */   protected Geometry createSubGeomInstance(String paramString, boolean paramBoolean) throws SQLException {
/* 56 */     return new Polygon(paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   public int numPolygons() {
/* 60 */     return this.subgeoms.length;
/*    */   }
/*    */   
/*    */   public Polygon getPolygon(int paramInt) {
/* 64 */     if ((((paramInt >= 0) ? 1 : 0) & ((paramInt < this.subgeoms.length) ? 1 : 0)) != 0) {
/* 65 */       return (Polygon)this.subgeoms[paramInt];
/*    */     }
/* 67 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Polygon[] getPolygons() {
/* 72 */     return (Polygon[])this.subgeoms;
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/MultiPolygon.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
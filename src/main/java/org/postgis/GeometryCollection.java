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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeometryCollection
/*    */   extends ComposedGeom
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/*    */   public static final String GeoCollID = "GEOMETRYCOLLECTION";
/*    */   
/*    */   public GeometryCollection() {
/* 47 */     super(7);
/*    */   }
/*    */   
/*    */   public GeometryCollection(Geometry[] paramArrayOfGeometry) {
/* 51 */     super(7, paramArrayOfGeometry);
/*    */   }
/*    */   
/*    */   public GeometryCollection(String paramString) throws SQLException {
/* 55 */     this(paramString, false);
/*    */   }
/*    */   
/*    */   public GeometryCollection(String paramString, boolean paramBoolean) throws SQLException {
/* 59 */     super(7, paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected Geometry[] createSubGeomArray(int paramInt) {
/* 63 */     return new Geometry[paramInt];
/*    */   }
/*    */   
/*    */   protected Geometry createSubGeomInstance(String paramString, boolean paramBoolean) throws SQLException {
/* 67 */     return PGgeometry.geomFromString(paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected void innerWKT(StringBuffer paramStringBuffer) {
/* 71 */     this.subgeoms[0].outerWKT(paramStringBuffer, false);
/* 72 */     for (byte b = 1; b < this.subgeoms.length; b++) {
/* 73 */       paramStringBuffer.append(',');
/* 74 */       this.subgeoms[b].outerWKT(paramStringBuffer, false);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Geometry[] getGeometries() {
/* 79 */     return this.subgeoms;
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/GeometryCollection.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
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
/*    */ public class MultiLineString
/*    */   extends ComposedGeom
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/* 35 */   double len = -1.0D;
/*    */   
/*    */   public int hashCode() {
/* 38 */     return super.hashCode() ^ (int)length();
/*    */   }
/*    */   
/*    */   public MultiLineString() {
/* 42 */     super(5);
/*    */   }
/*    */   
/*    */   public MultiLineString(LineString[] paramArrayOfLineString) {
/* 46 */     super(5, (Geometry[])paramArrayOfLineString);
/*    */   }
/*    */   
/*    */   public MultiLineString(String paramString) throws SQLException {
/* 50 */     this(paramString, false);
/*    */   }
/*    */   
/*    */   public MultiLineString(String paramString, boolean paramBoolean) throws SQLException {
/* 54 */     super(5, paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected Geometry createSubGeomInstance(String paramString, boolean paramBoolean) throws SQLException {
/* 58 */     return new LineString(paramString, paramBoolean);
/*    */   }
/*    */   
/*    */   protected Geometry[] createSubGeomArray(int paramInt) {
/* 62 */     return (Geometry[])new LineString[paramInt];
/*    */   }
/*    */   
/*    */   public int numLines() {
/* 66 */     return this.subgeoms.length;
/*    */   }
/*    */   
/*    */   public LineString[] getLines() {
/* 70 */     return (LineString[])this.subgeoms.clone();
/*    */   }
/*    */   
/*    */   public LineString getLine(int paramInt) {
/* 74 */     if ((((paramInt >= 0) ? 1 : 0) & ((paramInt < this.subgeoms.length) ? 1 : 0)) != 0) {
/* 75 */       return (LineString)this.subgeoms[paramInt];
/*    */     }
/* 77 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public double length() {
/* 82 */     if (this.len < 0.0D) {
/* 83 */       LineString[] arrayOfLineString = (LineString[])this.subgeoms;
/* 84 */       if (arrayOfLineString.length < 1) {
/* 85 */         this.len = 0.0D;
/*    */       } else {
/* 87 */         double d = 0.0D;
/* 88 */         for (byte b = 0; b < arrayOfLineString.length; b++) {
/* 89 */           d += arrayOfLineString[b].length();
/*    */         }
/* 91 */         this.len = d;
/*    */       } 
/*    */     } 
/* 94 */     return this.len;
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/MultiLineString.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
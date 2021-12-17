/*     */ package org.postgis;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Geometry
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 256L;
/*     */   public static final int LINEARRING = 0;
/*     */   public static final int POINT = 1;
/*     */   public static final int LINESTRING = 2;
/*     */   public static final int POLYGON = 3;
/*     */   public static final int MULTIPOINT = 4;
/*     */   public static final int MULTILINESTRING = 5;
/*     */   public static final int MULTIPOLYGON = 6;
/*     */   public static final int GEOMETRYCOLLECTION = 7;
/*  77 */   public static final String[] ALLTYPES = new String[] { "", "POINT", "LINESTRING", "POLYGON", "MULTIPOINT", "MULTILINESTRING", "MULTIPOLYGON", "GEOMETRYCOLLECTION" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int dimension;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTypeString(int paramInt) {
/*  91 */     if (paramInt >= 0 && paramInt <= 7) {
/*  92 */       return ALLTYPES[paramInt];
/*     */     }
/*  94 */     throw new IllegalArgumentException("Unknown Geometry type" + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean haveMeasure = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int type;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public int srid = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Geometry(int paramInt) {
/* 126 */     this.type = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 133 */     return this.dimension | this.type * 4 | this.srid * 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 140 */     return (paramObject != null && paramObject instanceof Geometry && equals((Geometry)paramObject));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Geometry paramGeometry) {
/* 148 */     return (paramGeometry != null && this.dimension == paramGeometry.dimension && this.type == paramGeometry.type && this.srid == paramGeometry.srid && this.haveMeasure == paramGeometry.haveMeasure && paramGeometry.getClass().equals(getClass()) && equalsintern(paramGeometry));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean equalsintern(Geometry paramGeometry);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int numPoints();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Point getPoint(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Point getFirstPoint();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Point getLastPoint();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/* 193 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeString() {
/* 200 */     return getTypeString(this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMeasured() {
/* 205 */     return this.haveMeasure;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDimension() {
/* 215 */     return this.dimension;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSrid() {
/* 222 */     return this.srid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSrid(int paramInt) {
/* 230 */     this.srid = paramInt;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 234 */     StringBuffer stringBuffer = new StringBuffer();
/* 235 */     if (this.srid != -1) {
/* 236 */       stringBuffer.append("SRID=");
/* 237 */       stringBuffer.append(this.srid);
/* 238 */       stringBuffer.append(';');
/*     */     } 
/* 240 */     outerWKT(stringBuffer, true);
/* 241 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void outerWKT(StringBuffer paramStringBuffer, boolean paramBoolean) {
/* 249 */     paramStringBuffer.append(getTypeString());
/* 250 */     if (paramBoolean && this.haveMeasure && this.dimension == 2) {
/* 251 */       paramStringBuffer.append('M');
/*     */     }
/* 253 */     mediumWKT(paramStringBuffer);
/*     */   }
/*     */   
/*     */   public final void outerWKT(StringBuffer paramStringBuffer) {
/* 257 */     outerWKT(paramStringBuffer, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mediumWKT(StringBuffer paramStringBuffer) {
/* 265 */     paramStringBuffer.append('(');
/* 266 */     innerWKT(paramStringBuffer);
/* 267 */     paramStringBuffer.append(')');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void innerWKT(StringBuffer paramStringBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 280 */     StringBuffer stringBuffer = new StringBuffer();
/* 281 */     mediumWKT(stringBuffer);
/* 282 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkConsistency() {
/* 300 */     return (this.dimension >= 2 && this.dimension <= 3 && this.type >= 0 && this.type <= 7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String initSRID(String paramString) {
/* 309 */     paramString = paramString.trim();
/* 310 */     if (paramString.startsWith("SRID=")) {
/* 311 */       int i = paramString.indexOf(';', 5);
/* 312 */       if (i == -1) {
/* 313 */         throw new IllegalArgumentException("Error parsing Geometry - SRID not delimited with ';' ");
/*     */       }
/*     */       
/* 316 */       this.srid = Integer.parseInt(paramString.substring(5, i));
/* 317 */       return paramString.substring(i + 1).trim();
/*     */     } 
/*     */     
/* 320 */     return paramString;
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/Geometry.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.postgis;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;

/*     */ import org.postgresql.util.PGtokenizer;
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
/*     */ public abstract class ComposedGeom
/*     */   extends Geometry
/*     */ {
/*     */   private static final long serialVersionUID = 256L;
/*  48 */   public static final Geometry[] EMPTY = new Geometry[0];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   protected Geometry[] subgeoms = EMPTY;
/*     */   boolean nohash;
/*     */   int hashcode;
/*     */   
/*     */   public ComposedGeom(int paramInt)
/*     */   {
/*  61 */     super(paramInt);
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
/* 233 */     this.nohash = true;
/* 234 */     this.hashcode = 0; } public Geometry getSubGeometry(int paramInt) { return this.subgeoms[paramInt]; } public int numGeoms() { return this.subgeoms.length; } protected ComposedGeom(int paramInt, Geometry[] paramArrayOfGeometry) { this(paramInt); this.subgeoms = paramArrayOfGeometry; if (paramArrayOfGeometry.length > 0) { this.dimension = (paramArrayOfGeometry[0]).dimension; this.haveMeasure = (paramArrayOfGeometry[0]).haveMeasure; } else { this.dimension = 0; }  } protected ComposedGeom(int paramInt, String paramString, boolean paramBoolean) throws SQLException { super(paramInt); this.nohash = true; this.hashcode = 0; paramString = initSRID(paramString); String str = getTypeString(); if (paramString.indexOf(str) == 0) { int j = str.length(); if (paramString.charAt(j) == 'M') { j++; paramBoolean = true; }  paramString = paramString.substring(j).trim(); } else if (paramString.charAt(0) != '(') { throw new SQLException("Error parsing a " + str + " out of " + paramString); }  if (paramString.equals("(EMPTY)")) return;  PGtokenizer pGtokenizer = new PGtokenizer(PGtokenizer.removePara(paramString), ','); int i = pGtokenizer.getSize(); this.subgeoms = createSubGeomArray(i); for (byte b = 0; b < i; b++) this.subgeoms[b] = createSubGeomInstance(pGtokenizer.getToken(b), paramBoolean);  this.dimension = (this.subgeoms[0]).dimension; this.haveMeasure = (this.subgeoms[0]).haveMeasure; }
/*     */   protected abstract Geometry createSubGeomInstance(String paramString, boolean paramBoolean) throws SQLException;
/*     */   protected abstract Geometry[] createSubGeomArray(int paramInt);
/* 237 */   protected boolean equalsintern(Geometry paramGeometry) { ComposedGeom composedGeom = (ComposedGeom)paramGeometry; if (composedGeom.subgeoms == null && this.subgeoms == null) return true;  if (composedGeom.subgeoms == null || this.subgeoms == null) return false;  if (composedGeom.subgeoms.length != this.subgeoms.length) return false;  if (this.subgeoms.length == 0) return true;  for (byte b = 0; b < this.subgeoms.length; b++) { if (!composedGeom.subgeoms[b].equalsintern(this.subgeoms[b])) return false;  }  return true; } public int numPoints() { if (this.subgeoms == null || this.subgeoms.length == 0) return 0;  int i = 0; for (byte b = 0; b < this.subgeoms.length; b++) i += this.subgeoms[b].numPoints();  return i; } public Point getPoint(int paramInt) { if (paramInt < 0) throw new ArrayIndexOutOfBoundsException("Negative index not allowed");  if (this.subgeoms == null || this.subgeoms.length == 0) throw new ArrayIndexOutOfBoundsException("Empty Geometry has no Points!");  for (byte b = 0; b < this.subgeoms.length; b++) { Geometry geometry = this.subgeoms[b]; int i = geometry.numPoints(); if (paramInt < i) return geometry.getPoint(paramInt);  paramInt -= i; }  throw new ArrayIndexOutOfBoundsException("Index too large!"); } public int hashCode() { if (this.nohash) {
/* 238 */       this.hashcode = super.hashCode() ^ this.subgeoms.hashCode();
/* 239 */       this.nohash = false;
/*     */     } 
/* 241 */     return this.hashcode; }
/*     */   public Point getLastPoint() { if (this.subgeoms == null || this.subgeoms.length == 0) throw new ArrayIndexOutOfBoundsException("Empty Geometry has no Points!");  return this.subgeoms[this.subgeoms.length - 1].getLastPoint(); }
/*     */   public Point getFirstPoint() { if (this.subgeoms == null || this.subgeoms.length == 0) throw new ArrayIndexOutOfBoundsException("Empty Geometry has no Points!");  return this.subgeoms[0].getFirstPoint(); }
/*     */   public Iterator iterator() { return Arrays.<Geometry>asList(this.subgeoms).iterator(); }
/* 245 */   public boolean isEmpty() { return (this.subgeoms == null || this.subgeoms.length == 0); } protected void mediumWKT(StringBuffer paramStringBuffer) { if (this.subgeoms == null || this.subgeoms.length == 0) { paramStringBuffer.append(" EMPTY"); } else { paramStringBuffer.append('('); innerWKT(paramStringBuffer); paramStringBuffer.append(')'); }  } protected void innerWKT(StringBuffer paramStringBuffer) { this.subgeoms[0].mediumWKT(paramStringBuffer); for (byte b = 1; b < this.subgeoms.length; b++) { paramStringBuffer.append(','); this.subgeoms[b].mediumWKT(paramStringBuffer); }  } public boolean checkConsistency() { if (super.checkConsistency()) {
/* 246 */       if (isEmpty()) {
/* 247 */         return true;
/*     */       }
/*     */       
/* 250 */       int i = this.dimension;
/* 251 */       boolean bool = this.haveMeasure;
/* 252 */       int j = this.srid;
/* 253 */       for (byte b = 0; b < this.subgeoms.length; b++) {
/* 254 */         Geometry geometry = this.subgeoms[b];
/* 255 */         if (!geometry.checkConsistency() || geometry.dimension != i || geometry.haveMeasure != bool || geometry.srid != j)
/*     */         {
/* 257 */           return false;
/*     */         }
/*     */       } 
/* 260 */       return true;
/*     */     } 
/* 262 */     return false; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSrid(int paramInt) {
/* 267 */     super.setSrid(paramInt);
/* 268 */     for (byte b = 0; b < this.subgeoms.length; b++)
/* 269 */       this.subgeoms[b].setSrid(paramInt); 
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/ComposedGeom.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
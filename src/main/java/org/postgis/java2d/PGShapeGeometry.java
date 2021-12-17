/*     */ package org.postgis.java2d;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.sql.SQLException;
/*     */ import org.postgresql.util.PGobject;
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
/*     */ public class PGShapeGeometry
/*     */   extends PGobject
/*     */   implements Shape
/*     */ {
/*     */   private static final long serialVersionUID = 256L;
/*  67 */   static final ShapeBinaryParser parser = new ShapeBinaryParser();
/*     */ 
/*     */ 
/*     */   
/*     */   private final GeneralPath path;
/*     */ 
/*     */   
/*     */   private int srid;
/*     */ 
/*     */ 
/*     */   
/*     */   public PGShapeGeometry() {
/*  79 */     setType("geometry");
/*  80 */     this.path = new GeneralPath(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public PGShapeGeometry(GeneralPath paramGeneralPath, int paramInt) {
/*  85 */     setType("geometry");
/*  86 */     this.path = paramGeneralPath;
/*  87 */     this.srid = paramInt;
/*     */   }
/*     */ 
/*     */   
/*     */   public PGShapeGeometry(String paramString) throws SQLException {
/*  92 */     this();
/*  93 */     setValue(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(String paramString) throws SQLException {
/* 103 */     this.srid = parser.parse(paramString, this.path);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 107 */     return "PGShapeGeometry " + this.path.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 112 */     return null;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 116 */     if (paramObject instanceof PGShapeGeometry)
/* 117 */       return ((PGShapeGeometry)paramObject).path.equals(this.path); 
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSRID() {
/* 123 */     return this.srid;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2) {
/* 128 */     return this.path.contains(paramDouble1, paramDouble2);
/*     */   }
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 132 */     return this.path.contains(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */   
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/* 136 */     return this.path.intersects(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*     */   }
/*     */   
/*     */   public Rectangle getBounds() {
/* 140 */     return this.path.getBounds();
/*     */   }
/*     */   
/*     */   public boolean contains(Point2D paramPoint2D) {
/* 144 */     return this.path.contains(paramPoint2D);
/*     */   }
/*     */   
/*     */   public Rectangle2D getBounds2D() {
/* 148 */     return this.path.getBounds2D();
/*     */   }
/*     */   
/*     */   public boolean contains(Rectangle2D paramRectangle2D) {
/* 152 */     return this.path.contains(paramRectangle2D);
/*     */   }
/*     */   
/*     */   public boolean intersects(Rectangle2D paramRectangle2D) {
/* 156 */     return this.path.intersects(paramRectangle2D);
/*     */   }
/*     */   
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform) {
/* 160 */     return this.path.getPathIterator(paramAffineTransform);
/*     */   }
/*     */   
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble) {
/* 164 */     return this.path.getPathIterator(paramAffineTransform, paramDouble);
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/java2d/PGShapeGeometry.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
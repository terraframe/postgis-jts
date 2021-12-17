/*     */ package org.postgis;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineString
/*     */   extends PointComposedGeom
/*     */ {
/*     */   private static final long serialVersionUID = 256L;
/*  35 */   double len = -1.0D;
/*     */   
/*     */   public LineString() {
/*  38 */     super(2);
/*     */   }
/*     */   
/*     */   public LineString(Point[] paramArrayOfPoint) {
/*  42 */     super(2, paramArrayOfPoint);
/*     */   }
/*     */   
/*     */   public LineString(String paramString) throws SQLException {
/*  46 */     super(2, paramString);
/*     */   }
/*     */   
/*     */   public LineString(String paramString, boolean paramBoolean) throws SQLException {
/*  50 */     super(2, paramString, paramBoolean);
/*     */   }
/*     */   
/*     */   public LineString reverse() {
/*  54 */     Point[] arrayOfPoint1 = getPoints();
/*  55 */     int i = arrayOfPoint1.length;
/*     */     
/*  57 */     Point[] arrayOfPoint2 = new Point[i]; byte b; int j;
/*  58 */     for (b = 0, j = i - 1; b < i; b++, j--) {
/*  59 */       arrayOfPoint2[b] = arrayOfPoint1[j];
/*     */     }
/*  61 */     return new LineString(arrayOfPoint2);
/*     */   }
/*     */   
/*     */   public LineString concat(LineString paramLineString) {
/*  65 */     Point[] arrayOfPoint1 = getPoints();
/*  66 */     Point[] arrayOfPoint2 = paramLineString.getPoints();
/*     */     
/*  68 */     boolean bool = (getLastPoint() == null || getLastPoint().equals(paramLineString.getFirstPoint())) ? true : false;
/*     */     
/*  70 */     int i = arrayOfPoint1.length + arrayOfPoint1.length - (bool ? 1 : 0);
/*  71 */     Point[] arrayOfPoint3 = new Point[i];
/*     */     
/*     */     byte b1;
/*     */     
/*  75 */     for (b1 = 0; b1 < arrayOfPoint1.length; b1++) {
/*  76 */       arrayOfPoint3[b1] = arrayOfPoint1[b1];
/*     */     }
/*  78 */     if (!bool) {
/*  79 */       arrayOfPoint3[b1++] = paramLineString.getFirstPoint();
/*     */     }
/*  81 */     for (byte b2 = 1; b2 < arrayOfPoint2.length; b2++, b1++) {
/*  82 */       arrayOfPoint3[b1] = arrayOfPoint2[b2];
/*     */     }
/*  84 */     return new LineString(arrayOfPoint3);
/*     */   }
/*     */   
/*     */   public double length() {
/*  88 */     if (this.len < 0.0D) {
/*  89 */       Point[] arrayOfPoint = getPoints();
/*  90 */       if (arrayOfPoint == null || arrayOfPoint.length < 2) {
/*  91 */         this.len = 0.0D;
/*     */       } else {
/*  93 */         double d = 0.0D;
/*  94 */         for (byte b = 1; b < arrayOfPoint.length; b++) {
/*  95 */           d += arrayOfPoint[b - 1].distance(arrayOfPoint[b]);
/*     */         }
/*  97 */         this.len = d;
/*     */       } 
/*     */     } 
/* 100 */     return this.len;
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/LineString.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
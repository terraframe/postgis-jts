/*     */ package org.postgis;
/*     */ 
/*     */ import java.sql.SQLException;
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
/*     */ public class Point
/*     */   extends Geometry
/*     */ {
/*     */   private static final long serialVersionUID = 256L;
/*     */   public static final boolean CUTINTS = true;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   
/*     */   public int hashCode() {
/*  40 */     return super.hashCode() ^ hashCode(this.x) ^ hashCode(this.y) ^ hashCode(this.z) ^ hashCode(this.m);
/*     */   }
/*     */   
/*     */   public static int hashCode(double paramDouble) {
/*  44 */     long l = Double.doubleToLongBits(paramDouble);
/*  45 */     return (int)(l ^ l >>> 32L);
/*     */   }
/*     */   
/*     */   protected boolean equalsintern(Geometry paramGeometry) {
/*  49 */     Point point = (Point)paramGeometry;
/*  50 */     return equals(point);
/*     */   }
/*     */   
/*     */   public final boolean equals(Point paramPoint) {
/*  54 */     boolean bool1 = (this.x == paramPoint.x) ? true : false;
/*  55 */     boolean bool2 = (this.y == paramPoint.y) ? true : false;
/*  56 */     boolean bool3 = (this.dimension == 2 || this.z == paramPoint.z) ? true : false;
/*  57 */     boolean bool4 = (!this.haveMeasure || this.m == paramPoint.m) ? true : false;
/*  58 */     return (bool1 && bool2 && bool3 && bool4);
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getPoint(int paramInt) {
/*  63 */     if (paramInt == 0) {
/*  64 */       return this;
/*     */     }
/*  66 */     throw new ArrayIndexOutOfBoundsException("Point only has a single Point! " + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Point getFirstPoint() {
/*  72 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getLastPoint() {
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public int numPoints() {
/*  81 */     return 1;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 107 */   public double m = 0.0D;
/*     */   
/*     */   public Point() {
/* 110 */     super(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 119 */     this();
/* 120 */     this.x = paramDouble1;
/* 121 */     this.y = paramDouble2;
/* 122 */     this.z = paramDouble3;
/* 123 */     this.dimension = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point(double paramDouble1, double paramDouble2) {
/* 131 */     this();
/* 132 */     this.x = paramDouble1;
/* 133 */     this.y = paramDouble2;
/* 134 */     this.z = 0.0D;
/* 135 */     this.dimension = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point(String paramString) throws SQLException {
/* 144 */     this(paramString, false);
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
/*     */   protected Point(String paramString, boolean paramBoolean) throws SQLException {
/* 160 */     this(); int i;
/* 161 */     paramString = initSRID(paramString);
/*     */     
/* 163 */     if (paramString.indexOf("POINTM") == 0) {
/* 164 */       paramBoolean = true;
/* 165 */       paramString = paramString.substring(6).trim();
/* 166 */     } else if (paramString.indexOf("POINT") == 0) {
/* 167 */       paramString = paramString.substring(5).trim();
/*     */     } 
/* 169 */     PGtokenizer pGtokenizer = new PGtokenizer(PGtokenizer.removePara(paramString), ' ');
/*     */     try {
/* 171 */       this.x = Double.valueOf(pGtokenizer.getToken(0)).doubleValue();
/* 172 */       this.y = Double.valueOf(pGtokenizer.getToken(1)).doubleValue();
/* 173 */       i = paramBoolean | ((pGtokenizer.getSize() == 4) ? 1 : 0);
/* 174 */       if ((pGtokenizer.getSize() == 3 && i == 0) || pGtokenizer.getSize() == 4) {
/* 175 */         this.z = Double.valueOf(pGtokenizer.getToken(2)).doubleValue();
/* 176 */         this.dimension = 3;
/*     */       } else {
/* 178 */         this.dimension = 2;
/*     */       } 
/* 180 */       if (i != 0) {
/* 181 */         this.m = Double.valueOf(pGtokenizer.getToken(this.dimension)).doubleValue();
/*     */       }
/* 183 */     } catch (NumberFormatException numberFormatException) {
/* 184 */       throw new SQLException("Error parsing Point: " + numberFormatException.toString());
/*     */     } 
/* 186 */     this.haveMeasure = i;
/*     */   }
/*     */   
/*     */   public void innerWKT(StringBuffer paramStringBuffer) {
/* 190 */     paramStringBuffer.append(this.x);
/*     */     
/* 192 */     cutint(paramStringBuffer);
/* 193 */     paramStringBuffer.append(' ');
/* 194 */     paramStringBuffer.append(this.y);
/*     */     
/* 196 */     cutint(paramStringBuffer);
/* 197 */     if (this.dimension == 3) {
/* 198 */       paramStringBuffer.append(' ');
/* 199 */       paramStringBuffer.append(this.z);
/*     */       
/* 201 */       cutint(paramStringBuffer);
/*     */     } 
/* 203 */     if (this.haveMeasure) {
/* 204 */       paramStringBuffer.append(' ');
/* 205 */       paramStringBuffer.append(this.m);
/*     */       
/* 207 */       cutint(paramStringBuffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void cutint(StringBuffer paramStringBuffer) {
/* 212 */     int i = paramStringBuffer.length() - 2;
/* 213 */     if (paramStringBuffer.charAt(i + 1) == '0' && paramStringBuffer.charAt(i) == '.') {
/* 214 */       paramStringBuffer.setLength(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getX() {
/* 219 */     return this.x;
/*     */   }
/*     */   
/*     */   public double getY() {
/* 223 */     return this.y;
/*     */   }
/*     */   
/*     */   public double getZ() {
/* 227 */     return this.z;
/*     */   }
/*     */   
/*     */   public double getM() {
/* 231 */     return this.m;
/*     */   }
/*     */   
/*     */   public void setX(double paramDouble) {
/* 235 */     this.x = paramDouble;
/*     */   }
/*     */   
/*     */   public void setY(double paramDouble) {
/* 239 */     this.y = paramDouble;
/*     */   }
/*     */   
/*     */   public void setZ(double paramDouble) {
/* 243 */     this.z = paramDouble;
/*     */   }
/*     */   
/*     */   public void setM(double paramDouble) {
/* 247 */     this.haveMeasure = true;
/* 248 */     this.m = paramDouble;
/*     */   }
/*     */   
/*     */   public void setX(int paramInt) {
/* 252 */     this.x = paramInt;
/*     */   }
/*     */   
/*     */   public void setY(int paramInt) {
/* 256 */     this.y = paramInt;
/*     */   }
/*     */   
/*     */   public void setZ(int paramInt) {
/* 260 */     this.z = paramInt;
/*     */   }
/*     */   
/*     */   public double distance(Point paramPoint) {
/*     */     double d2, d3;
/* 265 */     if (this.dimension != paramPoint.dimension) {
/* 266 */       throw new IllegalArgumentException("Points have different dimensions!");
/*     */     }
/* 268 */     double d1 = this.x - paramPoint.x;
/* 269 */     switch (this.dimension) {
/*     */       case 1:
/* 271 */         return Math.sqrt(d1 * d1);
/*     */       case 2:
/* 273 */         d2 = this.y - paramPoint.y;
/* 274 */         return Math.sqrt(d1 * d1 + d2 * d2);
/*     */       case 3:
/* 276 */         d2 = this.y - paramPoint.y;
/* 277 */         d3 = this.z - paramPoint.z;
/* 278 */         return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*     */     } 
/* 280 */     throw new IllegalArgumentException("Illegal dimension of Point" + this.dimension);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkConsistency() {
/* 285 */     return (super.checkConsistency() && (this.dimension == 3 || this.z == 0.0D) && (this.haveMeasure || this.m == 0.0D));
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/Point.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
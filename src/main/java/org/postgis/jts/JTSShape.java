/*     */ package org.postgis.jts;
/*     */ 
/*     */ import com.vividsolutions.jts.geom.Coordinate;
/*     */ import com.vividsolutions.jts.geom.CoordinateSequence;
/*     */ import com.vividsolutions.jts.geom.Envelope;
/*     */ import com.vividsolutions.jts.geom.Geometry;
/*     */ import com.vividsolutions.jts.geom.GeometryCollection;
/*     */ import com.vividsolutions.jts.geom.GeometryFactory;
/*     */ import com.vividsolutions.jts.geom.LineString;
/*     */ import com.vividsolutions.jts.geom.LinearRing;
/*     */ import com.vividsolutions.jts.geom.Point;
/*     */ import com.vividsolutions.jts.geom.Polygon;
/*     */ import com.vividsolutions.jts.geom.impl.PackedCoordinateSequence;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.PathIterator;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ 
/*     */ public class JTSShape
/*     */   implements Shape {
/*  23 */   static GeometryFactory fac = new GeometryFactory();
/*     */   
/*     */   Geometry geom;
/*     */   
/*  27 */   static final LinearRing[] NOSHELLS = new LinearRing[0];
/*     */   
/*     */   public JTSShape(Geometry paramGeometry) {
/*  30 */     this.geom = paramGeometry;
/*     */   }
/*     */   
/*     */   public JTSShape(JtsGeometry paramJtsGeometry) {
/*  34 */     this(paramJtsGeometry.getGeometry());
/*     */   }
/*     */   
/*     */   public boolean contains(Point2D paramPoint2D) {
/*  38 */     return contains(paramPoint2D.getX(), paramPoint2D.getY());
/*     */   }
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2) {
/*  42 */     Coordinate coordinate = new Coordinate(paramDouble1, paramDouble2);
/*  43 */     Point point = fac.createPoint(coordinate);
/*  44 */     return this.geom.contains((Geometry)point);
/*     */   }
/*     */   
/*     */   public boolean contains(Rectangle2D paramRectangle2D) {
/*  48 */     return contains(paramRectangle2D.getMinX(), paramRectangle2D.getMinY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*     */   }
/*     */   
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  52 */     Polygon polygon = createRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*  53 */     return this.geom.contains((Geometry)polygon);
/*     */   }
/*     */   
/*     */   protected Polygon createRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  57 */     double[] arrayOfDouble = { paramDouble1, paramDouble2, paramDouble1 + paramDouble3, paramDouble2, paramDouble1 + paramDouble3, paramDouble2 + paramDouble4, paramDouble1, paramDouble2 + paramDouble4, paramDouble1, paramDouble2 };
/*  58 */     PackedCoordinateSequence.Double double_ = new PackedCoordinateSequence.Double(arrayOfDouble, 2);
/*  59 */     return fac.createPolygon(fac.createLinearRing((CoordinateSequence)double_), NOSHELLS);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle2D getBounds2D() {
/*  64 */     Envelope envelope = this.geom.getEnvelopeInternal();
/*  65 */     return new Rectangle2D.Double(envelope.getMinX(), envelope.getMaxX(), envelope.getWidth(), envelope.getHeight());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Rectangle getBounds() {
/*  71 */     return getBounds2D().getBounds();
/*     */   }
/*     */   
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform) {
/*  75 */     return getPathIterator(this.geom, paramAffineTransform);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble) {
/*  81 */     return getPathIterator(paramAffineTransform);
/*     */   }
/*     */   
/*     */   public boolean intersects(Rectangle2D paramRectangle2D) {
/*  85 */     return intersects(paramRectangle2D.getMinX(), paramRectangle2D.getMinY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight());
/*     */   }
/*     */   
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
/*  89 */     Polygon polygon = createRect(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
/*  90 */     return this.geom.intersects((Geometry)polygon);
/*     */   }
/*     */   
/*     */   public static GeometryPathIterator getPathIterator(Geometry paramGeometry, AffineTransform paramAffineTransform) {
/*  94 */     if (paramGeometry instanceof Point)
/*  95 */       return new PointPathIterator((Point)paramGeometry, paramAffineTransform); 
/*  96 */     if (paramGeometry instanceof LineString)
/*  97 */       return new LineStringPathIterator((LineString)paramGeometry, paramAffineTransform); 
/*  98 */     if (paramGeometry instanceof Polygon) {
/*  99 */       return new PolygonPathIterator((Polygon)paramGeometry, paramAffineTransform);
/*     */     }
/* 101 */     return new GeometryCollectionPathIterator((GeometryCollection)paramGeometry, paramAffineTransform);
/*     */   }
/*     */   
/*     */   public static abstract class GeometryPathIterator
/*     */     implements PathIterator
/*     */   {
/*     */     protected final AffineTransform at;
/* 108 */     protected int index = 0;
/*     */     
/*     */     GeometryPathIterator(AffineTransform param1AffineTransform) {
/* 111 */       this.at = param1AffineTransform;
/*     */     }
/*     */     
/*     */     public final int getWindingRule() {
/* 115 */       return 0;
/*     */     }
/*     */     
/*     */     public void next() {
/* 119 */       this.index++;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PointPathIterator extends GeometryPathIterator {
/*     */     final Point p;
/*     */     
/*     */     public PointPathIterator(Point param1Point, AffineTransform param1AffineTransform) {
/* 127 */       super(param1AffineTransform);
/* 128 */       this.p = param1Point;
/*     */     }
/*     */     
/*     */     public int currentSegment(float[] param1ArrayOffloat) {
/* 132 */       switch (this.index) {
/*     */         case 0:
/* 134 */           param1ArrayOffloat[0] = (float)this.p.getX();
/* 135 */           param1ArrayOffloat[1] = (float)this.p.getY();
/* 136 */           this.at.transform(param1ArrayOffloat, 0, param1ArrayOffloat, 0, 1);
/* 137 */           return 0;
/*     */         case 1:
/* 139 */           return 4;
/*     */       } 
/* 141 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int currentSegment(double[] param1ArrayOfdouble) {
/* 146 */       switch (this.index) {
/*     */         case 0:
/* 148 */           param1ArrayOfdouble[0] = this.p.getX();
/* 149 */           param1ArrayOfdouble[1] = this.p.getY();
/* 150 */           this.at.transform(param1ArrayOfdouble, 0, param1ArrayOfdouble, 0, 1);
/* 151 */           return 0;
/*     */         case 1:
/* 153 */           return 4;
/*     */       } 
/* 155 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDone() {
/* 160 */       return (this.index > 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LineStringPathIterator
/*     */     extends GeometryPathIterator {
/*     */     CoordinateSequence cs;
/*     */     final boolean isRing;
/*     */     
/*     */     public LineStringPathIterator(LineString param1LineString, AffineTransform param1AffineTransform) {
/* 170 */       super(param1AffineTransform);
/* 171 */       this.cs = param1LineString.getCoordinateSequence();
/* 172 */       this.isRing = param1LineString instanceof LinearRing;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void reInit(CoordinateSequence param1CoordinateSequence) {
/* 177 */       this.cs = param1CoordinateSequence;
/* 178 */       this.index = 0;
/*     */     }
/*     */     
/*     */     public int currentSegment(float[] param1ArrayOffloat) {
/* 182 */       if (this.index == 0) {
/* 183 */         param1ArrayOffloat[0] = (float)this.cs.getOrdinate(this.index, 0);
/* 184 */         param1ArrayOffloat[1] = (float)this.cs.getOrdinate(this.index, 1);
/* 185 */         this.at.transform(param1ArrayOffloat, 0, param1ArrayOffloat, 0, 1);
/* 186 */         return 0;
/* 187 */       }  if (this.index < this.cs.size()) {
/* 188 */         param1ArrayOffloat[0] = (float)this.cs.getOrdinate(this.index, 0);
/* 189 */         param1ArrayOffloat[1] = (float)this.cs.getOrdinate(this.index, 1);
/* 190 */         this.at.transform(param1ArrayOffloat, 0, param1ArrayOffloat, 0, 1);
/* 191 */         return 1;
/* 192 */       }  if (this.isRing && this.index == this.cs.size()) {
/* 193 */         return 4;
/*     */       }
/* 195 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public int currentSegment(double[] param1ArrayOfdouble) {
/* 200 */       if (this.index == 0) {
/* 201 */         param1ArrayOfdouble[0] = this.cs.getOrdinate(this.index, 0);
/* 202 */         param1ArrayOfdouble[1] = this.cs.getOrdinate(this.index, 1);
/* 203 */         this.at.transform(param1ArrayOfdouble, 0, param1ArrayOfdouble, 0, 1);
/* 204 */         return 0;
/* 205 */       }  if (this.index < this.cs.size()) {
/* 206 */         param1ArrayOfdouble[0] = this.cs.getOrdinate(this.index, 0);
/* 207 */         param1ArrayOfdouble[1] = this.cs.getOrdinate(this.index, 1);
/* 208 */         this.at.transform(param1ArrayOfdouble, 0, param1ArrayOfdouble, 0, 1);
/* 209 */         return 1;
/* 210 */       }  if (this.isRing && this.index == this.cs.size()) {
/* 211 */         return 4;
/*     */       }
/* 213 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDone() {
/* 218 */       return this.isRing ? ((this.index > this.cs.size())) : ((this.index >= this.cs.size()));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class PolygonPathIterator extends LineStringPathIterator {
/*     */     final Polygon pg;
/* 224 */     int outerindex = -1;
/*     */     
/*     */     public PolygonPathIterator(Polygon param1Polygon, AffineTransform param1AffineTransform) {
/* 227 */       super(param1Polygon.getExteriorRing(), param1AffineTransform);
/* 228 */       this.pg = param1Polygon;
/* 229 */       this.index = -1;
/*     */     }
/*     */     
/*     */     public boolean isDone() {
/* 233 */       return (this.outerindex >= this.pg.getNumInteriorRing());
/*     */     }
/*     */     
/*     */     public void next() {
/* 237 */       super.next();
/* 238 */       if (super.isDone()) {
/* 239 */         this.outerindex++;
/* 240 */         if (this.outerindex < this.pg.getNumInteriorRing())
/* 241 */           reInit(this.pg.getInteriorRingN(this.outerindex).getCoordinateSequence()); 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GeometryCollectionPathIterator
/*     */     extends GeometryPathIterator {
/*     */     final GeometryCollection coll;
/*     */     JTSShape.GeometryPathIterator current;
/*     */     
/*     */     public GeometryCollectionPathIterator(GeometryCollection param1GeometryCollection, AffineTransform param1AffineTransform) {
/* 252 */       super(param1AffineTransform);
/* 253 */       this.coll = param1GeometryCollection;
/* 254 */       this.current = JTSShape.getPathIterator(this.coll.getGeometryN(this.index), param1AffineTransform);
/*     */     }
/*     */     
/*     */     public boolean isDone() {
/* 258 */       return (this.index > this.coll.getNumGeometries());
/*     */     }
/*     */     
/*     */     public void next() {
/* 262 */       this.current.next();
/* 263 */       if (this.current.isDone()) {
/* 264 */         this.index++;
/* 265 */         if (this.index < this.coll.getNumGeometries()) {
/* 266 */           this.current = JTSShape.getPathIterator(this.coll.getGeometryN(this.index), this.at);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public int currentSegment(float[] param1ArrayOffloat) {
/* 272 */       return this.current.currentSegment(param1ArrayOffloat);
/*     */     }
/*     */     
/*     */     public int currentSegment(double[] param1ArrayOfdouble) {
/* 276 */       return this.current.currentSegment(param1ArrayOfdouble);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/jts/JTSShape.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
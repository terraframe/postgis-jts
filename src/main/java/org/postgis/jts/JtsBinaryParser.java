/*     */ package org.postgis.jts;
/*     */ 
/*     */ import com.vividsolutions.jts.geom.Coordinate;
/*     */ import com.vividsolutions.jts.geom.CoordinateSequence;
/*     */ import com.vividsolutions.jts.geom.Geometry;
/*     */ import com.vividsolutions.jts.geom.GeometryCollection;
/*     */ import com.vividsolutions.jts.geom.LineString;
/*     */ import com.vividsolutions.jts.geom.LinearRing;
/*     */ import com.vividsolutions.jts.geom.MultiLineString;
/*     */ import com.vividsolutions.jts.geom.MultiPoint;
/*     */ import com.vividsolutions.jts.geom.MultiPolygon;
/*     */ import com.vividsolutions.jts.geom.Point;
/*     */ import com.vividsolutions.jts.geom.Polygon;
/*     */ import com.vividsolutions.jts.geom.impl.PackedCoordinateSequence;
/*     */ import org.postgis.binary.ByteGetter;
/*     */ import org.postgis.binary.ValueGetter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JtsBinaryParser
/*     */ {
/*     */   public static ValueGetter valueGetterForEndian(ByteGetter paramByteGetter) {
/*  60 */     if (paramByteGetter.get(0) == 0)
/*  61 */       return (ValueGetter)new ValueGetter.XDR(paramByteGetter); 
/*  62 */     if (paramByteGetter.get(0) == 1) {
/*  63 */       return (ValueGetter)new ValueGetter.NDR(paramByteGetter);
/*     */     }
/*  65 */     throw new IllegalArgumentException("Unknown Endian type:" + paramByteGetter.get(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Geometry parse(String paramString) {
/*  73 */     ByteGetter.StringByteGetter stringByteGetter = new ByteGetter.StringByteGetter(paramString);
/*  74 */     return parseGeometry(valueGetterForEndian((ByteGetter)stringByteGetter));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Geometry parse(byte[] paramArrayOfbyte) {
/*  81 */     ByteGetter.BinaryByteGetter binaryByteGetter = new ByteGetter.BinaryByteGetter(paramArrayOfbyte);
/*  82 */     return parseGeometry(valueGetterForEndian((ByteGetter)binaryByteGetter));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Geometry parseGeometry(ValueGetter paramValueGetter) {
/*  87 */     return parseGeometry(paramValueGetter, 0, false); } protected Geometry parseGeometry(ValueGetter paramValueGetter, int paramInt, boolean paramBoolean) { Point point; LineString lineString; Polygon polygon;
/*     */     MultiPoint multiPoint;
/*     */     MultiLineString multiLineString;
/*     */     MultiPolygon multiPolygon;
/*     */     GeometryCollection geometryCollection;
/*  92 */     byte b = paramValueGetter.getByte();
/*  93 */     if (b != paramValueGetter.endian) {
/*  94 */       throw new IllegalArgumentException("Endian inconsistency!");
/*     */     }
/*  96 */     int i = paramValueGetter.getInt();
/*     */     
/*  98 */     int j = i & 0x1FFFFFFF;
/*     */     
/* 100 */     boolean bool1 = ((i & Integer.MIN_VALUE) != 0) ? true : false;
/* 101 */     boolean bool2 = ((i & 0x40000000) != 0) ? true : false;
/* 102 */     boolean bool3 = ((i & 0x20000000) != 0) ? true : false;
/*     */     
/* 104 */     if (bool3) {
/* 105 */       int k = paramValueGetter.getInt();
/* 106 */       if (paramBoolean && k != paramInt) {
/* 107 */         throw new IllegalArgumentException("Inconsistent srids in complex geometry: " + paramInt + ", " + k);
/*     */       }
/* 109 */       paramInt = k;
/*     */     }
/* 111 */     else if (!paramBoolean) {
/* 112 */       paramInt = -1;
/*     */     } 
/*     */ 
/*     */     
/* 116 */     switch (j) {
/*     */       case 1:
/* 118 */         point = parsePoint(paramValueGetter, bool1, bool2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 142 */         point.setSRID(paramInt);
/*     */         
/* 144 */         return (Geometry)point;case 2: lineString = parseLineString(paramValueGetter, bool1, bool2); lineString.setSRID(paramInt); return (Geometry)lineString;case 3: polygon = parsePolygon(paramValueGetter, bool1, bool2, paramInt); polygon.setSRID(paramInt); return (Geometry)polygon;case 4: multiPoint = parseMultiPoint(paramValueGetter, paramInt); multiPoint.setSRID(paramInt); return (Geometry)multiPoint;case 5: multiLineString = parseMultiLineString(paramValueGetter, paramInt); multiLineString.setSRID(paramInt); return (Geometry)multiLineString;case 6: multiPolygon = parseMultiPolygon(paramValueGetter, paramInt); multiPolygon.setSRID(paramInt); return (Geometry)multiPolygon;case 7: geometryCollection = parseCollection(paramValueGetter, paramInt); geometryCollection.setSRID(paramInt); return (Geometry)geometryCollection;
/*     */     } 
/*     */     throw new IllegalArgumentException("Unknown Geometry Type!"); } private Point parsePoint(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/*     */     Point point;
/* 148 */     double d1 = paramValueGetter.getDouble();
/* 149 */     double d2 = paramValueGetter.getDouble();
/*     */     
/* 151 */     if (paramBoolean1) {
/* 152 */       double d = paramValueGetter.getDouble();
/* 153 */       point = JtsGeometry.geofac.createPoint(new Coordinate(d1, d2, d));
/*     */     } else {
/* 155 */       point = JtsGeometry.geofac.createPoint(new Coordinate(d1, d2));
/*     */     } 
/*     */     
/* 158 */     if (paramBoolean2) {
/* 159 */       paramValueGetter.getDouble();
/*     */     }
/*     */     
/* 162 */     return point;
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseGeometryArray(ValueGetter paramValueGetter, Geometry[] paramArrayOfGeometry, int paramInt) {
/* 167 */     for (byte b = 0; b < paramArrayOfGeometry.length; b++) {
/* 168 */       paramArrayOfGeometry[b] = parseGeometry(paramValueGetter, paramInt, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CoordinateSequence parseCS(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/* 180 */     int i = paramValueGetter.getInt();
/* 181 */     byte b1 = paramBoolean1 ? 3 : 2;
/* 182 */     PackedCoordinateSequence.Double double_ = new PackedCoordinateSequence.Double(i, b1);
/*     */     
/* 184 */     for (byte b2 = 0; b2 < i; b2++) {
/* 185 */       for (byte b = 0; b < b1; b++) {
/* 186 */         double_.setOrdinate(b2, b, paramValueGetter.getDouble());
/*     */       }
/* 188 */       if (paramBoolean2) {
/* 189 */         paramValueGetter.getDouble();
/*     */       }
/*     */     } 
/* 192 */     return (CoordinateSequence)double_;
/*     */   }
/*     */   
/*     */   private MultiPoint parseMultiPoint(ValueGetter paramValueGetter, int paramInt) {
/* 196 */     Point[] arrayOfPoint = new Point[paramValueGetter.getInt()];
/* 197 */     parseGeometryArray(paramValueGetter, (Geometry[])arrayOfPoint, paramInt);
/* 198 */     return JtsGeometry.geofac.createMultiPoint(arrayOfPoint);
/*     */   }
/*     */   
/*     */   private LineString parseLineString(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/* 202 */     return JtsGeometry.geofac.createLineString(parseCS(paramValueGetter, paramBoolean1, paramBoolean2));
/*     */   }
/*     */   
/*     */   private LinearRing parseLinearRing(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/* 206 */     return JtsGeometry.geofac.createLinearRing(parseCS(paramValueGetter, paramBoolean1, paramBoolean2));
/*     */   }
/*     */   
/*     */   private Polygon parsePolygon(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2, int paramInt) {
/* 210 */     int i = paramValueGetter.getInt() - 1;
/* 211 */     LinearRing[] arrayOfLinearRing = new LinearRing[i];
/* 212 */     LinearRing linearRing = parseLinearRing(paramValueGetter, paramBoolean1, paramBoolean2);
/* 213 */     linearRing.setSRID(paramInt);
/* 214 */     for (byte b = 0; b < i; b++) {
/* 215 */       arrayOfLinearRing[b] = parseLinearRing(paramValueGetter, paramBoolean1, paramBoolean2);
/* 216 */       arrayOfLinearRing[b].setSRID(paramInt);
/*     */     } 
/* 218 */     return JtsGeometry.geofac.createPolygon(linearRing, arrayOfLinearRing);
/*     */   }
/*     */   
/*     */   private MultiLineString parseMultiLineString(ValueGetter paramValueGetter, int paramInt) {
/* 222 */     int i = paramValueGetter.getInt();
/* 223 */     LineString[] arrayOfLineString = new LineString[i];
/* 224 */     parseGeometryArray(paramValueGetter, (Geometry[])arrayOfLineString, paramInt);
/* 225 */     return JtsGeometry.geofac.createMultiLineString(arrayOfLineString);
/*     */   }
/*     */   
/*     */   private MultiPolygon parseMultiPolygon(ValueGetter paramValueGetter, int paramInt) {
/* 229 */     int i = paramValueGetter.getInt();
/* 230 */     Polygon[] arrayOfPolygon = new Polygon[i];
/* 231 */     parseGeometryArray(paramValueGetter, (Geometry[])arrayOfPolygon, paramInt);
/* 232 */     return JtsGeometry.geofac.createMultiPolygon(arrayOfPolygon);
/*     */   }
/*     */   
/*     */   private GeometryCollection parseCollection(ValueGetter paramValueGetter, int paramInt) {
/* 236 */     int i = paramValueGetter.getInt();
/* 237 */     Geometry[] arrayOfGeometry = new Geometry[i];
/* 238 */     parseGeometryArray(paramValueGetter, arrayOfGeometry, paramInt);
/* 239 */     return JtsGeometry.geofac.createGeometryCollection(arrayOfGeometry);
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/jts/JtsBinaryParser.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
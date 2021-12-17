/*     */ package org.postgis.binary;
/*     */ 
/*     */ import org.postgis.Geometry;
/*     */ import org.postgis.GeometryCollection;
/*     */ import org.postgis.LineString;
/*     */ import org.postgis.LinearRing;
/*     */ import org.postgis.MultiLineString;
/*     */ import org.postgis.MultiPoint;
/*     */ import org.postgis.MultiPolygon;
/*     */ import org.postgis.Point;
/*     */ import org.postgis.Polygon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BinaryParser
/*     */ {
/*     */   public static ValueGetter valueGetterForEndian(ByteGetter paramByteGetter) {
/*  62 */     if (paramByteGetter.get(0) == 0)
/*  63 */       return new ValueGetter.XDR(paramByteGetter); 
/*  64 */     if (paramByteGetter.get(0) == 1) {
/*  65 */       return new ValueGetter.NDR(paramByteGetter);
/*     */     }
/*  67 */     throw new IllegalArgumentException("Unknown Endian type:" + paramByteGetter.get(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Geometry parse(String paramString) {
/*  78 */     ByteGetter.StringByteGetter stringByteGetter = new ByteGetter.StringByteGetter(paramString);
/*  79 */     return parseGeometry(valueGetterForEndian(stringByteGetter));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Geometry parse(byte[] paramArrayOfbyte) {
/*  89 */     ByteGetter.BinaryByteGetter binaryByteGetter = new ByteGetter.BinaryByteGetter(paramArrayOfbyte);
/*  90 */     return parseGeometry(valueGetterForEndian(binaryByteGetter)); } protected Geometry parseGeometry(ValueGetter paramValueGetter) { Point point; LineString lineString; Polygon polygon;
/*     */     MultiPoint multiPoint;
/*     */     MultiLineString multiLineString;
/*     */     MultiPolygon multiPolygon;
/*     */     GeometryCollection geometryCollection1;
/*  95 */     byte b = paramValueGetter.getByte();
/*  96 */     if (b != paramValueGetter.endian) {
/*  97 */       throw new IllegalArgumentException("Endian inconsistency!");
/*     */     }
/*  99 */     int i = paramValueGetter.getInt();
/*     */     
/* 101 */     int j = i & 0x1FFFFFFF;
/*     */     
/* 103 */     boolean bool1 = ((i & Integer.MIN_VALUE) != 0) ? true : false;
/* 104 */     boolean bool2 = ((i & 0x40000000) != 0) ? true : false;
/* 105 */     boolean bool3 = ((i & 0x20000000) != 0) ? true : false;
/*     */     
/* 107 */     int k = -1;
/*     */     
/* 109 */     if (bool3) {
/* 110 */       k = paramValueGetter.getInt();
/*     */     }
/*     */     
/* 113 */     switch (j) {
/*     */       case 1:
/* 115 */         point = parsePoint(paramValueGetter, bool1, bool2);
/*     */         break;
/*     */       case 2:
/* 118 */         lineString = parseLineString(paramValueGetter, bool1, bool2);
/*     */         break;
/*     */       case 3:
/* 121 */         polygon = parsePolygon(paramValueGetter, bool1, bool2);
/*     */         break;
/*     */       case 4:
/* 124 */         multiPoint = parseMultiPoint(paramValueGetter);
/*     */         break;
/*     */       case 5:
/* 127 */         multiLineString = parseMultiLineString(paramValueGetter);
/*     */         break;
/*     */       case 6:
/* 130 */         multiPolygon = parseMultiPolygon(paramValueGetter);
/*     */         break;
/*     */       case 7:
/* 133 */         geometryCollection1 = parseCollection(paramValueGetter);
/*     */         break;
/*     */       default:
/* 136 */         throw new IllegalArgumentException("Unknown Geometry Type: " + j);
/*     */     } 
/*     */     
/* 139 */     GeometryCollection geometryCollection2 = geometryCollection1;
/*     */     
/* 141 */     if (bool3) {
/* 142 */       geometryCollection2.setSrid(k);
/*     */     }
/* 144 */     return (Geometry)geometryCollection2; }
/*     */   
/*     */   private Point parsePoint(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/*     */     Point point;
/* 148 */     double d1 = paramValueGetter.getDouble();
/* 149 */     double d2 = paramValueGetter.getDouble();
/*     */     
/* 151 */     if (paramBoolean1) {
/* 152 */       double d = paramValueGetter.getDouble();
/* 153 */       point = new Point(d1, d2, d);
/*     */     } else {
/* 155 */       point = new Point(d1, d2);
/*     */     } 
/*     */     
/* 158 */     if (paramBoolean2) {
/* 159 */       point.setM(paramValueGetter.getDouble());
/*     */     }
/*     */     
/* 162 */     return point;
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseGeometryArray(ValueGetter paramValueGetter, Geometry[] paramArrayOfGeometry) {
/* 167 */     for (byte b = 0; b < paramArrayOfGeometry.length; b++) {
/* 168 */       paramArrayOfGeometry[b] = parseGeometry(paramValueGetter);
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
/*     */   private Point[] parsePointArray(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/* 180 */     int i = paramValueGetter.getInt();
/* 181 */     Point[] arrayOfPoint = new Point[i];
/* 182 */     for (byte b = 0; b < i; b++) {
/* 183 */       arrayOfPoint[b] = parsePoint(paramValueGetter, paramBoolean1, paramBoolean2);
/*     */     }
/* 185 */     return arrayOfPoint;
/*     */   }
/*     */   
/*     */   private MultiPoint parseMultiPoint(ValueGetter paramValueGetter) {
/* 189 */     Point[] arrayOfPoint = new Point[paramValueGetter.getInt()];
/* 190 */     parseGeometryArray(paramValueGetter, (Geometry[])arrayOfPoint);
/* 191 */     return new MultiPoint(arrayOfPoint);
/*     */   }
/*     */   
/*     */   private LineString parseLineString(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/* 195 */     Point[] arrayOfPoint = parsePointArray(paramValueGetter, paramBoolean1, paramBoolean2);
/* 196 */     return new LineString(arrayOfPoint);
/*     */   }
/*     */   
/*     */   private LinearRing parseLinearRing(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/* 200 */     Point[] arrayOfPoint = parsePointArray(paramValueGetter, paramBoolean1, paramBoolean2);
/* 201 */     return new LinearRing(arrayOfPoint);
/*     */   }
/*     */   
/*     */   private Polygon parsePolygon(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/* 205 */     int i = paramValueGetter.getInt();
/* 206 */     LinearRing[] arrayOfLinearRing = new LinearRing[i];
/* 207 */     for (byte b = 0; b < i; b++) {
/* 208 */       arrayOfLinearRing[b] = parseLinearRing(paramValueGetter, paramBoolean1, paramBoolean2);
/*     */     }
/* 210 */     return new Polygon(arrayOfLinearRing);
/*     */   }
/*     */   
/*     */   private MultiLineString parseMultiLineString(ValueGetter paramValueGetter) {
/* 214 */     int i = paramValueGetter.getInt();
/* 215 */     LineString[] arrayOfLineString = new LineString[i];
/* 216 */     parseGeometryArray(paramValueGetter, (Geometry[])arrayOfLineString);
/* 217 */     return new MultiLineString(arrayOfLineString);
/*     */   }
/*     */   
/*     */   private MultiPolygon parseMultiPolygon(ValueGetter paramValueGetter) {
/* 221 */     int i = paramValueGetter.getInt();
/* 222 */     Polygon[] arrayOfPolygon = new Polygon[i];
/* 223 */     parseGeometryArray(paramValueGetter, (Geometry[])arrayOfPolygon);
/* 224 */     return new MultiPolygon(arrayOfPolygon);
/*     */   }
/*     */   
/*     */   private GeometryCollection parseCollection(ValueGetter paramValueGetter) {
/* 228 */     int i = paramValueGetter.getInt();
/* 229 */     Geometry[] arrayOfGeometry = new Geometry[i];
/* 230 */     parseGeometryArray(paramValueGetter, arrayOfGeometry);
/* 231 */     return new GeometryCollection(arrayOfGeometry);
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/binary/BinaryParser.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
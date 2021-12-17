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
/*     */ public class BinaryWriter
/*     */ {
/*     */   public static ValueSetter valueSetterForEndian(ByteSetter paramByteSetter, byte paramByte) {
/*  61 */     if (paramByte == 0)
/*  62 */       return new ValueSetter.XDR(paramByteSetter); 
/*  63 */     if (paramByte == 1) {
/*  64 */       return new ValueSetter.NDR(paramByteSetter);
/*     */     }
/*  66 */     throw new IllegalArgumentException("Unknown Endian type:" + paramByte);
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
/*     */   public synchronized String writeHexed(Geometry paramGeometry, byte paramByte) {
/*  83 */     int i = estimateBytes(paramGeometry);
/*  84 */     ByteSetter.StringByteSetter stringByteSetter = new ByteSetter.StringByteSetter(i);
/*  85 */     writeGeometry(paramGeometry, valueSetterForEndian(stringByteSetter, paramByte));
/*  86 */     return stringByteSetter.result();
/*     */   }
/*     */   
/*     */   public synchronized String writeHexed(Geometry paramGeometry) {
/*  90 */     return writeHexed(paramGeometry, (byte)1);
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
/*     */   public synchronized byte[] writeBinary(Geometry paramGeometry, byte paramByte) {
/* 106 */     int i = estimateBytes(paramGeometry);
/* 107 */     ByteSetter.BinaryByteSetter binaryByteSetter = new ByteSetter.BinaryByteSetter(i);
/* 108 */     writeGeometry(paramGeometry, valueSetterForEndian(binaryByteSetter, paramByte));
/* 109 */     return binaryByteSetter.result();
/*     */   }
/*     */   
/*     */   public synchronized byte[] writeBinary(Geometry paramGeometry) {
/* 113 */     return writeBinary(paramGeometry, (byte)1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeGeometry(Geometry paramGeometry, ValueSetter paramValueSetter) {
/* 119 */     paramValueSetter.setByte(paramValueSetter.endian);
/*     */ 
/*     */     
/* 122 */     int i = paramGeometry.type;
/* 123 */     if (paramGeometry.dimension == 3) {
/* 124 */       i |= Integer.MIN_VALUE;
/*     */     }
/* 126 */     if (paramGeometry.haveMeasure) {
/* 127 */       i |= 0x40000000;
/*     */     }
/* 129 */     if (paramGeometry.srid != -1) {
/* 130 */       i |= 0x20000000;
/*     */     }
/*     */     
/* 133 */     paramValueSetter.setInt(i);
/*     */     
/* 135 */     if (paramGeometry.srid != -1) {
/* 136 */       paramValueSetter.setInt(paramGeometry.srid);
/*     */     }
/*     */     
/* 139 */     switch (paramGeometry.type) {
/*     */       case 1:
/* 141 */         writePoint((Point)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 2:
/* 144 */         writeLineString((LineString)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 3:
/* 147 */         writePolygon((Polygon)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 4:
/* 150 */         writeMultiPoint((MultiPoint)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 5:
/* 153 */         writeMultiLineString((MultiLineString)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 6:
/* 156 */         writeMultiPolygon((MultiPolygon)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 7:
/* 159 */         writeCollection((GeometryCollection)paramGeometry, paramValueSetter);
/*     */         return;
/*     */     } 
/* 162 */     throw new IllegalArgumentException("Unknown Geometry Type: " + paramGeometry.type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writePoint(Point paramPoint, ValueSetter paramValueSetter) {
/* 171 */     paramValueSetter.setDouble(paramPoint.x);
/* 172 */     paramValueSetter.setDouble(paramPoint.y);
/*     */     
/* 174 */     if (paramPoint.dimension == 3) {
/* 175 */       paramValueSetter.setDouble(paramPoint.z);
/*     */     }
/*     */     
/* 178 */     if (paramPoint.haveMeasure) {
/* 179 */       paramValueSetter.setDouble(paramPoint.m);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeGeometryArray(Geometry[] paramArrayOfGeometry, ValueSetter paramValueSetter) {
/* 185 */     for (byte b = 0; b < paramArrayOfGeometry.length; b++) {
/* 186 */       writeGeometry(paramArrayOfGeometry[b], paramValueSetter);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writePointArray(Point[] paramArrayOfPoint, ValueSetter paramValueSetter) {
/* 196 */     paramValueSetter.setInt(paramArrayOfPoint.length);
/* 197 */     for (byte b = 0; b < paramArrayOfPoint.length; b++) {
/* 198 */       writePoint(paramArrayOfPoint[b], paramValueSetter);
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeMultiPoint(MultiPoint paramMultiPoint, ValueSetter paramValueSetter) {
/* 203 */     paramValueSetter.setInt(paramMultiPoint.numPoints());
/* 204 */     writeGeometryArray((Geometry[])paramMultiPoint.getPoints(), paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writeLineString(LineString paramLineString, ValueSetter paramValueSetter) {
/* 208 */     writePointArray(paramLineString.getPoints(), paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writeLinearRing(LinearRing paramLinearRing, ValueSetter paramValueSetter) {
/* 212 */     writePointArray(paramLinearRing.getPoints(), paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writePolygon(Polygon paramPolygon, ValueSetter paramValueSetter) {
/* 216 */     paramValueSetter.setInt(paramPolygon.numRings());
/* 217 */     for (byte b = 0; b < paramPolygon.numRings(); b++) {
/* 218 */       writeLinearRing(paramPolygon.getRing(b), paramValueSetter);
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeMultiLineString(MultiLineString paramMultiLineString, ValueSetter paramValueSetter) {
/* 223 */     paramValueSetter.setInt(paramMultiLineString.numLines());
/* 224 */     writeGeometryArray((Geometry[])paramMultiLineString.getLines(), paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writeMultiPolygon(MultiPolygon paramMultiPolygon, ValueSetter paramValueSetter) {
/* 228 */     paramValueSetter.setInt(paramMultiPolygon.numPolygons());
/* 229 */     writeGeometryArray((Geometry[])paramMultiPolygon.getPolygons(), paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writeCollection(GeometryCollection paramGeometryCollection, ValueSetter paramValueSetter) {
/* 233 */     paramValueSetter.setInt(paramGeometryCollection.numGeoms());
/* 234 */     writeGeometryArray(paramGeometryCollection.getGeometries(), paramValueSetter);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int estimateBytes(Geometry paramGeometry) {
/* 239 */     int i = 0;
/*     */ 
/*     */     
/* 242 */     i++;
/*     */ 
/*     */     
/* 245 */     i += 4;
/*     */     
/* 247 */     if (paramGeometry.srid != -1) {
/* 248 */       i += 4;
/*     */     }
/*     */     
/* 251 */     switch (paramGeometry.type) {
/*     */       case 1:
/* 253 */         i += estimatePoint((Point)paramGeometry);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 276 */         return i;case 2: i += estimateLineString((LineString)paramGeometry); return i;case 3: i += estimatePolygon((Polygon)paramGeometry); return i;case 4: i += estimateMultiPoint((MultiPoint)paramGeometry); return i;case 5: i += estimateMultiLineString((MultiLineString)paramGeometry); return i;case 6: i += estimateMultiPolygon((MultiPolygon)paramGeometry); return i;case 7: i += estimateCollection((GeometryCollection)paramGeometry); return i;
/*     */     } 
/*     */     throw new IllegalArgumentException("Unknown Geometry Type: " + paramGeometry.type);
/*     */   }
/*     */   private int estimatePoint(Point paramPoint) {
/* 281 */     byte b = 16;
/* 282 */     if (paramPoint.dimension == 3) {
/* 283 */       b += 8;
/*     */     }
/*     */     
/* 286 */     if (paramPoint.haveMeasure) {
/* 287 */       b += 8;
/*     */     }
/* 289 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateGeometryArray(Geometry[] paramArrayOfGeometry) {
/* 294 */     int i = 0;
/* 295 */     for (byte b = 0; b < paramArrayOfGeometry.length; b++) {
/* 296 */       i += estimateBytes(paramArrayOfGeometry[b]);
/*     */     }
/* 298 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int estimatePointArray(Point[] paramArrayOfPoint) {
/* 307 */     int i = 4;
/*     */ 
/*     */ 
/*     */     
/* 311 */     if (paramArrayOfPoint.length > 0) {
/* 312 */       i += paramArrayOfPoint.length * estimatePoint(paramArrayOfPoint[0]);
/*     */     }
/* 314 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateMultiPoint(MultiPoint paramMultiPoint) {
/* 319 */     int i = 4;
/* 320 */     if (paramMultiPoint.numPoints() > 0)
/*     */     {
/* 322 */       i += paramMultiPoint.numPoints() * estimateBytes((Geometry)paramMultiPoint.getFirstPoint());
/*     */     }
/* 324 */     return i;
/*     */   }
/*     */   
/*     */   private int estimateLineString(LineString paramLineString) {
/* 328 */     return estimatePointArray(paramLineString.getPoints());
/*     */   }
/*     */   
/*     */   private int estimateLinearRing(LinearRing paramLinearRing) {
/* 332 */     return estimatePointArray(paramLinearRing.getPoints());
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimatePolygon(Polygon paramPolygon) {
/* 337 */     int i = 4;
/* 338 */     for (byte b = 0; b < paramPolygon.numRings(); b++) {
/* 339 */       i += estimateLinearRing(paramPolygon.getRing(b));
/*     */     }
/* 341 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateMultiLineString(MultiLineString paramMultiLineString) {
/* 346 */     return 4 + estimateGeometryArray((Geometry[])paramMultiLineString.getLines());
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateMultiPolygon(MultiPolygon paramMultiPolygon) {
/* 351 */     return 4 + estimateGeometryArray((Geometry[])paramMultiPolygon.getPolygons());
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateCollection(GeometryCollection paramGeometryCollection) {
/* 356 */     return 4 + estimateGeometryArray(paramGeometryCollection.getGeometries());
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/binary/BinaryWriter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
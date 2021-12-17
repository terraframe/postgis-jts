/*     */ package org.postgis.jts;
/*     */ 
/*     */ import com.vividsolutions.jts.geom.CoordinateSequence;
/*     */ import com.vividsolutions.jts.geom.Geometry;
/*     */ import com.vividsolutions.jts.geom.GeometryCollection;
/*     */ import com.vividsolutions.jts.geom.LineString;
/*     */ import com.vividsolutions.jts.geom.MultiLineString;
/*     */ import com.vividsolutions.jts.geom.MultiPoint;
/*     */ import com.vividsolutions.jts.geom.MultiPolygon;
/*     */ import com.vividsolutions.jts.geom.Point;
/*     */ import com.vividsolutions.jts.geom.Polygon;
/*     */ import org.postgis.binary.ByteSetter;
/*     */ import org.postgis.binary.ValueSetter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JtsBinaryWriter
/*     */ {
/*     */   public static ValueSetter valueSetterForEndian(ByteSetter paramByteSetter, byte paramByte) {
/*  65 */     if (paramByte == 0)
/*  66 */       return (ValueSetter)new ValueSetter.XDR(paramByteSetter); 
/*  67 */     if (paramByte == 1) {
/*  68 */       return (ValueSetter)new ValueSetter.NDR(paramByteSetter);
/*     */     }
/*  70 */     throw new IllegalArgumentException("Unknown Endian type:" + paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String writeHexed(Geometry paramGeometry, byte paramByte) {
/*  81 */     int i = estimateBytes(paramGeometry);
/*  82 */     ByteSetter.StringByteSetter stringByteSetter = new ByteSetter.StringByteSetter(i);
/*  83 */     writeGeometry(paramGeometry, valueSetterForEndian((ByteSetter)stringByteSetter, paramByte));
/*  84 */     return stringByteSetter.result();
/*     */   }
/*     */   
/*     */   public String writeHexed(Geometry paramGeometry) {
/*  88 */     return writeHexed(paramGeometry, (byte)1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] writeBinary(Geometry paramGeometry, byte paramByte) {
/*  98 */     int i = estimateBytes(paramGeometry);
/*  99 */     ByteSetter.BinaryByteSetter binaryByteSetter = new ByteSetter.BinaryByteSetter(i);
/* 100 */     writeGeometry(paramGeometry, valueSetterForEndian((ByteSetter)binaryByteSetter, paramByte));
/* 101 */     return binaryByteSetter.result();
/*     */   }
/*     */   
/*     */   public byte[] writeBinary(Geometry paramGeometry) {
/* 105 */     return writeBinary(paramGeometry, (byte)1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeGeometry(Geometry paramGeometry, ValueSetter paramValueSetter) {
/*     */     int i;
/* 111 */     if (paramGeometry == null)
/* 112 */       throw new NullPointerException(); 
/* 113 */     if (paramGeometry.isEmpty()) {
/*     */       
/* 115 */       i = 0;
/*     */     } else {
/* 117 */       i = getCoordDim(paramGeometry);
/* 118 */       if (i < 2 || i > 4) {
/* 119 */         throw new IllegalArgumentException("Unsupported geometry dimensionality: " + i);
/*     */       }
/*     */     } 
/*     */     
/* 123 */     paramValueSetter.setByte(paramValueSetter.endian);
/*     */ 
/*     */     
/* 126 */     int j = getWKBType(paramGeometry);
/* 127 */     int k = j;
/* 128 */     if (i == 3 || i == 4) {
/* 129 */       k |= Integer.MIN_VALUE;
/*     */     }
/* 131 */     if (i == 4) {
/* 132 */       k |= 0x40000000;
/*     */     }
/*     */     
/* 135 */     boolean bool = checkSrid(paramGeometry);
/* 136 */     if (bool) {
/* 137 */       k |= 0x20000000;
/*     */     }
/*     */     
/* 140 */     paramValueSetter.setInt(k);
/*     */     
/* 142 */     if (bool) {
/* 143 */       paramValueSetter.setInt(paramGeometry.getSRID());
/*     */     }
/*     */     
/* 146 */     switch (j) {
/*     */       case 1:
/* 148 */         writePoint((Point)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 2:
/* 151 */         writeLineString((LineString)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 3:
/* 154 */         writePolygon((Polygon)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 4:
/* 157 */         writeMultiPoint((MultiPoint)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 5:
/* 160 */         writeMultiLineString((MultiLineString)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 6:
/* 163 */         writeMultiPolygon((MultiPolygon)paramGeometry, paramValueSetter);
/*     */         return;
/*     */       case 7:
/* 166 */         writeCollection((GeometryCollection)paramGeometry, paramValueSetter);
/*     */         return;
/*     */     } 
/* 169 */     throw new IllegalArgumentException("Unknown Geometry Type: " + j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getWKBType(Geometry paramGeometry) {
/* 176 */     if (paramGeometry.isEmpty())
/* 177 */       return 7; 
/* 178 */     if (paramGeometry instanceof Point)
/* 179 */       return 1; 
/* 180 */     if (paramGeometry instanceof LineString)
/* 181 */       return 2; 
/* 182 */     if (paramGeometry instanceof Polygon)
/* 183 */       return 3; 
/* 184 */     if (paramGeometry instanceof MultiPoint)
/* 185 */       return 4; 
/* 186 */     if (paramGeometry instanceof MultiLineString)
/* 187 */       return 5; 
/* 188 */     if (paramGeometry instanceof MultiPolygon)
/* 189 */       return 6; 
/* 190 */     if (paramGeometry instanceof GeometryCollection) {
/* 191 */       return 7;
/*     */     }
/* 193 */     throw new IllegalArgumentException("Unknown Geometry Type: " + paramGeometry.getClass().getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writePoint(Point paramPoint, ValueSetter paramValueSetter) {
/* 202 */     writeCoordinates(paramPoint.getCoordinateSequence(), getCoordDim((Geometry)paramPoint), paramValueSetter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeCoordinates(CoordinateSequence paramCoordinateSequence, int paramInt, ValueSetter paramValueSetter) {
/* 210 */     for (byte b = 0; b < paramCoordinateSequence.size(); b++) {
/* 211 */       for (byte b1 = 0; b1 < paramInt; b1++) {
/* 212 */         paramValueSetter.setDouble(paramCoordinateSequence.getOrdinate(b, b1));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeMultiPoint(MultiPoint paramMultiPoint, ValueSetter paramValueSetter) {
/* 218 */     paramValueSetter.setInt(paramMultiPoint.getNumPoints());
/* 219 */     for (byte b = 0; b < paramMultiPoint.getNumPoints(); b++) {
/* 220 */       writeGeometry(paramMultiPoint.getGeometryN(b), paramValueSetter);
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeLineString(LineString paramLineString, ValueSetter paramValueSetter) {
/* 225 */     paramValueSetter.setInt(paramLineString.getNumPoints());
/* 226 */     writeCoordinates(paramLineString.getCoordinateSequence(), getCoordDim((Geometry)paramLineString), paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writePolygon(Polygon paramPolygon, ValueSetter paramValueSetter) {
/* 230 */     paramValueSetter.setInt(paramPolygon.getNumInteriorRing() + 1);
/* 231 */     writeLineString(paramPolygon.getExteriorRing(), paramValueSetter);
/* 232 */     for (byte b = 0; b < paramPolygon.getNumInteriorRing(); b++) {
/* 233 */       writeLineString(paramPolygon.getInteriorRingN(b), paramValueSetter);
/*     */     }
/*     */   }
/*     */   
/*     */   private void writeMultiLineString(MultiLineString paramMultiLineString, ValueSetter paramValueSetter) {
/* 238 */     writeGeometryArray((Geometry)paramMultiLineString, paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writeMultiPolygon(MultiPolygon paramMultiPolygon, ValueSetter paramValueSetter) {
/* 242 */     writeGeometryArray((Geometry)paramMultiPolygon, paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writeCollection(GeometryCollection paramGeometryCollection, ValueSetter paramValueSetter) {
/* 246 */     writeGeometryArray((Geometry)paramGeometryCollection, paramValueSetter);
/*     */   }
/*     */   
/*     */   private void writeGeometryArray(Geometry paramGeometry, ValueSetter paramValueSetter) {
/* 250 */     paramValueSetter.setInt(paramGeometry.getNumGeometries());
/* 251 */     for (byte b = 0; b < paramGeometry.getNumGeometries(); b++) {
/* 252 */       writeGeometry(paramGeometry.getGeometryN(b), paramValueSetter);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int estimateBytes(Geometry paramGeometry) {
/* 258 */     int i = 0;
/*     */ 
/*     */     
/* 261 */     i++;
/*     */ 
/*     */     
/* 264 */     i += 4;
/*     */     
/* 266 */     if (checkSrid(paramGeometry)) {
/* 267 */       i += 4;
/*     */     }
/*     */     
/* 270 */     switch (getWKBType(paramGeometry)) {
/*     */       case 1:
/* 272 */         i += estimatePoint((Point)paramGeometry);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 295 */         return i;case 2: i += estimateLineString((LineString)paramGeometry); return i;case 3: i += estimatePolygon((Polygon)paramGeometry); return i;case 4: i += estimateMultiPoint((MultiPoint)paramGeometry); return i;case 5: i += estimateMultiLineString((MultiLineString)paramGeometry); return i;case 6: i += estimateMultiPolygon((MultiPolygon)paramGeometry); return i;case 7: i += estimateCollection((GeometryCollection)paramGeometry); return i;
/*     */     } 
/*     */     throw new IllegalArgumentException("Unknown Geometry Type: " + getWKBType(paramGeometry));
/*     */   } private boolean checkSrid(Geometry paramGeometry) {
/* 299 */     int i = paramGeometry.getSRID();
/*     */     
/* 301 */     return (i != -1 && i != 0);
/*     */   }
/*     */   
/*     */   private int estimatePoint(Point paramPoint) {
/* 305 */     return 8 * getCoordDim((Geometry)paramPoint);
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateGeometryArray(Geometry paramGeometry) {
/* 310 */     int i = 0;
/* 311 */     for (byte b = 0; b < paramGeometry.getNumGeometries(); b++) {
/* 312 */       i += estimateBytes(paramGeometry.getGeometryN(b));
/*     */     }
/* 314 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int estimateMultiPoint(MultiPoint paramMultiPoint) {
/* 320 */     int i = 4;
/* 321 */     if (paramMultiPoint.getNumGeometries() > 0)
/*     */     {
/*     */       
/* 324 */       i += paramMultiPoint.getNumGeometries() * estimateBytes(paramMultiPoint.getGeometryN(0));
/*     */     }
/* 326 */     return i;
/*     */   }
/*     */   
/*     */   private int estimateLineString(LineString paramLineString) {
/* 330 */     if (paramLineString == null || paramLineString.getNumGeometries() == 0) {
/* 331 */       return 0;
/*     */     }
/* 333 */     return 4 + 8 * getCoordSequenceDim(paramLineString.getCoordinateSequence()) * paramLineString.getCoordinateSequence().size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int estimatePolygon(Polygon paramPolygon) {
/* 339 */     int i = 4;
/* 340 */     i += estimateLineString(paramPolygon.getExteriorRing());
/* 341 */     for (byte b = 0; b < paramPolygon.getNumInteriorRing(); b++) {
/* 342 */       i += estimateLineString(paramPolygon.getInteriorRingN(b));
/*     */     }
/* 344 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateMultiLineString(MultiLineString paramMultiLineString) {
/* 349 */     return 4 + estimateGeometryArray((Geometry)paramMultiLineString);
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateMultiPolygon(MultiPolygon paramMultiPolygon) {
/* 354 */     return 4 + estimateGeometryArray((Geometry)paramMultiPolygon);
/*     */   }
/*     */ 
/*     */   
/*     */   private int estimateCollection(GeometryCollection paramGeometryCollection) {
/* 359 */     return 4 + estimateGeometryArray((Geometry)paramGeometryCollection);
/*     */   }
/*     */   
/*     */   public static final int getCoordDim(Geometry paramGeometry) {
/* 363 */     if (paramGeometry.isEmpty()) {
/* 364 */       return 0;
/*     */     }
/* 366 */     if (paramGeometry instanceof Point)
/* 367 */       return getCoordSequenceDim(((Point)paramGeometry).getCoordinateSequence()); 
/* 368 */     if (paramGeometry instanceof LineString)
/* 369 */       return getCoordSequenceDim(((LineString)paramGeometry).getCoordinateSequence()); 
/* 370 */     if (paramGeometry instanceof Polygon) {
/* 371 */       return getCoordSequenceDim(((Polygon)paramGeometry).getExteriorRing().getCoordinateSequence());
/*     */     }
/* 373 */     return getCoordDim(paramGeometry.getGeometryN(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int getCoordSequenceDim(CoordinateSequence paramCoordinateSequence) {
/* 378 */     if (paramCoordinateSequence == null || paramCoordinateSequence.size() == 0) {
/* 379 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 383 */     int i = paramCoordinateSequence.getDimension();
/* 384 */     if (i == 3)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 389 */       return Double.isNaN(paramCoordinateSequence.getOrdinate(0, 2)) ? 2 : 3;
/*     */     }
/* 391 */     return i;
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/jts/JtsBinaryWriter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
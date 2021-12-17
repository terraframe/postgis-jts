/*     */ package org.postgis.java2d;
/*     */ 
/*     */ import java.awt.geom.GeneralPath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShapeBinaryParser
/*     */ {
/*     */   public static ValueGetter valueGetterForEndian(ByteGetter paramByteGetter) {
/*  58 */     if (paramByteGetter.get(0) == 0)
/*  59 */       return (ValueGetter)new ValueGetter.XDR(paramByteGetter); 
/*  60 */     if (paramByteGetter.get(0) == 1) {
/*  61 */       return (ValueGetter)new ValueGetter.NDR(paramByteGetter);
/*     */     }
/*  63 */     throw new IllegalArgumentException("Unknown Endian type:" + paramByteGetter.get(0));
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
/*     */   public synchronized int parse(String paramString, GeneralPath paramGeneralPath) {
/*  76 */     ByteGetter.StringByteGetter stringByteGetter = new ByteGetter.StringByteGetter(paramString);
/*  77 */     return parseGeometry(valueGetterForEndian((ByteGetter)stringByteGetter), paramGeneralPath);
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
/*     */   public synchronized int parse(byte[] paramArrayOfbyte, GeneralPath paramGeneralPath) {
/*  89 */     ByteGetter.BinaryByteGetter binaryByteGetter = new ByteGetter.BinaryByteGetter(paramArrayOfbyte);
/*  90 */     return parseGeometry(valueGetterForEndian((ByteGetter)binaryByteGetter), paramGeneralPath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int parseGeometry(ValueGetter paramValueGetter, GeneralPath paramGeneralPath) {
/*  99 */     byte b = paramValueGetter.getByte();
/* 100 */     if (b != paramValueGetter.endian) {
/* 101 */       throw new IllegalArgumentException("Endian inconsistency!");
/*     */     }
/* 103 */     int i = paramValueGetter.getInt();
/*     */     
/* 105 */     int j = i & 0x1FFFFFFF;
/*     */     
/* 107 */     boolean bool1 = ((i & Integer.MIN_VALUE) != 0) ? true : false;
/* 108 */     boolean bool2 = ((i & 0x40000000) != 0) ? true : false;
/* 109 */     boolean bool3 = ((i & 0x20000000) != 0) ? true : false;
/*     */     
/* 111 */     int k = -1;
/*     */     
/* 113 */     if (bool3) {
/* 114 */       k = paramValueGetter.getInt();
/*     */     }
/*     */     
/* 117 */     switch (j) {
/*     */       case 1:
/* 119 */         parsePoint(paramValueGetter, bool1, bool2, paramGeneralPath);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 142 */         return k;case 2: parseLineString(paramValueGetter, bool1, bool2, paramGeneralPath); return k;case 3: parsePolygon(paramValueGetter, bool1, bool2, paramGeneralPath); return k;case 4: parseMultiPoint(paramValueGetter, paramGeneralPath); return k;case 5: parseMultiLineString(paramValueGetter, paramGeneralPath); return k;case 6: parseMultiPolygon(paramValueGetter, paramGeneralPath); return k;case 7: parseCollection(paramValueGetter, paramGeneralPath); return k;
/*     */     } 
/*     */     throw new IllegalArgumentException("Unknown Geometry Type!");
/*     */   } private void parsePoint(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2, GeneralPath paramGeneralPath) {
/* 146 */     paramGeneralPath.moveTo((float)paramValueGetter.getDouble(), (float)paramValueGetter.getDouble());
/* 147 */     paramGeneralPath.closePath();
/* 148 */     skipZM(paramValueGetter, paramBoolean1, paramBoolean2);
/*     */   }
/*     */   
/*     */   private void skipZM(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2) {
/* 152 */     if (paramBoolean1) {
/* 153 */       paramValueGetter.getDouble();
/*     */     }
/* 155 */     if (paramBoolean2) {
/* 156 */       paramValueGetter.getDouble();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseGeometryArray(ValueGetter paramValueGetter, int paramInt, GeneralPath paramGeneralPath) {
/* 162 */     for (byte b = 0; b < paramInt; b++) {
/* 163 */       parseGeometry(paramValueGetter, paramGeneralPath);
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
/*     */   private void parseCS(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2, GeneralPath paramGeneralPath) {
/* 175 */     int i = paramValueGetter.getInt();
/* 176 */     if (i > 0) {
/* 177 */       paramGeneralPath.moveTo((float)paramValueGetter.getDouble(), (float)paramValueGetter.getDouble());
/* 178 */       skipZM(paramValueGetter, paramBoolean1, paramBoolean2);
/* 179 */       for (byte b = 1; b < i; b++) {
/* 180 */         paramGeneralPath.lineTo((float)paramValueGetter.getDouble(), (float)paramValueGetter.getDouble());
/* 181 */         skipZM(paramValueGetter, paramBoolean1, paramBoolean2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void parseMultiPoint(ValueGetter paramValueGetter, GeneralPath paramGeneralPath) {
/* 188 */     parseGeometryArray(paramValueGetter, paramValueGetter.getInt(), paramGeneralPath);
/*     */   }
/*     */   
/*     */   private void parseLineString(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2, GeneralPath paramGeneralPath) {
/* 192 */     parseCS(paramValueGetter, paramBoolean1, paramBoolean2, paramGeneralPath);
/*     */   }
/*     */   
/*     */   private void parseLinearRing(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2, GeneralPath paramGeneralPath) {
/* 196 */     parseCS(paramValueGetter, paramBoolean1, paramBoolean2, paramGeneralPath);
/* 197 */     paramGeneralPath.closePath();
/*     */   }
/*     */   
/*     */   private void parsePolygon(ValueGetter paramValueGetter, boolean paramBoolean1, boolean paramBoolean2, GeneralPath paramGeneralPath) {
/* 201 */     int i = paramValueGetter.getInt() - 1;
/*     */     
/* 203 */     parseLinearRing(paramValueGetter, paramBoolean1, paramBoolean2, paramGeneralPath);
/*     */     
/* 205 */     for (byte b = 0; b < i; b++) {
/* 206 */       parseLinearRing(paramValueGetter, paramBoolean1, paramBoolean2, paramGeneralPath);
/*     */     }
/*     */   }
/*     */   
/*     */   private void parseMultiLineString(ValueGetter paramValueGetter, GeneralPath paramGeneralPath) {
/* 211 */     int i = paramValueGetter.getInt();
/* 212 */     parseGeometryArray(paramValueGetter, i, paramGeneralPath);
/*     */   }
/*     */   
/*     */   private void parseMultiPolygon(ValueGetter paramValueGetter, GeneralPath paramGeneralPath) {
/* 216 */     int i = paramValueGetter.getInt();
/* 217 */     parseGeometryArray(paramValueGetter, i, paramGeneralPath);
/*     */   }
/*     */   
/*     */   private void parseCollection(ValueGetter paramValueGetter, GeneralPath paramGeneralPath) {
/* 221 */     int i = paramValueGetter.getInt();
/* 222 */     parseGeometryArray(paramValueGetter, i, paramGeneralPath);
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/java2d/ShapeBinaryParser.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
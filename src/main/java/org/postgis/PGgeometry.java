/*     */ package org.postgis;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import org.postgis.binary.BinaryParser;
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
/*     */ public class PGgeometry
/*     */   extends PGobject
/*     */ {
/*     */   private static final long serialVersionUID = 256L;
/*     */   Geometry geom;
/*  39 */   BinaryParser bp = new BinaryParser();
/*     */   
/*     */   public PGgeometry() {
/*  42 */     setType("geometry");
/*     */   }
/*     */   public static final String SRIDPREFIX = "SRID=";
/*     */   public PGgeometry(Geometry paramGeometry) {
/*  46 */     this();
/*  47 */     this.geom = paramGeometry;
/*     */   }
/*     */   
/*     */   public PGgeometry(String paramString) throws SQLException {
/*  51 */     this();
/*  52 */     setValue(paramString);
/*     */   }
/*     */   
/*     */   public void setValue(String paramString) throws SQLException {
/*  56 */     this.geom = geomFromString(paramString, this.bp);
/*     */   }
/*     */   
/*     */   public static Geometry geomFromString(String paramString) throws SQLException {
/*  60 */     return geomFromString(paramString, false);
/*     */   }
/*     */   
/*     */   public static Geometry geomFromString(String paramString, boolean paramBoolean) throws SQLException {
/*  64 */     BinaryParser binaryParser = new BinaryParser();
/*     */     
/*  66 */     return geomFromString(paramString, binaryParser, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Geometry geomFromString(String paramString, BinaryParser paramBinaryParser) throws SQLException {
/*  73 */     return geomFromString(paramString, paramBinaryParser, false);
/*     */   }
/*     */   
/*     */   public static Geometry geomFromString(String paramString, BinaryParser paramBinaryParser, boolean paramBoolean) throws SQLException {
/*     */     Geometry geometry;
/*  78 */     paramString = paramString.trim();
/*     */     
/*  80 */     int i = -1;
/*     */     
/*  82 */     if (paramString.startsWith("SRID=")) {
/*     */       
/*  84 */       String[] arrayOfString = splitSRID(paramString);
/*  85 */       paramString = arrayOfString[1].trim();
/*  86 */       i = Integer.parseInt(arrayOfString[0].substring(5));
/*     */     } 
/*     */ 
/*     */     
/*  90 */     if (paramString.startsWith("00") || paramString.startsWith("01")) {
/*  91 */       geometry = paramBinaryParser.parse(paramString);
/*  92 */     } else if (paramString.endsWith("EMPTY")) {
/*     */ 
/*     */       
/*  95 */       geometry = new GeometryCollection();
/*  96 */     } else if (paramString.startsWith("MULTIPOLYGON")) {
/*  97 */       geometry = new MultiPolygon(paramString, paramBoolean);
/*  98 */     } else if (paramString.startsWith("MULTILINESTRING")) {
/*  99 */       geometry = new MultiLineString(paramString, paramBoolean);
/* 100 */     } else if (paramString.startsWith("MULTIPOINT")) {
/* 101 */       geometry = new MultiPoint(paramString, paramBoolean);
/* 102 */     } else if (paramString.startsWith("POLYGON")) {
/* 103 */       geometry = new Polygon(paramString, paramBoolean);
/* 104 */     } else if (paramString.startsWith("LINESTRING")) {
/* 105 */       geometry = new LineString(paramString, paramBoolean);
/* 106 */     } else if (paramString.startsWith("POINT")) {
/* 107 */       geometry = new Point(paramString, paramBoolean);
/* 108 */     } else if (paramString.startsWith("GEOMETRYCOLLECTION")) {
/* 109 */       geometry = new GeometryCollection(paramString, paramBoolean);
/*     */     } else {
/* 111 */       throw new SQLException("Unknown type: " + paramString);
/*     */     } 
/*     */     
/* 114 */     if (i != -1) {
/* 115 */       geometry.srid = i;
/*     */     }
/*     */     
/* 118 */     return geometry;
/*     */   }
/*     */   
/*     */   public Geometry getGeometry() {
/* 122 */     return this.geom;
/*     */   }
/*     */   
/*     */   public void setGeometry(Geometry paramGeometry) {
/* 126 */     this.geom = paramGeometry;
/*     */   }
/*     */   
/*     */   public int getGeoType() {
/* 130 */     return this.geom.type;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 134 */     return this.geom.toString();
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 138 */     return this.geom.toString();
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 142 */     return new PGgeometry(this.geom);
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
/*     */   public static String[] splitSRID(String paramString) throws SQLException {
/* 160 */     int i = paramString.indexOf(';', 5);
/* 161 */     if (i == -1) {
/* 162 */       throw new SQLException("Error parsing Geometry - SRID not delimited with ';' ");
/*     */     }
/* 164 */     return new String[] { paramString.substring(0, i), paramString.substring(i + 1) };
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/PGgeometry.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
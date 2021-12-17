/*     */ package examples;
/*     */ 
/*     */ import com.vividsolutions.jts.geom.Geometry;
/*     */ import com.vividsolutions.jts.geom.GeometryCollection;
/*     */ import com.vividsolutions.jts.geom.LineString;
/*     */ import com.vividsolutions.jts.geom.LinearRing;
/*     */ import com.vividsolutions.jts.geom.MultiLineString;
/*     */ import com.vividsolutions.jts.geom.MultiPoint;
/*     */ import com.vividsolutions.jts.geom.MultiPolygon;
/*     */ import com.vividsolutions.jts.geom.Point;
/*     */ import com.vividsolutions.jts.geom.Polygon;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.postgis.jts.JtsBinaryParser;
/*     */ import org.postgis.jts.JtsBinaryWriter;
/*     */ import org.postgis.jts.JtsGeometry;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JtsTestParser
/*     */ {
/*  55 */   public static String ALL = "ALL"; public static String ONLY10 = "ONLY10"; public static String EQUAL10 = "EQUAL10";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final String[][] testset = new String[][] { { ALL, "POINT(10 10)" }, { ALL, "POINT(10 10 0)" }, { ALL, "POINT(10 10 20)" }, { ALL, "MULTIPOINT(11 12, 20 20)" }, { ALL, "MULTIPOINT(11 12 13, 20 20 20)" }, { ALL, "LINESTRING(10 10,20 20,50 50,34 34)" }, { ALL, "LINESTRING(10 10 20,20 20 20,50 50 50,34 34 34)" }, { ALL, "POLYGON((10 10,20 10,20 20,20 10,10 10),(5 5,5 6,6 6,6 5,5 5))" }, { ALL, "POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))" }, { ALL, "MULTIPOLYGON(((10 10,20 10,20 20,20 10,10 10),(5 5,5 6,6 6,6 5,5 5)),((10 10,20 10,20 20,20 10,10 10),(5 5,5 6,6 6,6 5,5 5)))" }, { ALL, "MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ALL, "MULTILINESTRING((10 10,20 10,20 20,20 10,10 10),(5 5,5 6,6 6,6 5,5 5))" }, { ALL, "MULTILINESTRING((10 10 5,20 10 5,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))" }, { ALL, "GEOMETRYCOLLECTION(POINT(10 10),POINT(20 20))" }, { ALL, "GEOMETRYCOLLECTION(POINT(10 10 20),POINT(20 20 20))" }, { ALL, "GEOMETRYCOLLECTION(LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34),LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34))" }, { ALL, "GEOMETRYCOLLECTION(POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ONLY10, "GEOMETRYCOLLECTION(MULTIPOINT(10 10 10, 20 20 20),MULTIPOINT(10 10 10, 20 20 20))" }, { EQUAL10, "GEOMETRYCOLLECTION(MULTILINESTRING((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { EQUAL10, "GEOMETRYCOLLECTION(MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))),MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))))" }, { ALL, "GEOMETRYCOLLECTION(POINT(10 10 20),LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34),POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ONLY10, "GEOMETRYCOLLECTION(POINT(10 10 20),MULTIPOINT(10 10 10, 20 20 20),LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34),POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))),MULTILINESTRING((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ALL, "GEOMETRYCOLLECTION EMPTY" } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SRID = 4326;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SRIDPREFIX = "SRID=4326;";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static int failcount = 0;
/*     */   
/* 119 */   private static JtsBinaryParser bp = new JtsBinaryParser();
/*     */   
/* 121 */   private static final JtsBinaryWriter bw = new JtsBinaryWriter();
/*     */ 
/*     */   
/*     */   public static void test(String paramString1, Connection[] paramArrayOfConnection, String paramString2) throws SQLException {
/* 125 */     System.out.println("Original:  " + paramString1);
/* 126 */     Geometry geometry1 = JtsGeometry.geomFromString(paramString1);
/* 127 */     String str1 = geometry1.toString();
/* 128 */     if (paramString1.startsWith("SRID=")) {
/* 129 */       str1 = "SRID=" + geometry1.getSRID() + ";" + str1;
/*     */     }
/* 131 */     System.out.println("Parsed:    " + str1);
/* 132 */     Geometry geometry2 = JtsGeometry.geomFromString(str1);
/* 133 */     String str2 = geometry2.toString();
/* 134 */     if (paramString1.startsWith("SRID=")) {
/* 135 */       str2 = "SRID=" + geometry1.getSRID() + ";" + str2;
/*     */     }
/* 137 */     System.out.println("Re-Parsed: " + str2);
/* 138 */     if (!geometry1.equalsExact(geometry2)) {
/* 139 */       System.out.println("--- Geometries are not equal!");
/* 140 */       failcount++;
/* 141 */     } else if (geometry1.getSRID() != geometry2.getSRID()) {
/* 142 */       System.out.println("--- Geometriy SRIDs are not equal!");
/* 143 */       failcount++;
/* 144 */     } else if (!str2.equals(str1)) {
/* 145 */       System.out.println("--- Text Reps are not equal!");
/* 146 */       failcount++;
/*     */     } else {
/* 148 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 151 */     String str3 = bw.writeHexed(geometry1, (byte)1);
/* 152 */     System.out.println("NDRHex:    " + str3);
/* 153 */     geometry2 = JtsGeometry.geomFromString(str3);
/* 154 */     System.out.println("ReNDRHex:  " + geometry2.toString());
/* 155 */     if (!geometry1.equalsExact(geometry2)) {
/* 156 */       System.out.println("--- Geometries are not equal!");
/* 157 */       failcount++;
/*     */     } else {
/* 159 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 162 */     String str4 = bw.writeHexed(geometry1, (byte)0);
/* 163 */     System.out.println("XDRHex:    " + str4);
/* 164 */     geometry2 = JtsGeometry.geomFromString(str4);
/* 165 */     System.out.println("ReXDRHex:  " + geometry2.toString());
/* 166 */     if (!geometry1.equalsExact(geometry2)) {
/* 167 */       System.out.println("--- Geometries are not equal!");
/* 168 */       failcount++;
/*     */     } else {
/* 170 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 173 */     byte[] arrayOfByte1 = bw.writeBinary(geometry1, (byte)1);
/* 174 */     geometry2 = bp.parse(arrayOfByte1);
/* 175 */     System.out.println("NDR:       " + geometry2.toString());
/* 176 */     if (!geometry1.equalsExact(geometry2)) {
/* 177 */       System.out.println("--- Geometries are not equal!");
/* 178 */       failcount++;
/*     */     } else {
/* 180 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 183 */     byte[] arrayOfByte2 = bw.writeBinary(geometry1, (byte)0);
/* 184 */     geometry2 = bp.parse(arrayOfByte2);
/* 185 */     System.out.println("XDR:       " + geometry2.toString());
/* 186 */     if (!geometry1.equalsExact(geometry2)) {
/* 187 */       System.out.println("--- Geometries are not equal!");
/* 188 */       failcount++;
/*     */     } else {
/* 190 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 193 */     Geometry geometry3 = rebuildCS(geometry1);
/* 194 */     System.out.println("CoordArray:" + geometry2.toString());
/* 195 */     if (!geometry1.equalsExact(geometry3)) {
/* 196 */       System.out.println("--- Geometries are not equal!");
/* 197 */       failcount++;
/*     */     } else {
/* 199 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 202 */     String str5 = bw.writeHexed(geometry3, (byte)1);
/* 203 */     System.out.println("HexCArray: " + str5);
/* 204 */     if (!str5.equals(str3)) {
/* 205 */       System.out.println("--- CoordArray HexWKT is not equal: " + bp.parse(str5));
/* 206 */       failcount++;
/*     */     } else {
/* 208 */       System.out.println("HexEquals: yes");
/*     */     } 
/*     */     
/* 211 */     for (byte b = 0; b < paramArrayOfConnection.length; b++) {
/* 212 */       Connection connection = paramArrayOfConnection[b];
/* 213 */       Statement statement = connection.createStatement();
/* 214 */       int i = TestAutoregister.getPostgisMajor(statement);
/*     */       
/* 216 */       if (paramString2 == ONLY10 && i < 1) {
/* 217 */         System.out.println("PostGIS server too old, skipping test on connection " + b + ": " + connection.getCatalog());
/*     */       } else {
/*     */         
/* 220 */         System.out.println("Testing on connection " + b + ": " + connection.getCatalog());
/*     */         try {
/* 222 */           Geometry geometry = viaSQL(paramString1, statement);
/* 223 */           System.out.println("SQLin    : " + geometry.toString());
/* 224 */           if (!geometry1.equalsExact(geometry)) {
/* 225 */             System.out.println("--- Geometries after SQL are not equal!");
/* 226 */             if (paramString2 == EQUAL10 && i < 1) {
/* 227 */               System.out.println("--- This is expected with PostGIS " + i + ".X");
/*     */             } else {
/* 229 */               failcount++;
/*     */             } 
/*     */           } else {
/* 232 */             System.out.println("Eq SQL in: yes");
/*     */           } 
/* 234 */         } catch (SQLException sQLException) {
/* 235 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 236 */           failcount++;
/*     */         } 
/*     */         
/*     */         try {
/* 240 */           Geometry geometry = viaSQL(str1, statement);
/* 241 */           System.out.println("SQLout  :  " + geometry.toString());
/* 242 */           if (!geometry1.equalsExact(geometry)) {
/* 243 */             System.out.println("--- reparsed Geometries after SQL are not equal!");
/* 244 */             if (paramString2 == EQUAL10 && i < 1) {
/* 245 */               System.out.println("--- This is expected with PostGIS " + i + ".X");
/*     */             } else {
/* 247 */               failcount++;
/*     */             } 
/*     */           } else {
/* 250 */             System.out.println("Eq SQLout: yes");
/*     */           } 
/* 252 */         } catch (SQLException sQLException) {
/* 253 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 254 */           failcount++;
/*     */         } 
/*     */         
/*     */         try {
/* 258 */           Geometry geometry = viaPrepSQL(geometry1, connection);
/* 259 */           System.out.println("Prepared:  " + geometry.toString());
/* 260 */           if (!geometry1.equalsExact(geometry)) {
/* 261 */             System.out.println("--- reparsed Geometries after prepared StatementSQL are not equal!");
/* 262 */             if (paramString2 == EQUAL10 && i < 1) {
/* 263 */               System.out.println("--- This is expected with PostGIS " + i + ".X");
/*     */             } else {
/* 265 */               failcount++;
/*     */             } 
/*     */           } else {
/* 268 */             System.out.println("Eq Prep: yes");
/*     */           } 
/* 270 */         } catch (SQLException sQLException) {
/* 271 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 272 */           failcount++;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 279 */           if (i >= 1) {
/* 280 */             Geometry geometry = ewktViaSQL(paramString1, statement);
/* 281 */             System.out.println("asEWKT   : " + geometry.toString());
/* 282 */             if (!geometry1.equalsExact(geometry)) {
/* 283 */               System.out.println("--- Geometries after EWKT SQL are not equal!");
/* 284 */               failcount++;
/*     */             } else {
/* 286 */               System.out.println("equal   : yes");
/*     */             } 
/*     */           } 
/* 289 */         } catch (SQLException sQLException) {
/* 290 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 291 */           failcount++;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 296 */           if (i >= 1) {
/* 297 */             Geometry geometry = ewkbViaSQL(paramString1, statement);
/* 298 */             System.out.println("asEWKB   : " + geometry.toString());
/* 299 */             if (!geometry1.equalsExact(geometry)) {
/* 300 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 301 */               failcount++;
/*     */             } else {
/* 303 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 306 */         } catch (SQLException sQLException) {
/* 307 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 308 */           failcount++;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 313 */           if (i >= 1) {
/* 314 */             Geometry geometry = viaSQL(str3, statement);
/* 315 */             System.out.println("hexNWKT:   " + geometry.toString());
/* 316 */             if (!geometry1.equalsExact(geometry)) {
/* 317 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 318 */               failcount++;
/*     */             } else {
/* 320 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 323 */         } catch (SQLException sQLException) {
/* 324 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 325 */           failcount++;
/*     */         } 
/*     */         try {
/* 328 */           if (i >= 1) {
/* 329 */             Geometry geometry = viaSQL(str4, statement);
/* 330 */             System.out.println("hexXWKT:   " + geometry.toString());
/* 331 */             if (!geometry1.equalsExact(geometry)) {
/* 332 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 333 */               failcount++;
/*     */             } else {
/* 335 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 338 */         } catch (SQLException sQLException) {
/* 339 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 340 */           failcount++;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 345 */           if (i >= 1) {
/* 346 */             Geometry geometry = binaryViaSQL(arrayOfByte1, connection);
/* 347 */             System.out.println("NWKT:      " + geometry.toString());
/* 348 */             if (!geometry1.equalsExact(geometry)) {
/* 349 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 350 */               failcount++;
/*     */             } else {
/* 352 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 355 */         } catch (SQLException sQLException) {
/* 356 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 357 */           failcount++;
/*     */         } 
/*     */         try {
/* 360 */           if (i >= 1) {
/* 361 */             Geometry geometry = binaryViaSQL(arrayOfByte2, connection);
/* 362 */             System.out.println("XWKT:      " + geometry.toString());
/* 363 */             if (!geometry1.equalsExact(geometry)) {
/* 364 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 365 */               failcount++;
/*     */             } else {
/* 367 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 370 */         } catch (SQLException sQLException) {
/* 371 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 372 */           failcount++;
/*     */         } 
/*     */       } 
/*     */       
/* 376 */       statement.close();
/*     */     } 
/* 378 */     System.out.println("***");
/*     */   }
/*     */ 
/*     */   
/*     */   public static Geometry rebuildCS(Geometry paramGeometry) {
/* 383 */     if (paramGeometry instanceof Point)
/* 384 */       return (Geometry)rebuildCSPoint((Point)paramGeometry); 
/* 385 */     if (paramGeometry instanceof MultiPoint)
/* 386 */       return (Geometry)rebuildCSMP((MultiPoint)paramGeometry); 
/* 387 */     if (paramGeometry instanceof LineString)
/* 388 */       return (Geometry)rebuildCSLS((LineString)paramGeometry); 
/* 389 */     if (paramGeometry instanceof MultiLineString)
/* 390 */       return (Geometry)rebuildCSMLS((MultiLineString)paramGeometry); 
/* 391 */     if (paramGeometry instanceof Polygon)
/* 392 */       return (Geometry)rebuildCSP((Polygon)paramGeometry); 
/* 393 */     if (paramGeometry instanceof MultiPolygon)
/* 394 */       return (Geometry)rebuildCSMP((MultiPolygon)paramGeometry); 
/* 395 */     if (paramGeometry instanceof GeometryCollection) {
/* 396 */       return rebuildCSGC((GeometryCollection)paramGeometry);
/*     */     }
/* 398 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry rebuildCSGC(GeometryCollection paramGeometryCollection) {
/* 403 */     Geometry[] arrayOfGeometry = new Geometry[paramGeometryCollection.getNumGeometries()];
/* 404 */     for (byte b = 0; b < paramGeometryCollection.getNumGeometries(); b++) {
/* 405 */       arrayOfGeometry[b] = rebuildCS(paramGeometryCollection.getGeometryN(b));
/*     */     }
/* 407 */     GeometryCollection geometryCollection = paramGeometryCollection.getFactory().createGeometryCollection(arrayOfGeometry);
/* 408 */     geometryCollection.setSRID(paramGeometryCollection.getSRID());
/* 409 */     return (Geometry)geometryCollection;
/*     */   }
/*     */   
/*     */   private static MultiPolygon rebuildCSMP(MultiPolygon paramMultiPolygon) {
/* 413 */     Polygon[] arrayOfPolygon = new Polygon[paramMultiPolygon.getNumGeometries()];
/* 414 */     for (byte b = 0; b < arrayOfPolygon.length; b++) {
/* 415 */       arrayOfPolygon[b] = rebuildCSP((Polygon)paramMultiPolygon.getGeometryN(b));
/*     */     }
/* 417 */     MultiPolygon multiPolygon = paramMultiPolygon.getFactory().createMultiPolygon(arrayOfPolygon);
/* 418 */     multiPolygon.setSRID(paramMultiPolygon.getSRID());
/* 419 */     return multiPolygon;
/*     */   }
/*     */   
/*     */   private static Polygon rebuildCSP(Polygon paramPolygon) {
/* 423 */     LinearRing linearRing = rebuildLR(paramPolygon.getExteriorRing());
/* 424 */     LinearRing[] arrayOfLinearRing = new LinearRing[paramPolygon.getNumInteriorRing()];
/* 425 */     for (byte b = 0; b < arrayOfLinearRing.length; b++) {
/* 426 */       arrayOfLinearRing[b] = rebuildLR(paramPolygon.getInteriorRingN(b));
/*     */     }
/* 428 */     Polygon polygon = paramPolygon.getFactory().createPolygon(linearRing, arrayOfLinearRing);
/* 429 */     polygon.setSRID(paramPolygon.getSRID());
/* 430 */     return polygon;
/*     */   }
/*     */   
/*     */   private static LinearRing rebuildLR(LineString paramLineString) {
/* 434 */     LinearRing linearRing = paramLineString.getFactory().createLinearRing(paramLineString.getCoordinates());
/* 435 */     linearRing.setSRID(paramLineString.getSRID());
/* 436 */     return linearRing;
/*     */   }
/*     */   
/*     */   private static MultiLineString rebuildCSMLS(MultiLineString paramMultiLineString) {
/* 440 */     LineString[] arrayOfLineString = new LineString[paramMultiLineString.getNumGeometries()];
/* 441 */     for (byte b = 0; b < arrayOfLineString.length; b++) {
/* 442 */       arrayOfLineString[b] = rebuildCSLS((LineString)paramMultiLineString.getGeometryN(b));
/*     */     }
/* 444 */     MultiLineString multiLineString = paramMultiLineString.getFactory().createMultiLineString(arrayOfLineString);
/* 445 */     multiLineString.setSRID(paramMultiLineString.getSRID());
/* 446 */     return multiLineString;
/*     */   }
/*     */ 
/*     */   
/*     */   private static LineString rebuildCSLS(LineString paramLineString) {
/* 451 */     LineString lineString = paramLineString.getFactory().createLineString(paramLineString.getCoordinates());
/* 452 */     lineString.setSRID(paramLineString.getSRID());
/* 453 */     return lineString;
/*     */   }
/*     */   
/*     */   private static MultiPoint rebuildCSMP(MultiPoint paramMultiPoint) {
/* 457 */     Point[] arrayOfPoint = new Point[paramMultiPoint.getNumGeometries()];
/* 458 */     for (byte b = 0; b < arrayOfPoint.length; b++) {
/* 459 */       arrayOfPoint[b] = rebuildCSPoint((Point)paramMultiPoint.getGeometryN(b));
/*     */     }
/* 461 */     MultiPoint multiPoint = paramMultiPoint.getFactory().createMultiPoint(arrayOfPoint);
/* 462 */     multiPoint.setSRID(paramMultiPoint.getSRID());
/* 463 */     return multiPoint;
/*     */   }
/*     */   
/*     */   private static Point rebuildCSPoint(Point paramPoint) {
/* 467 */     Point point = paramPoint.getFactory().createPoint(paramPoint.getCoordinate());
/* 468 */     point.setSRID(paramPoint.getSRID());
/* 469 */     return point;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry viaSQL(String paramString, Statement paramStatement) throws SQLException {
/* 474 */     ResultSet resultSet = paramStatement.executeQuery("SELECT geometry_in('" + paramString + "')");
/* 475 */     resultSet.next();
/* 476 */     return ((JtsGeometry)resultSet.getObject(1)).getGeometry();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Geometry viaPrepSQL(Geometry paramGeometry, Connection paramConnection) throws SQLException {
/* 484 */     PreparedStatement preparedStatement = paramConnection.prepareStatement("SELECT ?::geometry");
/* 485 */     JtsGeometry jtsGeometry1 = new JtsGeometry(paramGeometry);
/* 486 */     preparedStatement.setObject(1, jtsGeometry1, 1111);
/* 487 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 488 */     resultSet.next();
/* 489 */     JtsGeometry jtsGeometry2 = (JtsGeometry)resultSet.getObject(1);
/* 490 */     return jtsGeometry2.getGeometry();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry ewktViaSQL(String paramString, Statement paramStatement) throws SQLException {
/* 495 */     ResultSet resultSet = paramStatement.executeQuery("SELECT asEWKT(geometry_in('" + paramString + "'))");
/* 496 */     resultSet.next();
/* 497 */     String str = resultSet.getString(1);
/* 498 */     return JtsGeometry.geomFromString(str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry ewkbViaSQL(String paramString, Statement paramStatement) throws SQLException {
/* 503 */     ResultSet resultSet = paramStatement.executeQuery("SELECT asEWKB(geometry_in('" + paramString + "'))");
/* 504 */     resultSet.next();
/* 505 */     byte[] arrayOfByte = resultSet.getBytes(1);
/* 506 */     return bp.parse(arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry binaryViaSQL(byte[] paramArrayOfbyte, Connection paramConnection) throws SQLException {
/* 511 */     PreparedStatement preparedStatement = paramConnection.prepareStatement("SELECT ?::bytea::geometry");
/* 512 */     preparedStatement.setBytes(1, paramArrayOfbyte);
/* 513 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 514 */     resultSet.next();
/* 515 */     JtsGeometry jtsGeometry = (JtsGeometry)resultSet.getObject(1);
/* 516 */     return jtsGeometry.getGeometry();
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
/*     */   public static Connection connect(String paramString1, String paramString2, String paramString3) throws SQLException {
/* 532 */     return DriverManager.getConnection(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadDrivers() throws ClassNotFoundException {
/* 537 */     Class.forName("org.postgis.jts.JtsWrapper");
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws SQLException, ClassNotFoundException {
/*     */     PGtokenizer pGtokenizer;
/* 542 */     loadDrivers();
/*     */ 
/*     */     
/* 545 */     String str1 = null;
/* 546 */     String str2 = null;
/*     */     
/* 548 */     if (paramArrayOfString.length == 1 && paramArrayOfString[0].equalsIgnoreCase("offline")) {
/* 549 */       System.out.println("Performing only offline tests");
/* 550 */       pGtokenizer = new PGtokenizer("", ';');
/* 551 */     } else if (paramArrayOfString.length == 3) {
/* 552 */       System.out.println("Performing offline and online tests");
/* 553 */       pGtokenizer = new PGtokenizer(paramArrayOfString[0], ';');
/*     */       
/* 555 */       str1 = paramArrayOfString[1];
/* 556 */       str2 = paramArrayOfString[2];
/*     */     } else {
/* 558 */       System.err.println("Usage: java examples/TestParser dburls user pass [tablename]");
/* 559 */       System.err.println("   or: java examples/TestParser offline");
/* 560 */       System.err.println();
/* 561 */       System.err.println("dburls has one or more jdbc urls separated by ; in the following format");
/* 562 */       System.err.println("jdbc:postgresql://HOST:PORT/DATABASENAME");
/* 563 */       System.err.println("tablename is 'jdbc_test' by default.");
/* 564 */       System.exit(1);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 570 */     Connection[] arrayOfConnection = new Connection[pGtokenizer.getSize()]; byte b;
/* 571 */     for (b = 0; b < pGtokenizer.getSize(); b++) {
/* 572 */       System.out.println("Creating JDBC connection to " + pGtokenizer.getToken(b));
/* 573 */       arrayOfConnection[b] = connect(pGtokenizer.getToken(b), str1, str2);
/*     */     } 
/*     */     
/* 576 */     System.out.println("Performing tests...");
/* 577 */     System.out.println("***");
/*     */     
/* 579 */     for (b = 0; b < testset.length; b++) {
/* 580 */       test(testset[b][1], arrayOfConnection, testset[b][0]);
/* 581 */       test("SRID=4326;" + testset[b][1], arrayOfConnection, testset[b][0]);
/*     */     } 
/*     */     
/* 584 */     System.out.print("cleaning up...");
/* 585 */     for (b = 0; b < arrayOfConnection.length; b++) {
/* 586 */       arrayOfConnection[b].close();
/*     */     }
/*     */     
/* 589 */     System.out.println("Finished, " + failcount + " tests failed!");
/* 590 */     System.err.println("Finished, " + failcount + " tests failed!");
/* 591 */     System.exit(failcount);
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/examples/JtsTestParser.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
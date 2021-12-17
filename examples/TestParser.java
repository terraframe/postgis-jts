/*     */ package examples;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.postgis.Geometry;
/*     */ import org.postgis.PGgeometry;
/*     */ import org.postgis.binary.BinaryParser;
/*     */ import org.postgis.binary.BinaryWriter;
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
/*     */ public class TestParser
/*     */ {
/*  46 */   public static String ALL = "ALL"; public static String ONLY10 = "ONLY10"; public static String EQUAL10 = "EQUAL10";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final String[][] testset = new String[][] { { ALL, "POINT(10 10)" }, { ALL, "POINT(10 10 0)" }, { ALL, "POINT(10 10 20)" }, { ALL, "POINT(1e100 1.2345e-100 -2e-5)" }, { ONLY10, "POINTM(10 10 20)" }, { ONLY10, "POINT(10 10 20 30)" }, { ALL, "MULTIPOINT(11 12, 20 20)" }, { ALL, "MULTIPOINT(11 12 13, 20 20 20)" }, { ONLY10, "MULTIPOINTM(11 12 13, 20 20 20)" }, { ONLY10, "MULTIPOINT(11 12 13 14,20 20 20 20)" }, { ALL, "MULTIPOINT((11 12), (20 20))" }, { ALL, "MULTIPOINT((11 12 13), (20 20 20))" }, { ONLY10, "MULTIPOINTM((11 12 13), (20 20 20))" }, { ONLY10, "MULTIPOINT((11 12 13 14),(20 20 20 20))" }, { ALL, "LINESTRING(10 10,20 20,50 50,34 34)" }, { ALL, "LINESTRING(10 10 20,20 20 20,50 50 50,34 34 34)" }, { ONLY10, "LINESTRINGM(10 10 20,20 20 20,50 50 50,34 34 34)" }, { ONLY10, "LINESTRING(10 10 20 20,20 20 20 20,50 50 50 50,34 34 34 50)" }, { ALL, "POLYGON((10 10,20 10,20 20,20 10,10 10),(5 5,5 6,6 6,6 5,5 5))" }, { ALL, "POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))" }, { ONLY10, "POLYGONM((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))" }, { ONLY10, "POLYGON((10 10 0 7,20 10 0 7,20 20 0 7,20 10 0 7,10 10 0 7),(5 5 0 7,5 6 0 7,6 6 0 7,6 5 0 7,5 5 0 7))" }, { ALL, "MULTIPOLYGON(((10 10,20 10,20 20,20 10,10 10),(5 5,5 6,6 6,6 5,5 5)),((10 10,20 10,20 20,20 10,10 10),(5 5,5 6,6 6,6 5,5 5)))" }, { ALL, "MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ONLY10, "MULTIPOLYGONM(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ONLY10, "MULTIPOLYGON(((10 10 0 7,20 10 0 7,20 20 0 7,20 10 0 7,10 10 0 7),(5 5 0 7,5 6 0 7,6 6 0 7,6 5 0 7,5 5 0 7)),((10 10 0 7,20 10 0 7,20 20 0 7,20 10 0 7,10 10 0 7),(5 5 0 7,5 6 0 7,6 6 0 7,6 5 0 7,5 5 0 7)))" }, { ALL, "MULTILINESTRING((10 10,20 10,20 20,20 10,10 10),(5 5,5 6,6 6,6 5,5 5))" }, { ALL, "MULTILINESTRING((10 10 5,20 10 5,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))" }, { ONLY10, "MULTILINESTRINGM((10 10 7,20 10 7,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))" }, { ONLY10, "MULTILINESTRING((10 10 0 7,20 10 0 7,20 20 0 7,20 10 0 7,10 10 0 7),(5 5 0 7,5 6 0 7,6 6 0 7,6 5 0 7,5 5 0 7))" }, { ALL, "GEOMETRYCOLLECTION(POINT(10 10),POINT(20 20))" }, { ALL, "GEOMETRYCOLLECTION(POINT(10 10 20),POINT(20 20 20))" }, { ONLY10, "GEOMETRYCOLLECTIONM(POINT(10 10 20),POINT(20 20 20))" }, { ONLY10, "GEOMETRYCOLLECTION(POINT(10 10 20 7),POINT(20 20 20 7))" }, { ALL, "GEOMETRYCOLLECTION(LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34),LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34))" }, { ALL, "GEOMETRYCOLLECTION(POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ONLY10, "GEOMETRYCOLLECTION(MULTIPOINT(10 10 10, 20 20 20),MULTIPOINT(10 10 10, 20 20 20))" }, { ONLY10, "GEOMETRYCOLLECTION(MULTIPOINT((10 10 10), (20 20 20)),MULTIPOINT((10 10 10), (20 20 20)))" }, { EQUAL10, "GEOMETRYCOLLECTION(MULTILINESTRING((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { EQUAL10, "GEOMETRYCOLLECTION(MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))),MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))))" }, { ALL, "GEOMETRYCOLLECTION(POINT(10 10 20),LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34),POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ONLY10, "GEOMETRYCOLLECTION(POINT(10 10 20),MULTIPOINT(10 10 10, 20 20 20),LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34),POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))),MULTILINESTRING((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ONLY10, "GEOMETRYCOLLECTION(POINT(10 10 20),MULTIPOINT((10 10 10), (20 20 20)),LINESTRING(10 10 20,20 20 20, 50 50 50, 34 34 34),POLYGON((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),MULTIPOLYGON(((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))),MULTILINESTRING((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))" }, { ALL, "GEOMETRYCOLLECTION(EMPTY)" }, { ALL, "GEOMETRYCOLLECTION EMPTY" }, { ONLY10, "POINT EMPTY" }, { ONLY10, "LINESTRING EMPTY" }, { ONLY10, "POLYGON EMPTY" }, { ONLY10, "MULTIPOINT EMPTY" }, { ONLY10, "MULTILINESTRING EMPTY" }, { ONLY10, "MULTIPOLYGON EMPTY" } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public static int failcount = 0;
/*     */   
/* 222 */   private static BinaryParser bp = new BinaryParser();
/* 223 */   private static final BinaryWriter bw = new BinaryWriter();
/*     */ 
/*     */   
/*     */   public static void test(String paramString1, Connection[] paramArrayOfConnection, String paramString2) throws SQLException {
/* 227 */     System.out.println("Original:  " + paramString1);
/* 228 */     Geometry geometry1 = PGgeometry.geomFromString(paramString1);
/* 229 */     String str1 = geometry1.toString();
/* 230 */     System.out.println("Parsed:    " + str1);
/* 231 */     Geometry geometry2 = PGgeometry.geomFromString(str1);
/* 232 */     String str2 = geometry2.toString();
/* 233 */     System.out.println("Re-Parsed: " + str2);
/* 234 */     if (!geometry1.equals(geometry2)) {
/* 235 */       System.out.println("--- Geometries are not equal!");
/* 236 */       failcount++;
/* 237 */     } else if (!str2.equals(str1)) {
/* 238 */       System.out.println("--- Text Reps are not equal!");
/* 239 */       failcount++;
/*     */     } else {
/* 241 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 244 */     String str3 = bw.writeHexed(geometry2, (byte)1);
/* 245 */     System.out.println("NDRHex:    " + str3);
/* 246 */     geometry2 = PGgeometry.geomFromString(str3);
/* 247 */     System.out.println("ReNDRHex:  " + geometry2.toString());
/* 248 */     if (!geometry1.equals(geometry2)) {
/* 249 */       System.out.println("--- Geometries are not equal!");
/* 250 */       failcount++;
/*     */     } else {
/* 252 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 255 */     String str4 = bw.writeHexed(geometry2, (byte)0);
/* 256 */     System.out.println("XDRHex:    " + str4);
/* 257 */     geometry2 = PGgeometry.geomFromString(str4);
/* 258 */     System.out.println("ReXDRHex:  " + geometry2.toString());
/* 259 */     if (!geometry1.equals(geometry2)) {
/* 260 */       System.out.println("--- Geometries are not equal!");
/* 261 */       failcount++;
/*     */     } else {
/* 263 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 266 */     byte[] arrayOfByte1 = bw.writeBinary(geometry2, (byte)1);
/* 267 */     geometry2 = bp.parse(arrayOfByte1);
/* 268 */     System.out.println("NDR:       " + geometry2.toString());
/* 269 */     if (!geometry1.equals(geometry2)) {
/* 270 */       System.out.println("--- Geometries are not equal!");
/* 271 */       failcount++;
/*     */     } else {
/* 273 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 276 */     byte[] arrayOfByte2 = bw.writeBinary(geometry2, (byte)0);
/* 277 */     geometry2 = bp.parse(arrayOfByte2);
/* 278 */     System.out.println("XDR:       " + geometry2.toString());
/* 279 */     if (!geometry1.equals(geometry2)) {
/* 280 */       System.out.println("--- Geometries are not equal!");
/* 281 */       failcount++;
/*     */     } else {
/* 283 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/* 286 */     for (byte b = 0; b < paramArrayOfConnection.length; b++) {
/* 287 */       Connection connection = paramArrayOfConnection[b];
/* 288 */       Statement statement = connection.createStatement();
/* 289 */       int i = TestAutoregister.getPostgisMajor(statement);
/*     */       
/* 291 */       if (paramString2 == ONLY10 && i < 1) {
/* 292 */         System.out.println("PostGIS server too old, skipping test on connection " + b + ": " + connection.getCatalog());
/*     */       } else {
/*     */         
/* 295 */         System.out.println("Testing on connection " + b + ": " + connection.getCatalog());
/*     */         try {
/* 297 */           Geometry geometry = viaSQL(paramString1, statement);
/* 298 */           System.out.println("SQLin    : " + geometry.toString());
/* 299 */           if (!geometry1.equals(geometry)) {
/* 300 */             System.out.println("--- Geometries after SQL are not equal!");
/* 301 */             if (paramString2 == EQUAL10 && i < 1) {
/* 302 */               System.out.println("--- This is expected with PostGIS " + i + ".X");
/*     */             } else {
/*     */               
/* 305 */               failcount++;
/*     */             } 
/*     */           } else {
/* 308 */             System.out.println("Eq SQL in: yes");
/*     */           } 
/* 310 */         } catch (SQLException sQLException) {
/* 311 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 312 */           failcount++;
/*     */         } 
/*     */         
/*     */         try {
/* 316 */           Geometry geometry = viaSQL(str1, statement);
/* 317 */           System.out.println("SQLout  :  " + geometry.toString());
/* 318 */           if (!geometry1.equals(geometry)) {
/* 319 */             System.out.println("--- reparsed Geometries after SQL are not equal!");
/* 320 */             if (paramString2 == EQUAL10 && i < 1) {
/* 321 */               System.out.println("--- This is expected with PostGIS " + i + ".X");
/*     */             } else {
/*     */               
/* 324 */               failcount++;
/*     */             } 
/*     */           } else {
/* 327 */             System.out.println("Eq SQLout: yes");
/*     */           } 
/* 329 */         } catch (SQLException sQLException) {
/* 330 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 331 */           failcount++;
/*     */         } 
/*     */         
/*     */         try {
/* 335 */           Geometry geometry = viaPrepSQL(geometry1, connection);
/* 336 */           System.out.println("Prepared:  " + geometry.toString());
/* 337 */           if (!geometry1.equals(geometry)) {
/* 338 */             System.out.println("--- reparsed Geometries after prepared StatementSQL are not equal!");
/* 339 */             if (paramString2 == EQUAL10 && i < 1) {
/* 340 */               System.out.println("--- This is expected with PostGIS " + i + ".X");
/*     */             } else {
/*     */               
/* 343 */               failcount++;
/*     */             } 
/*     */           } else {
/* 346 */             System.out.println("Eq Prep: yes");
/*     */           } 
/* 348 */         } catch (SQLException sQLException) {
/* 349 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 350 */           failcount++;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 357 */           if (i >= 1) {
/* 358 */             Geometry geometry = ewktViaSQL(paramString1, statement);
/* 359 */             System.out.println("asEWKT   : " + geometry.toString());
/* 360 */             if (!geometry1.equals(geometry)) {
/* 361 */               System.out.println("--- Geometries after EWKT SQL are not equal!");
/* 362 */               failcount++;
/*     */             } else {
/* 364 */               System.out.println("equal   : yes");
/*     */             } 
/*     */           } 
/* 367 */         } catch (SQLException sQLException) {
/* 368 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 369 */           failcount++;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 374 */           if (i >= 1) {
/* 375 */             Geometry geometry = ewkbViaSQL(paramString1, statement);
/* 376 */             System.out.println("asEWKB   : " + geometry.toString());
/* 377 */             if (!geometry1.equals(geometry)) {
/* 378 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 379 */               failcount++;
/*     */             } else {
/* 381 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 384 */         } catch (SQLException sQLException) {
/* 385 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 386 */           failcount++;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 391 */           if (i >= 1) {
/* 392 */             Geometry geometry = viaSQL(str3, statement);
/* 393 */             System.out.println("hexNWKT:   " + geometry.toString());
/* 394 */             if (!geometry1.equals(geometry)) {
/* 395 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 396 */               failcount++;
/*     */             } else {
/* 398 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 401 */         } catch (SQLException sQLException) {
/* 402 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 403 */           failcount++;
/*     */         } 
/*     */         try {
/* 406 */           if (i >= 1) {
/* 407 */             Geometry geometry = viaSQL(str4, statement);
/* 408 */             System.out.println("hexXWKT:   " + geometry.toString());
/* 409 */             if (!geometry1.equals(geometry)) {
/* 410 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 411 */               failcount++;
/*     */             } else {
/* 413 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 416 */         } catch (SQLException sQLException) {
/* 417 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 418 */           failcount++;
/*     */         } 
/*     */ 
/*     */         
/*     */         try {
/* 423 */           if (i >= 1) {
/* 424 */             Geometry geometry = binaryViaSQL(arrayOfByte1, connection);
/* 425 */             System.out.println("NWKT:      " + geometry.toString());
/* 426 */             if (!geometry1.equals(geometry)) {
/* 427 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 428 */               failcount++;
/*     */             } else {
/* 430 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 433 */         } catch (SQLException sQLException) {
/* 434 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 435 */           failcount++;
/*     */         } 
/*     */         try {
/* 438 */           if (i >= 1) {
/* 439 */             Geometry geometry = binaryViaSQL(arrayOfByte2, connection);
/* 440 */             System.out.println("XWKT:      " + geometry.toString());
/* 441 */             if (!geometry1.equals(geometry)) {
/* 442 */               System.out.println("--- Geometries after EWKB SQL are not equal!");
/* 443 */               failcount++;
/*     */             } else {
/* 445 */               System.out.println("equal    : yes");
/*     */             } 
/*     */           } 
/* 448 */         } catch (SQLException sQLException) {
/* 449 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 450 */           failcount++;
/*     */         } 
/*     */       } 
/*     */       
/* 454 */       statement.close();
/*     */     } 
/* 456 */     System.out.println("***");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry viaSQL(String paramString, Statement paramStatement) throws SQLException {
/* 461 */     ResultSet resultSet = paramStatement.executeQuery("SELECT geometry_in('" + paramString + "')");
/* 462 */     resultSet.next();
/* 463 */     return ((PGgeometry)resultSet.getObject(1)).getGeometry();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Geometry viaPrepSQL(Geometry paramGeometry, Connection paramConnection) throws SQLException {
/* 471 */     PreparedStatement preparedStatement = paramConnection.prepareStatement("SELECT ?::geometry");
/* 472 */     PGgeometry pGgeometry1 = new PGgeometry(paramGeometry);
/* 473 */     preparedStatement.setObject(1, pGgeometry1, 1111);
/* 474 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 475 */     resultSet.next();
/* 476 */     PGgeometry pGgeometry2 = (PGgeometry)resultSet.getObject(1);
/* 477 */     return pGgeometry2.getGeometry();
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry ewktViaSQL(String paramString, Statement paramStatement) throws SQLException {
/* 482 */     ResultSet resultSet = paramStatement.executeQuery("SELECT asEWKT(geometry_in('" + paramString + "'))");
/* 483 */     resultSet.next();
/* 484 */     String str = resultSet.getString(1);
/* 485 */     return PGgeometry.geomFromString(str);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry ewkbViaSQL(String paramString, Statement paramStatement) throws SQLException {
/* 490 */     ResultSet resultSet = paramStatement.executeQuery("SELECT asEWKB(geometry_in('" + paramString + "'))");
/* 491 */     resultSet.next();
/* 492 */     byte[] arrayOfByte = resultSet.getBytes(1);
/* 493 */     return bp.parse(arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Geometry binaryViaSQL(byte[] paramArrayOfbyte, Connection paramConnection) throws SQLException {
/* 498 */     PreparedStatement preparedStatement = paramConnection.prepareStatement("SELECT ?::bytea::geometry");
/* 499 */     preparedStatement.setBytes(1, paramArrayOfbyte);
/* 500 */     ResultSet resultSet = preparedStatement.executeQuery();
/* 501 */     resultSet.next();
/* 502 */     PGgeometry pGgeometry = (PGgeometry)resultSet.getObject(1);
/* 503 */     return pGgeometry.getGeometry();
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
/* 519 */     return DriverManager.getConnection(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadDrivers() throws ClassNotFoundException {
/* 524 */     Class.forName("org.postgis.DriverWrapper");
/* 525 */     Class.forName("org.postgis.DriverWrapperAutoprobe");
/*     */   }
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws SQLException, ClassNotFoundException {
/*     */     PGtokenizer pGtokenizer;
/* 530 */     loadDrivers();
/*     */ 
/*     */     
/* 533 */     String str1 = null;
/* 534 */     String str2 = null;
/*     */     
/* 536 */     if (paramArrayOfString.length == 1 && paramArrayOfString[0].equalsIgnoreCase("offline")) {
/* 537 */       System.out.println("Performing only offline tests");
/* 538 */       pGtokenizer = new PGtokenizer("", ';');
/* 539 */     } else if (paramArrayOfString.length == 3) {
/* 540 */       System.out.println("Performing offline and online tests");
/* 541 */       pGtokenizer = new PGtokenizer(paramArrayOfString[0], ';');
/*     */       
/* 543 */       str1 = paramArrayOfString[1];
/* 544 */       str2 = paramArrayOfString[2];
/*     */     } else {
/* 546 */       System.err.println("Usage: java examples/TestParser dburls user pass");
/* 547 */       System.err.println("   or: java examples/TestParser offline");
/* 548 */       System.err.println();
/* 549 */       System.err.println("dburls has one or more jdbc urls separated by ; in the following format");
/* 550 */       System.err.println("jdbc:postgresql://HOST:PORT/DATABASENAME");
/* 551 */       System.exit(1);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 557 */     Connection[] arrayOfConnection = new Connection[pGtokenizer.getSize()]; byte b;
/* 558 */     for (b = 0; b < pGtokenizer.getSize(); b++) {
/* 559 */       System.out.println("Creating JDBC connection to " + pGtokenizer.getToken(b));
/* 560 */       arrayOfConnection[b] = connect(pGtokenizer.getToken(b), str1, str2);
/*     */     } 
/*     */     
/* 563 */     System.out.println("Performing tests...");
/* 564 */     System.out.println("***");
/*     */     
/* 566 */     for (b = 0; b < testset.length; b++) {
/* 567 */       test(testset[b][1], arrayOfConnection, testset[b][0]);
/* 568 */       test("SRID=4326;" + testset[b][1], arrayOfConnection, testset[b][0]);
/*     */     } 
/*     */     
/* 571 */     System.out.print("cleaning up...");
/* 572 */     for (b = 0; b < arrayOfConnection.length; b++) {
/* 573 */       arrayOfConnection[b].close();
/*     */     }
/*     */     
/* 576 */     System.out.println("Finished, " + failcount + " tests failed!");
/* 577 */     System.err.println("Finished, " + failcount + " tests failed!");
/* 578 */     System.exit(failcount);
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/examples/TestParser.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
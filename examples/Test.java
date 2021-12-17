/*     */ package examples;
/*     */ 
/*     */ import java.sql.SQLException;
/*     */ import org.postgis.LineString;
/*     */ import org.postgis.LinearRing;
/*     */ import org.postgis.MultiLineString;
/*     */ import org.postgis.MultiPolygon;
/*     */ import org.postgis.PGgeometry;
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
/*     */ public class Test
/*     */ {
/*     */   public static void main(String[] paramArrayOfString) throws SQLException {
/*  42 */     String str1 = "MULTILINESTRING ((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))";
/*  43 */     String str2 = "MULTIPOLYGON (((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)),((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0)))";
/*  44 */     String str3 = "POLYGON ((10 10 0,20 10 0,20 20 0,20 10 0,10 10 0),(5 5 0,5 6 0,6 6 0,6 5 0,5 5 0))";
/*  45 */     String str4 = "LINESTRING  (10 10 20,20 20 20, 50 50 50, 34 34 34)";
/*  46 */     String str5 = "POINT(10 10 20)";
/*  47 */     String str6 = "(10 10 20,34 34 34, 23 19 23 , 10 10 11)";
/*     */     
/*  49 */     System.out.println("LinearRing Test:");
/*  50 */     System.out.println("\t" + str6);
/*  51 */     LinearRing linearRing = new LinearRing(str6);
/*  52 */     System.out.println("\t" + linearRing.toString());
/*     */     
/*  54 */     System.out.println();
/*     */     
/*  56 */     System.out.println("Point Test:");
/*  57 */     System.out.println("\t" + str5);
/*  58 */     Point point = new Point(str5);
/*  59 */     System.out.println("\t" + point.toString());
/*     */     
/*  61 */     System.out.println();
/*     */     
/*  63 */     System.out.println("LineString Test:");
/*  64 */     System.out.println("\t" + str4);
/*  65 */     LineString lineString = new LineString(str4);
/*  66 */     System.out.println("\t" + lineString.toString());
/*     */     
/*  68 */     System.out.println();
/*     */     
/*  70 */     System.out.println("Polygon Test:");
/*  71 */     System.out.println("\t" + str3);
/*  72 */     Polygon polygon = new Polygon(str3);
/*  73 */     System.out.println("\t" + polygon.toString());
/*     */     
/*  75 */     System.out.println();
/*     */     
/*  77 */     System.out.println("MultiPolygon Test:");
/*  78 */     System.out.println("\t" + str2);
/*  79 */     MultiPolygon multiPolygon = new MultiPolygon(str2);
/*  80 */     System.out.println("\t" + multiPolygon.toString());
/*     */     
/*  82 */     System.out.println();
/*     */     
/*  84 */     System.out.println("MultiLineString Test:");
/*  85 */     System.out.println("\t" + str1);
/*  86 */     MultiLineString multiLineString = new MultiLineString(str1);
/*  87 */     System.out.println("\t" + multiLineString.toString());
/*     */     
/*  89 */     System.out.println();
/*     */     
/*  91 */     System.out.println("PG Test:");
/*  92 */     System.out.println("\t" + str1);
/*  93 */     PGgeometry pGgeometry = new PGgeometry(str1);
/*  94 */     System.out.println("\t" + pGgeometry.toString());
/*     */     
/*  96 */     System.out.println();
/*     */     
/*  98 */     System.out.println("finished");
/*     */     
/* 100 */     System.err.println("Test.java finished without errors.");
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/examples/Test.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
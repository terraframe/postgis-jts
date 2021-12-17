/*     */ package examples;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.postgis.Version;
/*     */ import org.postgresql.Driver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VersionPrinter
/*     */ {
/*  41 */   public static String[] GISVERSIONS = new String[] { "postgis_version", "postgis_proj_version", "postgis_scripts_installed", "postgis_lib_version", "postgis_scripts_released", "postgis_uses_stats", "postgis_geos_version", "postgis_scripts_build_date", "postgis_lib_build_date", "postgis_full_version" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     Driver driver;
/*  54 */     Statement statement = null;
/*     */ 
/*     */ 
/*     */     
/*  58 */     printHeading("PostGIS jdbc client code");
/*  59 */     printVersionString("getFullVersion", Version.getFullVersion());
/*     */ 
/*     */     
/*  62 */     printHeading("PGJDBC Driver");
/*  63 */     printVersionString("getVersion", Driver.getVersion());
/*     */     try {
/*  65 */       driver = new Driver();
/*  66 */     } catch (Exception exception) {
/*  67 */       System.err.println("Cannot create Driver instance: " + exception.getMessage());
/*  68 */       System.exit(1);
/*     */       return;
/*     */     } 
/*  71 */     printVersionString("getMajorVersion", driver.getMajorVersion());
/*  72 */     printVersionString("getMinorVersion", driver.getMinorVersion());
/*     */ 
/*     */     
/*  75 */     if (paramArrayOfString.length == 3) {
/*  76 */       Connection connection = null;
/*     */       try {
/*  78 */         connection = DriverManager.getConnection(paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2]);
/*  79 */         statement = connection.createStatement();
/*  80 */       } catch (SQLException sQLException) {
/*  81 */         System.err.println("Connection to database failed, aborting.");
/*  82 */         System.err.println(sQLException.getMessage());
/*  83 */         System.exit(1);
/*     */       } 
/*  85 */     } else if (paramArrayOfString.length != 0) {
/*  86 */       System.err.println("Usage: java examples/VersionPrinter dburl user pass");
/*  87 */       System.exit(1);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  92 */     if (statement == null) {
/*  93 */       System.out.println("No online version available.");
/*     */     }
/*     */     
/*  96 */     printHeading("PostgreSQL Server");
/*  97 */     printVersionString("version", statement);
/*     */ 
/*     */     
/* 100 */     printHeading("PostGIS Server");
/* 101 */     for (byte b = 0; b < GISVERSIONS.length; b++) {
/* 102 */       printVersionString(GISVERSIONS[b], statement);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean makeemptyline = false;
/*     */   
/*     */   private static void printHeading(String paramString) {
/* 110 */     if (makeemptyline) {
/* 111 */       System.out.println();
/*     */     }
/* 113 */     System.out.println("** " + paramString + " **");
/* 114 */     makeemptyline = true;
/*     */   }
/*     */   
/*     */   public static void printVersionString(String paramString, int paramInt) {
/* 118 */     printVersionString(paramString, Integer.toString(paramInt));
/*     */   }
/*     */   
/*     */   public static void printVersionString(String paramString1, String paramString2) {
/* 122 */     System.out.println("\t" + paramString1 + ": " + paramString2);
/*     */   }
/*     */   
/*     */   public static void printVersionString(String paramString, Statement paramStatement) {
/* 126 */     printVersionString(paramString, getVersionString(paramString, paramStatement));
/*     */   }
/*     */   
/*     */   public static String getVersionString(String paramString, Statement paramStatement) {
/*     */     try {
/* 131 */       ResultSet resultSet = paramStatement.executeQuery("SELECT " + paramString + "()");
/* 132 */       if (!resultSet.next()) {
/* 133 */         return "-- no result --";
/*     */       }
/* 135 */       String str = resultSet.getString(1);
/* 136 */       if (str == null) {
/* 137 */         return "-- null result --";
/*     */       }
/* 139 */       return str.trim();
/* 140 */     } catch (SQLException sQLException) {
/* 141 */       return "-- unavailable -- ";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/examples/VersionPrinter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
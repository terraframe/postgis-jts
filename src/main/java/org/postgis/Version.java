/*     */ package org.postgis;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Version
/*     */ {
/*     */   private static final String RESSOURCENAME = "org/postgis/version.properties";
/*     */   public static final int MAJOR;
/*     */   public static final int MINOR;
/*     */   public static final String MICRO;
/*     */   
/*     */   static {
/*  48 */     int i = -1;
/*  49 */     int j = -1;
/*  50 */     String str = null;
/*     */     try {
/*  52 */       ClassLoader classLoader = Version.class.getClassLoader();
/*     */       
/*  54 */       Properties properties = new Properties();
/*     */       
/*     */       try {
/*  57 */         properties.load(classLoader.getResourceAsStream("org/postgis/version.properties"));
/*  58 */       } catch (IOException iOException) {
/*  59 */         throw new ExceptionInInitializerError("Error initializing PostGIS JDBC version! Cause: Ressource org/postgis/version.properties cannot be read! " + iOException.getMessage());
/*     */       }
/*  61 */       catch (NullPointerException nullPointerException) {
/*  62 */         throw new ExceptionInInitializerError("Error initializing PostGIS JDBC version! Cause: Ressource org/postgis/version.properties not found! " + nullPointerException.getMessage());
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/*  67 */         i = Integer.parseInt(properties.getProperty("REL_MAJOR_VERSION"));
/*  68 */       } catch (NullPointerException nullPointerException) {
/*  69 */         throw new ExceptionInInitializerError("Error initializing PostGIS JDBC version! Missing REL_MAJOR_VERSION! " + nullPointerException.getMessage());
/*     */       }
/*  71 */       catch (NumberFormatException numberFormatException) {
/*  72 */         throw new ExceptionInInitializerError("Error initializing PostGIS JDBC version! Error parsing REL_MAJOR_VERSION! " + numberFormatException.getMessage());
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/*  77 */         j = Integer.parseInt(properties.getProperty("REL_MINOR_VERSION"));
/*  78 */       } catch (NullPointerException nullPointerException) {
/*  79 */         throw new ExceptionInInitializerError("Error initializing PostGIS JDBC version! Missing REL_MINOR_VERSION! " + nullPointerException.getMessage());
/*     */       }
/*  81 */       catch (NumberFormatException numberFormatException) {
/*  82 */         throw new ExceptionInInitializerError("Error initializing PostGIS JDBC version! Error parsing REL_MINOR_VERSION! " + numberFormatException.getMessage());
/*     */       } 
/*     */ 
/*     */       
/*  86 */       str = properties.getProperty("REL_MICRO_VERSION");
/*  87 */       if (str == null) {
/*  88 */         throw new ExceptionInInitializerError("Error initializing PostGIS JDBC version! Missing REL_MICRO_VERSION! ");
/*     */       }
/*     */     } finally {
/*     */       
/*  92 */       MAJOR = i;
/*  93 */       MINOR = j;
/*  94 */       MICRO = str;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  99 */   public static final String FULL = "PostGIS JDBC V" + MAJOR + "." + MINOR + "." + MICRO;
/*     */   
/*     */   public static void main(String[] paramArrayOfString) {
/* 102 */     System.out.println(FULL);
/*     */   }
/*     */   
/*     */   public static String getFullVersion() {
/* 106 */     return FULL;
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/Version.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
/*     */ package org.postgis;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.logging.Level;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DriverWrapperAutoprobe
/*     */   extends DriverWrapper
/*     */ {
/*     */   public static final String POSTGIS_AUTOPROTOCOL = "jdbc:postgresql_autogis:";
/*     */   public static final String REVISIONAUTO = "$Revision: 2570 $";
/*     */   
/*     */   static {
/*     */     try {
/*  69 */       DriverManager.registerDriver((Driver)new DriverWrapperAutoprobe());
/*  70 */     } catch (SQLException sQLException) {
/*  71 */       logger.log(Level.WARNING, "Error registering PostGIS LW Wrapper Driver", sQLException);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getProtoString() {
/*  76 */     return "jdbc:postgresql_autogis:";
/*     */   }
/*     */   
/*     */   protected boolean useLW(Connection paramConnection) {
/*     */     try {
/*  81 */       return supportsEWKB(paramConnection);
/*  82 */     } catch (SQLException sQLException) {
/*     */       
/*  84 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/*  92 */     return "PostGisWrapperAutoprobe $Revision: 2570 $, wrapping " + Driver.getVersion();
/*     */   }
/*     */   
/*     */   public static boolean supportsEWKB(Connection paramConnection) throws SQLException {
/*  96 */     Statement statement = paramConnection.createStatement();
/*  97 */     ResultSet resultSet = statement.executeQuery("SELECT postgis_version()");
/*  98 */     resultSet.next();
/*  99 */     String str = resultSet.getString(1);
/* 100 */     resultSet.close();
/* 101 */     statement.close();
/* 102 */     if (str == null) {
/* 103 */       throw new SQLException("postgis_version returned NULL!");
/*     */     }
/* 105 */     str = str.trim();
/* 106 */     int i = str.indexOf('.');
/* 107 */     int j = Integer.parseInt(str.substring(0, i));
/* 108 */     return (j >= 1);
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/DriverWrapperAutoprobe.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
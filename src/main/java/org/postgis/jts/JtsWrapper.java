/*     */ package org.postgis.jts;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.PGConnection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JtsWrapper
/*     */   extends Driver
/*     */ {
/*  60 */   protected static final Logger logger = Logger.getLogger("org.postgis.DriverWrapper");
/*     */ 
/*     */   
/*     */   private static final String POSTGRES_PROTOCOL = "jdbc:postgresql:";
/*     */ 
/*     */   
/*     */   private static final String POSTGIS_PROTOCOL = "jdbc:postgres_jts:";
/*     */   
/*     */   public static final String REVISION = "$Revision: 2570 $";
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/*  73 */       DriverManager.registerDriver((Driver)new JtsWrapper());
/*  74 */     } catch (SQLException sQLException) {
/*  75 */       logger.log(Level.WARNING, "Error registering PostgreSQL Jts Wrapper Driver", sQLException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection connect(String paramString, Properties paramProperties) throws SQLException {
/*  92 */     paramString = mangleURL(paramString);
/*  93 */     Connection connection = super.connect(paramString, paramProperties);
/*  94 */     addGISTypes((PGConnection)connection);
/*  95 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addGISTypes(PGConnection paramPGConnection) throws SQLException {
/* 103 */     paramPGConnection.addDataType("geometry", JtsGeometry.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String mangleURL(String paramString) throws SQLException {
/* 110 */     if (paramString.startsWith("jdbc:postgres_jts:")) {
/* 111 */       return "jdbc:postgresql:" + paramString.substring("jdbc:postgres_jts:".length());
/*     */     }
/* 113 */     throw new SQLException("Unknown protocol or subprotocol in url " + paramString);
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
/*     */   public boolean acceptsURL(String paramString) throws SQLException {
/*     */     try {
/* 128 */       paramString = mangleURL(paramString);
/* 129 */     } catch (SQLException sQLException) {
/* 130 */       return false;
/*     */     } 
/* 132 */     return super.acceptsURL(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMajorVersion() {
/* 142 */     return super.getMajorVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinorVersion() {
/* 151 */     return super.getMinorVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 158 */     return "JtsGisWrapper $Revision: 2570 $, wrapping " + Driver.getVersion();
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/jts/JtsWrapper.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
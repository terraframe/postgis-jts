/*     */ package org.postgis.jts;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import org.postgis.PGbox2d;
/*     */ import org.postgis.PGbox3d;
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
/*     */ public class JtsGisWrapper
/*     */   extends Driver
/*     */ {
/*     */   private static final String POSTGRES_PROTOCOL = "jdbc:postgresql:";
/*     */   private static final String POSTGIS_PROTOCOL = "jdbc:postgresql_JTS:";
/*     */   public static final String REVISION = "$Revision: 1977 $";
/*     */   
/*     */   static {
/*     */     try {
/*  62 */       DriverManager.registerDriver((Driver)new JtsGisWrapper());
/*  63 */     } catch (SQLException sQLException) {
/*  64 */       sQLException.printStackTrace();
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
/*  81 */     paramString = mangleURL(paramString);
/*  82 */     Connection connection = super.connect(paramString, paramProperties);
/*  83 */     addGISTypes((PGConnection)connection);
/*  84 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addGISTypes(PGConnection paramPGConnection) throws SQLException {
/*  93 */     paramPGConnection.addDataType("geometry", JtsGeometry.class);
/*  94 */     paramPGConnection.addDataType("box3d", PGbox3d.class);
/*  95 */     paramPGConnection.addDataType("box2d", PGbox2d.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String mangleURL(String paramString) throws SQLException {
/* 102 */     if (paramString.startsWith("jdbc:postgresql_JTS:")) {
/* 103 */       return "jdbc:postgresql:" + paramString.substring("jdbc:postgresql_JTS:".length());
/*     */     }
/* 105 */     throw new SQLException("Unknown protocol or subprotocol in url " + paramString);
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
/*     */   public boolean acceptsURL(String paramString) throws SQLException {
/*     */     try {
/* 123 */       paramString = mangleURL(paramString);
/* 124 */     } catch (SQLException sQLException) {
/* 125 */       return false;
/*     */     } 
/* 127 */     return super.acceptsURL(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMajorVersion() {
/* 137 */     return super.getMajorVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinorVersion() {
/* 146 */     return super.getMinorVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 153 */     return "JtsGisWrapper $Revision: 1977 $, wrapping " + Driver.getVersion();
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/jts/JtsGisWrapper.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
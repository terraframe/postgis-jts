/*    */ package org.postgis;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.Driver;
/*    */ import java.sql.DriverManager;
/*    */ import java.sql.SQLException;
/*    */ import java.util.logging.Level;
/*    */ import org.postgresql.Driver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DriverWrapperLW
/*    */   extends DriverWrapper
/*    */ {
/*    */   public static final String POSTGIS_LWPROTOCOL = "jdbc:postgresql_lwgis:";
/*    */   public static final String REVISIONLW = "$Revision: 2570 $";
/*    */   
/*    */   static {
/*    */     try {
/* 64 */       DriverManager.registerDriver((Driver)new DriverWrapperLW());
/* 65 */     } catch (SQLException sQLException) {
/* 66 */       logger.log(Level.WARNING, "Error registering PostGIS LW Wrapper Driver", sQLException);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected String getProtoString() {
/* 71 */     return "jdbc:postgresql_lwgis:";
/*    */   }
/*    */   
/*    */   protected boolean useLW(Connection paramConnection) {
/* 75 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getVersion() {
/* 82 */     return "PostGisWrapperLW $Revision: 2570 $, wrapping " + Driver.getVersion();
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/DriverWrapperLW.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
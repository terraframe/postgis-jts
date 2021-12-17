/*     */ package org.postgis;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Driver;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Properties;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.postgresql.Connection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DriverWrapper
/*     */   extends Driver
/*     */ {
/*  74 */   protected static final Logger logger = Logger.getLogger("org.postgis.DriverWrapper");
/*     */   
/*     */   public static final String POSTGRES_PROTOCOL = "jdbc:postgresql:";
/*     */   public static final String POSTGIS_PROTOCOL = "jdbc:postgresql_postGIS:";
/*     */   public static final String REVISION = "$Revision: 2570 $";
/*  79 */   protected static TypesAdder ta72 = null;
/*  80 */   protected static TypesAdder ta74 = null;
/*  81 */   protected static TypesAdder ta80 = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class class$org$postgis$PGgeometry;
/*     */ 
/*     */ 
/*     */   
/*     */   static Class class$org$postgis$PGbox3d;
/*     */ 
/*     */ 
/*     */   
/*  94 */   protected TypesAdder typesAdder = getTypesAdder(this);
/*     */   public DriverWrapper() throws SQLException {
/*  96 */     if (getMajorVersion() > 8 || getMinorVersion() > 1)
/*  97 */       logger.fine(getClass().getName() + " loaded TypesAdder: " + this.typesAdder.getClass().getName()); 
/*     */   }
/*     */   static Class class$org$postgis$PGbox2d;
/*     */   static Class class$org$postgis$PGgeometryLW;
/*     */   
/*     */   protected static TypesAdder getTypesAdder(Driver paramDriver) throws SQLException {
/* 103 */     if (paramDriver.getMajorVersion() == 7) {
/* 104 */       if (paramDriver.getMinorVersion() >= 3) {
/* 105 */         if (ta74 == null) {
/* 106 */           ta74 = loadTypesAdder("74");
/*     */         }
/* 108 */         return ta74;
/*     */       } 
/* 110 */       if (ta72 == null) {
/* 111 */         ta72 = loadTypesAdder("72");
/*     */       }
/* 113 */       return ta72;
/*     */     } 
/*     */     
/* 116 */     if (ta80 == null) {
/* 117 */       ta80 = loadTypesAdder("80");
/*     */     }
/* 119 */     return ta80;
/*     */   }
/*     */ 
/*     */   
/*     */   private static TypesAdder loadTypesAdder(String paramString) throws SQLException {
/*     */     try {
/* 125 */       Class clazz = Class.forName("org.postgis.DriverWrapper$TypesAdder" + paramString);
/* 126 */       return (TypesAdder)clazz.newInstance();
/* 127 */     } catch (Exception exception) {
/* 128 */       throw new SQLException("Cannot create TypesAdder instance! " + exception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 135 */       DriverManager.registerDriver((Driver)new DriverWrapper());
/* 136 */     } catch (SQLException sQLException) {
/* 137 */       logger.log(Level.WARNING, "Error registering PostGIS Wrapper Driver", sQLException);
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
/* 154 */     paramString = mangleURL(paramString);
/* 155 */     Connection connection = super.connect(paramString, paramProperties);
/* 156 */     this.typesAdder.addGT(connection, useLW(connection));
/* 157 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean useLW(Connection paramConnection) {
/* 165 */     if (paramConnection == null) {
/* 166 */       throw new IllegalArgumentException("null is no valid parameter");
/*     */     }
/* 168 */     return false;
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
/*     */   public boolean acceptsURL(String paramString) throws SQLException {
/*     */     try {
/* 182 */       paramString = mangleURL(paramString);
/* 183 */     } catch (SQLException sQLException) {
/* 184 */       return false;
/*     */     } 
/* 186 */     return super.acceptsURL(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVersion() {
/* 193 */     return "PostGisWrapper $Revision: 2570 $, wrapping " + Driver.getVersion();
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
/*     */   
/*     */   public static void addGISTypes(PGConnection paramPGConnection) throws SQLException {
/* 212 */     loadTypesAdder("74").addGT((Connection)paramPGConnection, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addGISTypes80(PGConnection paramPGConnection) throws SQLException {
/* 219 */     loadTypesAdder("80").addGT((Connection)paramPGConnection, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addGISTypes72(Connection paramConnection) throws SQLException {
/* 228 */     loadTypesAdder("72").addGT((Connection)paramConnection, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String mangleURL(String paramString) throws SQLException {
/* 235 */     String str = getProtoString();
/* 236 */     if (paramString.startsWith(str)) {
/* 237 */       return "jdbc:postgresql:" + paramString.substring(str.length());
/*     */     }
/* 239 */     throw new SQLException("Unknown protocol or subprotocol in url " + paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getProtoString() {
/* 244 */     return "jdbc:postgresql_postGIS:";
/*     */   }
/*     */   
/*     */   protected static abstract class TypesAdder
/*     */   {
/*     */     public final void addGT(Connection param1Connection, boolean param1Boolean) throws SQLException {
/* 250 */       if (param1Boolean) {
/* 251 */         addBinaryGeometries(param1Connection);
/*     */       } else {
/* 253 */         addGeometries(param1Connection);
/*     */       } 
/* 255 */       addBoxen(param1Connection);
/*     */     }
/*     */     
/*     */     public abstract void addGeometries(Connection param1Connection) throws SQLException;
/*     */     
/*     */     public abstract void addBoxen(Connection param1Connection) throws SQLException;
/*     */     
/*     */     public abstract void addBinaryGeometries(Connection param1Connection) throws SQLException;
/*     */   }
/*     */   
/*     */   protected static final class TypesAdder74
/*     */     extends TypesAdder {
/*     */     public void addGeometries(Connection param1Connection) {
/* 268 */       PGConnection pGConnection = (PGConnection)param1Connection;
/* 269 */       pGConnection.addDataType("geometry", "org.postgis.PGgeometry");
/*     */     }
/*     */     
/*     */     public void addBoxen(Connection param1Connection) {
/* 273 */       PGConnection pGConnection = (PGConnection)param1Connection;
/* 274 */       pGConnection.addDataType("box3d", "org.postgis.PGbox3d");
/* 275 */       pGConnection.addDataType("box2d", "org.postgis.PGbox2d");
/*     */     }
/*     */     
/*     */     public void addBinaryGeometries(Connection param1Connection) {
/* 279 */       PGConnection pGConnection = (PGConnection)param1Connection;
/* 280 */       pGConnection.addDataType("geometry", "org.postgis.PGgeometryLW");
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class TypesAdder72
/*     */     extends TypesAdder {
/*     */     public void addGeometries(Connection param1Connection) {
/* 287 */       Connection connection = (Connection)param1Connection;
/* 288 */       connection.addDataType("geometry", "org.postgis.PGgeometry");
/*     */     }
/*     */     
/*     */     public void addBoxen(Connection param1Connection) {
/* 292 */       Connection connection = (Connection)param1Connection;
/* 293 */       connection.addDataType("box3d", "org.postgis.PGbox3d");
/* 294 */       connection.addDataType("box2d", "org.postgis.PGbox2d");
/*     */     }
/*     */     
/*     */     public void addBinaryGeometries(Connection param1Connection) {
/* 298 */       Connection connection = (Connection)param1Connection;
/* 299 */       connection.addDataType("geometry", "org.postgis.PGgeometryLW");
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class TypesAdder80
/*     */     extends TypesAdder {
/*     */     public void addGeometries(Connection param1Connection) throws SQLException {
/* 306 */       PGConnection pGConnection = (PGConnection)param1Connection;
/* 307 */       pGConnection.addDataType("geometry", (DriverWrapper.class$org$postgis$PGgeometry == null) ? (DriverWrapper.class$org$postgis$PGgeometry = DriverWrapper.class$("org.postgis.PGgeometry")) : DriverWrapper.class$org$postgis$PGgeometry);
/*     */     }
/*     */     
/*     */     public void addBoxen(Connection param1Connection) throws SQLException {
/* 311 */       PGConnection pGConnection = (PGConnection)param1Connection;
/* 312 */       pGConnection.addDataType("box3d", (DriverWrapper.class$org$postgis$PGbox3d == null) ? (DriverWrapper.class$org$postgis$PGbox3d = DriverWrapper.class$("org.postgis.PGbox3d")) : DriverWrapper.class$org$postgis$PGbox3d);
/* 313 */       pGConnection.addDataType("box2d", (DriverWrapper.class$org$postgis$PGbox2d == null) ? (DriverWrapper.class$org$postgis$PGbox2d = DriverWrapper.class$("org.postgis.PGbox2d")) : DriverWrapper.class$org$postgis$PGbox2d);
/*     */     }
/*     */     
/*     */     public void addBinaryGeometries(Connection param1Connection) throws SQLException {
/* 317 */       PGConnection pGConnection = (PGConnection)param1Connection;
/* 318 */       pGConnection.addDataType("geometry", (DriverWrapper.class$org$postgis$PGgeometryLW == null) ? (DriverWrapper.class$org$postgis$PGgeometryLW = DriverWrapper.class$("org.postgis.PGgeometryLW")) : DriverWrapper.class$org$postgis$PGgeometryLW);
/*     */     }
/*     */   }
/*     */   
/*     */   static Class class$(String paramString) {
/*     */     try {
/*     */       return Class.forName(paramString);
/*     */     } catch (ClassNotFoundException classNotFoundException) {
/*     */       throw new NoClassDefFoundError(classNotFoundException.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/DriverWrapper.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
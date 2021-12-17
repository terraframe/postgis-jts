/*     */ package examples;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.postgresql.Driver;
/*     */ import org.postgresql.util.PGobject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestAutoregister
/*     */ {
/*     */   public static void main(String[] paramArrayOfString) {
/*     */     int i;
/*  49 */     String str1 = null;
/*  50 */     String str2 = null;
/*  51 */     String str3 = null;
/*     */     
/*  53 */     if (paramArrayOfString.length == 3) {
/*  54 */       System.out.println("Testing proper auto-registration");
/*  55 */       str1 = paramArrayOfString[0];
/*  56 */       str2 = paramArrayOfString[1];
/*  57 */       str3 = paramArrayOfString[2];
/*     */     } else {
/*  59 */       System.err.println("Usage: java examples/TestParser dburl user pass");
/*  60 */       System.exit(1);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  65 */     System.out.println("Driver version: " + Driver.getVersion());
/*     */     
/*     */     try {
/*  68 */       i = (new Driver()).getMajorVersion();
/*  69 */     } catch (Exception exception) {
/*  70 */       System.err.println("Cannot create Driver instance: " + exception.getMessage());
/*  71 */       System.exit(1);
/*     */       
/*     */       return;
/*     */     } 
/*  75 */     if (i < 8) {
/*  76 */       System.err.println("Your pgdjbc " + i + ".X is too old, it does not support autoregistration!");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  81 */     System.out.println("Creating JDBC connection to " + str1);
/*  82 */     Connection connection = null;
/*  83 */     Statement statement = null;
/*     */     try {
/*  85 */       connection = DriverManager.getConnection(str1, str2, str3);
/*  86 */       statement = connection.createStatement();
/*  87 */     } catch (SQLException sQLException) {
/*  88 */       System.err.println("Connection initialization failed, aborting.");
/*  89 */       sQLException.printStackTrace();
/*  90 */       System.exit(1);
/*     */       
/*  92 */       throw new AssertionError();
/*     */     } 
/*     */     
/*  95 */     int j = 0;
/*     */     try {
/*  97 */       j = getPostgisMajor(statement);
/*  98 */     } catch (SQLException sQLException) {
/*  99 */       System.err.println("Error fetching PostGIS version: " + sQLException.getMessage());
/* 100 */       System.err.println("Is PostGIS really installed in the database?");
/* 101 */       System.exit(1);
/*     */       
/* 103 */       throw new AssertionError();
/*     */     } 
/*     */     
/* 106 */     System.out.println("PostGIS Version: " + j);
/*     */     
/* 108 */     PGobject pGobject = null;
/*     */ 
/*     */     
/*     */     try {
/* 112 */       ResultSet resultSet = statement.executeQuery("SELECT 'POINT(1 2)'::geometry");
/* 113 */       resultSet.next();
/* 114 */       pGobject = (PGobject)resultSet.getObject(1);
/* 115 */       if (pGobject instanceof org.postgis.PGgeometry) {
/* 116 */         System.out.println("PGgeometry successful!");
/*     */       } else {
/* 118 */         System.out.println("PGgeometry failed!");
/*     */       } 
/* 120 */     } catch (SQLException sQLException) {
/* 121 */       System.err.println("Selecting geometry failed: " + sQLException.getMessage());
/* 122 */       System.exit(1);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 129 */       ResultSet resultSet = statement.executeQuery("SELECT 'BOX3D(1 2 3, 4 5 6)'::box3d");
/* 130 */       resultSet.next();
/* 131 */       pGobject = (PGobject)resultSet.getObject(1);
/* 132 */       if (pGobject instanceof org.postgis.PGbox3d) {
/* 133 */         System.out.println("Box3d successful!");
/*     */       } else {
/* 135 */         System.out.println("Box3d failed!");
/*     */       } 
/* 137 */     } catch (SQLException sQLException) {
/* 138 */       System.err.println("Selecting box3d failed: " + sQLException.getMessage());
/* 139 */       System.exit(1);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 145 */     if (j < 1) {
/* 146 */       System.out.println("PostGIS version is too old, skipping box2ed test");
/* 147 */       System.err.println("PostGIS version is too old, skipping box2ed test");
/*     */     } else {
/*     */       try {
/* 150 */         ResultSet resultSet = statement.executeQuery("SELECT 'BOX(1 2,3 4)'::box2d");
/* 151 */         resultSet.next();
/* 152 */         pGobject = (PGobject)resultSet.getObject(1);
/* 153 */         if (pGobject instanceof org.postgis.PGbox2d) {
/* 154 */           System.out.println("Box2d successful!");
/*     */         } else {
/* 156 */           System.out.println("Box2d failed! " + pGobject.getClass().getName());
/*     */         } 
/* 158 */       } catch (SQLException sQLException) {
/* 159 */         System.err.println("Selecting box2d failed: " + sQLException.getMessage());
/* 160 */         System.exit(1);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     System.out.println("Finished.");
/*     */     
/* 168 */     System.err.println("TestAutoregister.java finished without errors.");
/*     */   }
/*     */   
/*     */   public static int getPostgisMajor(Statement paramStatement) throws SQLException {
/* 172 */     ResultSet resultSet = paramStatement.executeQuery("SELECT postgis_version()");
/* 173 */     resultSet.next();
/* 174 */     String str = resultSet.getString(1);
/* 175 */     if (str == null) {
/* 176 */       throw new SQLException("postgis_version returned NULL!");
/*     */     }
/* 178 */     str = str.trim();
/* 179 */     int i = str.indexOf('.');
/* 180 */     return Integer.parseInt(str.substring(0, i));
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/examples/TestAutoregister.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
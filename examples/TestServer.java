/*     */ package examples;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import org.postgresql.Connection;
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
/*     */ public class TestServer
/*     */ {
/*     */   public static void main(String[] paramArrayOfString) {
/*  37 */     if (paramArrayOfString.length != 4 && paramArrayOfString.length != 3) {
/*  38 */       System.err.println("Usage: java examples/TestServer dburl user pass [tablename]");
/*  39 */       System.err.println();
/*  40 */       System.err.println("dburl has the following format:");
/*  41 */       System.err.println("jdbc:postgresql://HOST:PORT/DATABASENAME");
/*  42 */       System.err.println("tablename is 'jdbc_test' by default.");
/*  43 */       System.exit(1);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  48 */     String str1 = paramArrayOfString[0];
/*  49 */     String str2 = paramArrayOfString[1];
/*  50 */     String str3 = paramArrayOfString[2];
/*     */     
/*  52 */     String str4 = "jdbc_test";
/*     */     
/*  54 */     String str5 = "drop table " + str4;
/*  55 */     String str6 = "create table " + str4 + " (geom geometry, id int4)";
/*  56 */     String str7 = "insert into " + str4 + " values ('POINT (10 10 10)',1)";
/*  57 */     String str8 = "insert into " + str4 + " values ('POLYGON ((0 0 0,0 10 0,10 10 0,10 0 0,0 0 0))',2)";
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  62 */       System.out.println("Creating JDBC connection...");
/*  63 */       Class.forName("org.postgresql.Driver");
/*  64 */       Connection connection = DriverManager.getConnection(str1, str2, str3);
/*  65 */       System.out.println("Adding geometric type entries...");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       if (connection.getClass().getName().equals("org.postgresql.jdbc2.Connection")) {
/*  78 */         ((Connection)connection).addDataType("geometry", "org.postgis.PGgeometry");
/*  79 */         ((Connection)connection).addDataType("box3d", "org.postgis.PGbox3d");
/*     */       } else {
/*  81 */         ((PGConnection)connection).addDataType("geometry", "org.postgis.PGgeometry");
/*     */         
/*  83 */         ((PGConnection)connection).addDataType("box3d", "org.postgis.PGbox3d");
/*     */       } 
/*  85 */       Statement statement = connection.createStatement();
/*  86 */       System.out.println("Creating table with geometric types...");
/*     */       
/*     */       try {
/*  89 */         statement.execute(str5);
/*  90 */       } catch (Exception exception) {
/*  91 */         System.out.println("Error dropping old table: " + exception.getMessage());
/*     */       } 
/*  93 */       statement.execute(str6);
/*  94 */       System.out.println("Inserting point...");
/*  95 */       statement.execute(str7);
/*  96 */       System.out.println("Inserting polygon...");
/*  97 */       statement.execute(str8);
/*  98 */       System.out.println("Done.");
/*  99 */       statement = connection.createStatement();
/* 100 */       System.out.println("Querying table...");
/* 101 */       ResultSet resultSet = statement.executeQuery("select asText(geom),id from " + str4);
/* 102 */       while (resultSet.next()) {
/* 103 */         Object object = resultSet.getObject(1);
/* 104 */         int i = resultSet.getInt(2);
/* 105 */         System.out.println("Row " + i + ":");
/* 106 */         System.out.println(object.toString());
/*     */       } 
/* 108 */       statement.close();
/* 109 */       connection.close();
/* 110 */     } catch (Exception exception) {
/* 111 */       System.err.println("Aborted due to error:");
/* 112 */       exception.printStackTrace();
/* 113 */       System.exit(1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/examples/TestServer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
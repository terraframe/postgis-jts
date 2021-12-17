/*     */ package examples;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import org.postgis.PGbox2d;
/*     */ import org.postgis.PGbox3d;
/*     */ import org.postgresql.util.PGobject;
/*     */ import org.postgresql.util.PGtokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestBoxes
/*     */ {
/*  43 */   public static final String[] BOXEN3D = new String[] { "BOX3D(1 2 3,4 5 6)", "BOX3D(1 2,4 5)" };
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static final String[] BOXEN2D = new String[] { "BOX(1 2,3 4)" };
/*     */ 
/*     */   
/*     */   public static final int SRID = 4326;
/*     */ 
/*     */   
/*     */   public static final String SRIDPREFIX = "SRID=4326;";
/*     */ 
/*     */   
/*  56 */   public static int failcount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void test(String paramString, PGobject paramPGobject, Connection[] paramArrayOfConnection, boolean paramBoolean) throws SQLException {
/*     */     PGobject pGobject;
/*  64 */     System.out.println("Original:  " + paramString);
/*  65 */     String str1 = paramPGobject.toString();
/*  66 */     System.out.println("Parsed:    " + str1);
/*     */     
/*  68 */     if (!paramString.equals(str1)) {
/*  69 */       System.out.println("--- Recreated Text Rep not equal!");
/*  70 */       failcount++;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  76 */       pGobject = (PGobject)paramPGobject.getClass().newInstance();
/*  77 */     } catch (Exception exception) {
/*  78 */       System.out.println("--- pgjdbc instantiation failed!");
/*  79 */       System.out.println("--- " + exception.getMessage());
/*  80 */       failcount++;
/*     */       return;
/*     */     } 
/*  83 */     pGobject.setValue(str1);
/*     */     
/*  85 */     String str2 = pGobject.toString();
/*  86 */     System.out.println("Re-Parsed: " + str2);
/*  87 */     if (!pGobject.equals(paramPGobject)) {
/*  88 */       System.out.println("--- Recreated boxen are not equal!");
/*  89 */       failcount++;
/*  90 */     } else if (!str2.equals(paramString)) {
/*  91 */       System.out.println("--- 2nd generation text reps are not equal!");
/*  92 */       failcount++;
/*     */     } else {
/*  94 */       System.out.println("Equals:    yes");
/*     */     } 
/*     */     
/*  97 */     for (byte b = 0; b < paramArrayOfConnection.length; b++) {
/*  98 */       System.out.println("Testing on connection " + b + ": " + paramArrayOfConnection[b].getCatalog());
/*  99 */       Statement statement = paramArrayOfConnection[b].createStatement();
/* 100 */       if (paramBoolean && TestAutoregister.getPostgisMajor(statement) < 1) {
/* 101 */         System.out.println("PostGIS version is too old, not testing box2d");
/*     */       } else {
/*     */         
/*     */         try {
/* 105 */           PGobject pGobject1 = viaSQL(paramPGobject, statement);
/* 106 */           System.out.println("SQLin    : " + pGobject1.toString());
/* 107 */           if (!paramPGobject.equals(pGobject1)) {
/* 108 */             System.out.println("--- Geometries after SQL are not equal!");
/* 109 */             failcount++;
/*     */           } else {
/* 111 */             System.out.println("Eq SQL in: yes");
/*     */           } 
/* 113 */         } catch (SQLException sQLException) {
/* 114 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 115 */           failcount++;
/*     */         } 
/*     */         
/*     */         try {
/* 119 */           PGobject pGobject1 = viaSQL(pGobject, statement);
/* 120 */           System.out.println("SQLout  :  " + pGobject1.toString());
/* 121 */           if (!paramPGobject.equals(pGobject1)) {
/* 122 */             System.out.println("--- reparsed Geometries after SQL are not equal!");
/* 123 */             failcount++;
/*     */           } else {
/* 125 */             System.out.println("Eq SQLout: yes");
/*     */           } 
/* 127 */         } catch (SQLException sQLException) {
/* 128 */           System.out.println("--- Server side error: " + sQLException.toString());
/* 129 */           failcount++;
/*     */         } 
/*     */       } 
/* 132 */       statement.close();
/*     */     } 
/* 134 */     System.out.println("***");
/*     */   }
/*     */ 
/*     */   
/*     */   private static PGobject viaSQL(PGobject paramPGobject, Statement paramStatement) throws SQLException {
/* 139 */     ResultSet resultSet = paramStatement.executeQuery("SELECT '" + paramPGobject.toString() + "'::" + paramPGobject.getType());
/* 140 */     resultSet.next();
/* 141 */     return (PGobject)resultSet.getObject(1);
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
/*     */   public static Connection connect(String paramString1, String paramString2, String paramString3) throws SQLException, ClassNotFoundException {
/* 160 */     Class.forName("org.postgis.DriverWrapper");
/* 161 */     return DriverManager.getConnection(paramString1, paramString2, paramString3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws SQLException, ClassNotFoundException {
/*     */     PGtokenizer pGtokenizer;
/* 168 */     String str1 = null;
/* 169 */     String str2 = null;
/*     */     
/* 171 */     if (paramArrayOfString.length == 1 && paramArrayOfString[0].equalsIgnoreCase("offline")) {
/* 172 */       System.out.println("Performing only offline tests");
/* 173 */       pGtokenizer = new PGtokenizer("", ';');
/* 174 */     } else if (paramArrayOfString.length == 3) {
/* 175 */       System.out.println("Performing offline and online tests");
/*     */       
/* 177 */       pGtokenizer = new PGtokenizer(paramArrayOfString[0], ';');
/* 178 */       str1 = paramArrayOfString[1];
/* 179 */       str2 = paramArrayOfString[2];
/*     */     } else {
/* 181 */       System.err.println("Usage: java examples/TestParser dburls user pass [tablename]");
/* 182 */       System.err.println("   or: java examples/TestParser offline");
/* 183 */       System.err.println();
/* 184 */       System.err.println("dburls has one or more jdbc urls separated by ; in the following format");
/* 185 */       System.err.println("jdbc:postgresql://HOST:PORT/DATABASENAME");
/* 186 */       System.err.println("tablename is 'jdbc_test' by default.");
/* 187 */       System.exit(1);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 193 */     Connection[] arrayOfConnection = new Connection[pGtokenizer.getSize()]; byte b;
/* 194 */     for (b = 0; b < pGtokenizer.getSize(); b++) {
/* 195 */       System.out.println("Creating JDBC connection to " + pGtokenizer.getToken(b));
/* 196 */       arrayOfConnection[b] = connect(pGtokenizer.getToken(b), str1, str2);
/*     */     } 
/*     */     
/* 199 */     System.out.println("Performing tests...");
/* 200 */     System.out.println("***");
/*     */     
/* 202 */     for (b = 0; b < BOXEN3D.length; b++) {
/*     */       try {
/* 204 */         PGbox3d pGbox3d = new PGbox3d(BOXEN3D[b]);
/* 205 */         test(BOXEN3D[b], (PGobject)pGbox3d, arrayOfConnection, false);
/* 206 */       } catch (SQLException sQLException) {
/* 207 */         System.out.println("--- Instantiation of " + BOXEN3D[b] + "failed:");
/* 208 */         System.out.println("--- " + sQLException.getMessage());
/* 209 */         failcount++;
/*     */       } 
/*     */     } 
/*     */     
/* 213 */     for (b = 0; b < BOXEN2D.length; b++) {
/*     */       try {
/* 215 */         PGbox2d pGbox2d = new PGbox2d(BOXEN2D[b]);
/* 216 */         test(BOXEN2D[b], (PGobject)pGbox2d, arrayOfConnection, true);
/* 217 */       } catch (SQLException sQLException) {
/* 218 */         System.out.println("--- Instantiation of " + BOXEN2D[b] + "failed:");
/* 219 */         System.out.println("--- " + sQLException.getMessage());
/* 220 */         failcount++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 225 */     System.out.print("cleaning up...");
/* 226 */     for (b = 0; b < arrayOfConnection.length; b++) {
/* 227 */       arrayOfConnection[b].close();
/*     */     }
/*     */     
/* 230 */     System.out.println("Finished, " + failcount + " tests failed!");
/* 231 */     System.err.println("Finished, " + failcount + " tests failed!");
/* 232 */     System.exit(failcount);
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/examples/TestBoxes.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
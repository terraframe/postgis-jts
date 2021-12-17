/*    */ package org.postgis;
/*    */ 
/*    */ import java.sql.SQLException;
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
/*    */ public class PGbox3d
/*    */   extends PGboxbase
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/*    */   
/*    */   public PGbox3d() {}
/*    */   
/*    */   public PGbox3d(Point paramPoint1, Point paramPoint2) {
/* 41 */     super(paramPoint1, paramPoint2);
/*    */   }
/*    */   
/*    */   public PGbox3d(String paramString) throws SQLException {
/* 45 */     super(paramString);
/*    */   }
/*    */   
/*    */   public String getPrefix() {
/* 49 */     return "BOX3D";
/*    */   }
/*    */   
/*    */   public String getPGtype() {
/* 53 */     return "box3d";
/*    */   }
/*    */   
/*    */   protected PGboxbase newInstance() {
/* 57 */     return new PGbox3d();
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/PGbox3d.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
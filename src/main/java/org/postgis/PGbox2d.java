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
/*    */ public class PGbox2d
/*    */   extends PGboxbase
/*    */ {
/*    */   private static final long serialVersionUID = 256L;
/*    */   
/*    */   public PGbox2d() {}
/*    */   
/*    */   public PGbox2d(Point paramPoint1, Point paramPoint2) {
/* 40 */     super(paramPoint1, paramPoint2);
/*    */   }
/*    */   
/*    */   public PGbox2d(String paramString) throws SQLException {
/* 44 */     super(paramString);
/*    */   }
/*    */   
/*    */   public void setValue(String paramString) throws SQLException {
/* 48 */     super.setValue(paramString);
/*    */     
/* 50 */     if (this.llb.dimension != 2 || this.urt.dimension != 2) {
/* 51 */       throw new SQLException("PGbox2d is only allowed to have 2 dimensions!");
/*    */     }
/*    */   }
/*    */   
/*    */   public String getPrefix() {
/* 56 */     return "BOX";
/*    */   }
/*    */   
/*    */   public String getPGtype() {
/* 60 */     return "box2d";
/*    */   }
/*    */   
/*    */   protected PGboxbase newInstance() {
/* 64 */     return new PGbox2d();
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/PGbox2d.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
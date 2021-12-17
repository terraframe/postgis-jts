/*     */ package org.postgis;
/*     */ 
/*     */ import java.sql.SQLException;
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
/*     */ public abstract class PGboxbase
/*     */   extends PGobject
/*     */ {
/*     */   private static final long serialVersionUID = 256L;
/*     */   protected Point llb;
/*     */   protected Point urt;
/*     */   
/*     */   public abstract String getPrefix();
/*     */   
/*     */   public abstract String getPGtype();
/*     */   
/*     */   public PGboxbase() {
/*  69 */     setType(getPGtype());
/*     */   }
/*     */   
/*     */   public PGboxbase(Point paramPoint1, Point paramPoint2) {
/*  73 */     this();
/*  74 */     this.llb = paramPoint1;
/*  75 */     this.urt = paramPoint2;
/*     */   }
/*     */   
/*     */   public PGboxbase(String paramString) throws SQLException {
/*  79 */     this();
/*  80 */     setValue(paramString);
/*     */   }
/*     */   
/*     */   public void setValue(String paramString) throws SQLException {
/*  84 */     int i = -1;
/*  85 */     paramString = paramString.trim();
/*  86 */     if (paramString.startsWith("SRID=")) {
/*  87 */       String[] arrayOfString = PGgeometry.splitSRID(paramString);
/*  88 */       paramString = arrayOfString[1].trim();
/*  89 */       i = Integer.parseInt(arrayOfString[0].substring(5));
/*     */     } 
/*  91 */     String str = getPrefix();
/*  92 */     if (paramString.startsWith(str)) {
/*  93 */       paramString = paramString.substring(str.length()).trim();
/*     */     }
/*  95 */     PGtokenizer pGtokenizer = new PGtokenizer(PGtokenizer.removePara(paramString), ',');
/*  96 */     this.llb = new Point(pGtokenizer.getToken(0));
/*  97 */     this.urt = new Point(pGtokenizer.getToken(1));
/*  98 */     if (i != -1) {
/*  99 */       this.llb.setSrid(i);
/* 100 */       this.urt.setSrid(i);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 105 */     StringBuffer stringBuffer = new StringBuffer();
/* 106 */     outerWKT(stringBuffer);
/* 107 */     return stringBuffer.toString();
/*     */   }
/*     */   
/*     */   private void outerWKT(StringBuffer paramStringBuffer) {
/* 111 */     paramStringBuffer.append(getPrefix());
/* 112 */     paramStringBuffer.append('(');
/* 113 */     this.llb.innerWKT(paramStringBuffer);
/* 114 */     paramStringBuffer.append(',');
/* 115 */     this.urt.innerWKT(paramStringBuffer);
/* 116 */     paramStringBuffer.append(')');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getLLB() {
/* 129 */     return this.llb;
/*     */   }
/*     */ 
/*     */   
/*     */   public Point getURT() {
/* 134 */     return this.urt;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 138 */     if (paramObject instanceof PGboxbase) {
/* 139 */       PGboxbase pGboxbase = (PGboxbase)paramObject;
/* 140 */       return (compareLazyDim(this.llb, pGboxbase.llb) && compareLazyDim(this.urt, pGboxbase.urt));
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean compareLazyDim(Point paramPoint1, Point paramPoint2) {
/* 153 */     return (paramPoint1.x == paramPoint2.x && paramPoint1.y == paramPoint2.y && (((paramPoint1.dimension == 2 || paramPoint1.z == 0.0D) && (paramPoint2.dimension == 2 || paramPoint2.z == 0.0D)) || paramPoint1.z == paramPoint2.z));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 159 */     PGboxbase pGboxbase = newInstance();
/* 160 */     pGboxbase.llb = this.llb;
/* 161 */     pGboxbase.urt = this.urt;
/* 162 */     pGboxbase.setType(this.type);
/* 163 */     return pGboxbase;
/*     */   }
/*     */   
/*     */   protected abstract PGboxbase newInstance();
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/PGboxbase.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
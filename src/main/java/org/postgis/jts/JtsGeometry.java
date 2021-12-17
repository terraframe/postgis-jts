/*     */ package org.postgis.jts;
/*     */ 
/*     */ import com.vividsolutions.jts.geom.CoordinateSequenceFactory;
/*     */ import com.vividsolutions.jts.geom.Geometry;
/*     */ import com.vividsolutions.jts.geom.GeometryFactory;
/*     */ import com.vividsolutions.jts.geom.Polygon;
/*     */ import com.vividsolutions.jts.geom.PrecisionModel;
/*     */ import com.vividsolutions.jts.geom.impl.PackedCoordinateSequenceFactory;
/*     */ import com.vividsolutions.jts.io.WKTReader;
/*     */ import java.sql.SQLException;
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
/*     */ 
/*     */ 
/*     */ public class JtsGeometry
/*     */   extends PGobject
/*     */ {
/*     */   private static final long serialVersionUID = 256L;
/*     */   Geometry geom;
/*  54 */   static final JtsBinaryParser bp = new JtsBinaryParser();
/*     */   
/*  56 */   static final JtsBinaryWriter bw = new JtsBinaryWriter();
/*     */   
/*  58 */   static final PrecisionModel prec = new PrecisionModel();
/*     */   
/*  60 */   static final CoordinateSequenceFactory csfac = (CoordinateSequenceFactory)PackedCoordinateSequenceFactory.DOUBLE_FACTORY;
/*     */   
/*  62 */   static final GeometryFactory geofac = new GeometryFactory(prec, 0, csfac);
/*     */   
/*  64 */   static final WKTReader reader = new WKTReader(geofac);
/*     */ 
/*     */   
/*     */   public JtsGeometry() {
/*  68 */     setType("geometry");
/*     */   }
/*     */   
/*     */   public JtsGeometry(Geometry paramGeometry) {
/*  72 */     this();
/*  73 */     this.geom = paramGeometry;
/*     */   }
/*     */   
/*     */   public JtsGeometry(String paramString) throws SQLException {
/*  77 */     this();
/*  78 */     setValue(paramString);
/*     */   }
/*     */   
/*     */   public void setValue(String paramString) throws SQLException {
/*  82 */     this.geom = geomFromString(paramString);
/*     */   }
/*     */   
/*     */   public static Geometry geomFromString(String paramString) throws SQLException {
/*     */     try {
/*  87 */       paramString = paramString.trim();
/*  88 */       if (paramString.startsWith("00") || paramString.startsWith("01")) {
/*  89 */         return bp.parse(paramString);
/*     */       }
/*     */ 
/*     */       
/*  93 */       int i = 0;
/*     */       
/*  95 */       if (paramString.startsWith("SRID=")) {
/*  96 */         String[] arrayOfString = paramString.split(";");
/*  97 */         paramString = arrayOfString[1].trim();
/*  98 */         i = Integer.parseInt(arrayOfString[0].substring(5));
/*     */       } 
/*     */       
/* 101 */       Geometry geometry = reader.read(paramString);
/* 102 */       setSridRecurse(geometry, i);
/* 103 */       return geometry;
/*     */     }
/* 105 */     catch (Exception exception) {
/* 106 */       exception.printStackTrace();
/* 107 */       throw new SQLException("Error parsing SQL data:" + exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setSridRecurse(Geometry paramGeometry, int paramInt) {
/* 113 */     paramGeometry.setSRID(paramInt);
/* 114 */     if (paramGeometry instanceof com.vividsolutions.jts.geom.GeometryCollection) {
/* 115 */       int i = paramGeometry.getNumGeometries();
/* 116 */       for (byte b = 0; b < i; b++) {
/* 117 */         setSridRecurse(paramGeometry.getGeometryN(b), paramInt);
/*     */       }
/* 119 */     } else if (paramGeometry instanceof Polygon) {
/* 120 */       Polygon polygon = (Polygon)paramGeometry;
/* 121 */       polygon.getExteriorRing().setSRID(paramInt);
/* 122 */       int i = polygon.getNumInteriorRing();
/* 123 */       for (byte b = 0; b < i; b++) {
/* 124 */         polygon.getInteriorRingN(b).setSRID(paramInt);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Geometry getGeometry() {
/* 130 */     return this.geom;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 134 */     return this.geom.toString();
/*     */   }
/*     */   
/*     */   public String getValue() {
/* 138 */     return bw.writeHexed(getGeometry());
/*     */   }
/*     */   
/*     */   public Object clone() {
/* 142 */     JtsGeometry jtsGeometry = new JtsGeometry(this.geom);
/* 143 */     jtsGeometry.setType(this.type);
/* 144 */     return jtsGeometry;
/*     */   }
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 148 */     if (paramObject != null && paramObject instanceof JtsGeometry) {
/* 149 */       Geometry geometry = ((JtsGeometry)paramObject).geom;
/* 150 */       if (this.geom == geometry)
/*     */       {
/* 152 */         return true; } 
/* 153 */       if (this.geom != null && geometry != null) {
/* 154 */         return geometry.equals(this.geom);
/*     */       }
/*     */     } 
/* 157 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/jts/JtsGeometry.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
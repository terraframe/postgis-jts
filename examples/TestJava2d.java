/*     */ package examples;
/*     */ 
/*     */ import java.awt.Canvas;
/*     */ import java.awt.Color;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Shape;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.awt.event.WindowListener;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import org.postgis.java2d.Java2DWrapper;
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
/*     */ public class TestJava2d
/*     */ {
/*     */   private static final boolean DEBUG = true;
/*  45 */   public static final Shape[] SHAPEARRAY = new Shape[0];
/*     */   
/*     */   static {
/*  48 */     new Java2DWrapper();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] paramArrayOfString) throws ClassNotFoundException, SQLException {
/*  53 */     if (paramArrayOfString.length != 5) {
/*  54 */       System.err.println("Usage: java examples/TestJava2D dburl user pass tablename column");
/*  55 */       System.err.println();
/*  56 */       System.err.println("dburl has the following format:");
/*  57 */       System.err.println("jdbc:postgis_j2d://HOST:PORT/DATABASENAME");
/*  58 */       System.err.println("tablename is 'jdbc_test' by default.");
/*  59 */       System.exit(1);
/*     */     } 
/*     */     
/*  62 */     Shape[] arrayOfShape = read(paramArrayOfString[0], paramArrayOfString[1], paramArrayOfString[2], "SELECT " + paramArrayOfString[4] + " FROM " + paramArrayOfString[3]);
/*     */ 
/*     */     
/*  65 */     if (arrayOfShape.length == 0) {
/*  66 */       System.err.println("No geometries were found.");
/*     */       
/*     */       return;
/*     */     } 
/*  70 */     System.err.println("Painting...");
/*  71 */     Frame frame = new Frame("PostGIS java2D demo");
/*     */     
/*  73 */     GisCanvas gisCanvas = new GisCanvas(arrayOfShape);
/*     */     
/*  75 */     frame.add(gisCanvas);
/*     */     
/*  77 */     frame.setSize(500, 500);
/*     */     
/*  79 */     frame.addWindowListener(new EventHandler());
/*     */     
/*  81 */     frame.setVisible(true);
/*     */   }
/*     */   
/*     */   static Rectangle2D calcbbox(Shape[] paramArrayOfShape) {
/*  85 */     Rectangle2D rectangle2D = paramArrayOfShape[0].getBounds2D();
/*  86 */     for (byte b = 1; b < paramArrayOfShape.length; b++) {
/*  87 */       rectangle2D = rectangle2D.createUnion(paramArrayOfShape[b].getBounds2D());
/*     */     }
/*  89 */     return rectangle2D;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Shape[] read(String paramString1, String paramString2, String paramString3, String paramString4) throws ClassNotFoundException, SQLException {
/*  94 */     ArrayList arrayList = new ArrayList();
/*  95 */     System.out.println("Creating JDBC connection...");
/*  96 */     Class.forName("org.postgresql.Driver");
/*  97 */     Connection connection = DriverManager.getConnection(paramString1, paramString2, paramString3);
/*     */     
/*  99 */     System.out.println("fetching geometries");
/* 100 */     ResultSet resultSet = connection.createStatement().executeQuery(paramString4);
/*     */     
/* 102 */     while (resultSet.next()) {
/* 103 */       Shape shape = (Shape)resultSet.getObject(1);
/* 104 */       if (shape != null) {
/* 105 */         arrayList.add(shape);
/*     */       }
/*     */     } 
/* 108 */     connection.close();
/* 109 */     return arrayList.<Shape>toArray(SHAPEARRAY);
/*     */   }
/*     */   
/*     */   public static class GisCanvas
/*     */     extends Canvas
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     final Rectangle2D bbox;
/*     */     final Shape[] geometries;
/*     */     
/*     */     public GisCanvas(Shape[] param1ArrayOfShape) {
/* 120 */       this.geometries = param1ArrayOfShape;
/* 121 */       this.bbox = TestJava2d.calcbbox(param1ArrayOfShape);
/* 122 */       setBackground(Color.GREEN);
/*     */     }
/*     */     
/*     */     public void paint(Graphics param1Graphics) {
/* 126 */       Graphics2D graphics2D = (Graphics2D)param1Graphics;
/*     */       
/* 128 */       double d1 = (getWidth() - 10) / this.bbox.getWidth();
/* 129 */       double d2 = (getHeight() - 10) / this.bbox.getHeight();
/*     */       
/* 131 */       AffineTransform affineTransform = new AffineTransform();
/* 132 */       affineTransform.translate((getX() + 5), (getY() + 5));
/* 133 */       affineTransform.scale(d1, d2);
/* 134 */       affineTransform.translate(-this.bbox.getX(), -this.bbox.getY());
/*     */ 
/*     */       
/* 137 */       System.err.println();
/* 138 */       System.err.println("bbox:  " + this.bbox);
/* 139 */       System.err.println("trans: " + affineTransform);
/* 140 */       System.err.println("new:   " + affineTransform.createTransformedShape(this.bbox).getBounds2D());
/* 141 */       System.err.println("visual:" + getBounds());
/*     */       
/* 143 */       for (byte b = 0; b < this.geometries.length; b++) {
/* 144 */         graphics2D.setPaint(Color.BLUE);
/* 145 */         Shape shape = affineTransform.createTransformedShape(this.geometries[b]);
/* 146 */         graphics2D.fill(shape);
/* 147 */         graphics2D.setPaint(Color.ORANGE);
/* 148 */         graphics2D.draw(shape);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class EventHandler
/*     */     implements WindowListener
/*     */   {
/*     */     public void windowActivated(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowClosed(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowClosing(WindowEvent param1WindowEvent) {
/* 162 */       param1WindowEvent.getWindow().setVisible(false);
/* 163 */       System.exit(0);
/*     */     }
/*     */     
/*     */     public void windowDeactivated(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowDeiconified(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowIconified(WindowEvent param1WindowEvent) {}
/*     */     
/*     */     public void windowOpened(WindowEvent param1WindowEvent) {}
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/examples/TestJava2d.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
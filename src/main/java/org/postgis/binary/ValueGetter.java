/*     */ package org.postgis.binary;
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
/*     */ public abstract class ValueGetter
/*     */ {
/*     */   ByteGetter data;
/*     */   int position;
/*     */   public final byte endian;
/*     */   
/*     */   public ValueGetter(ByteGetter paramByteGetter, byte paramByte) {
/*  33 */     this.data = paramByteGetter;
/*  34 */     this.endian = paramByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getByte() {
/*  41 */     return (byte)this.data.get(this.position++);
/*     */   }
/*     */   
/*     */   public int getInt() {
/*  45 */     int i = getInt(this.position);
/*  46 */     this.position += 4;
/*  47 */     return i;
/*     */   }
/*     */   
/*     */   public long getLong() {
/*  51 */     long l = getLong(this.position);
/*  52 */     this.position += 8;
/*  53 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getInt(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract long getLong(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble() {
/*  69 */     long l = getLong();
/*  70 */     return Double.longBitsToDouble(l);
/*     */   }
/*     */   
/*     */   public static class XDR extends ValueGetter {
/*     */     public static final byte NUMBER = 0;
/*     */     
/*     */     public XDR(ByteGetter param1ByteGetter) {
/*  77 */       super(param1ByteGetter, (byte)0);
/*     */     }
/*     */     
/*     */     protected int getInt(int param1Int) {
/*  81 */       return (this.data.get(param1Int) << 24) + (this.data.get(param1Int + 1) << 16) + (this.data.get(param1Int + 2) << 8) + this.data.get(param1Int + 3);
/*     */     }
/*     */ 
/*     */     
/*     */     protected long getLong(int param1Int) {
/*  86 */       return (this.data.get(param1Int) << 56L) + (this.data.get(param1Int + 1) << 48L) + (this.data.get(param1Int + 2) << 40L) + (this.data.get(param1Int + 3) << 32L) + (this.data.get(param1Int + 4) << 24L) + (this.data.get(param1Int + 5) << 16L) + (this.data.get(param1Int + 6) << 8L) + (this.data.get(param1Int + 7) << 0L);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class NDR
/*     */     extends ValueGetter
/*     */   {
/*     */     public static final byte NUMBER = 1;
/*     */     
/*     */     public NDR(ByteGetter param1ByteGetter) {
/*  97 */       super(param1ByteGetter, (byte)1);
/*     */     }
/*     */     
/*     */     protected int getInt(int param1Int) {
/* 101 */       return (this.data.get(param1Int + 3) << 24) + (this.data.get(param1Int + 2) << 16) + (this.data.get(param1Int + 1) << 8) + this.data.get(param1Int);
/*     */     }
/*     */ 
/*     */     
/*     */     protected long getLong(int param1Int) {
/* 106 */       return (this.data.get(param1Int + 7) << 56L) + (this.data.get(param1Int + 6) << 48L) + (this.data.get(param1Int + 5) << 40L) + (this.data.get(param1Int + 4) << 32L) + (this.data.get(param1Int + 3) << 24L) + (this.data.get(param1Int + 2) << 16L) + (this.data.get(param1Int + 1) << 8L) + (this.data.get(param1Int) << 0L);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/binary/ValueGetter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
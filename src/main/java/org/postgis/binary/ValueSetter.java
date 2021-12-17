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
/*     */ public abstract class ValueSetter
/*     */ {
/*     */   ByteSetter data;
/*  29 */   int position = 0;
/*     */   public final byte endian;
/*     */   
/*     */   public ValueSetter(ByteSetter paramByteSetter, byte paramByte) {
/*  33 */     this.data = paramByteSetter;
/*  34 */     this.endian = paramByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(byte paramByte) {
/*  41 */     this.data.set(paramByte, this.position);
/*  42 */     this.position++;
/*     */   }
/*     */   
/*     */   public void setInt(int paramInt) {
/*  46 */     setInt(paramInt, this.position);
/*  47 */     this.position += 4;
/*     */   }
/*     */   
/*     */   public void setLong(long paramLong) {
/*  51 */     setLong(paramLong, this.position);
/*  52 */     this.position += 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void setInt(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void setLong(long paramLong, int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(double paramDouble) {
/*  68 */     long l = Double.doubleToLongBits(paramDouble);
/*  69 */     setLong(l);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  73 */     String str1 = getClass().getName();
/*  74 */     int i = str1.lastIndexOf('.');
/*  75 */     String str2 = str1.substring(i + 1);
/*  76 */     return str2 + "('" + ((this.data == null) ? "NULL" : (this.data.toString() + "')"));
/*     */   }
/*     */   
/*     */   public static class XDR extends ValueSetter {
/*     */     public static final byte NUMBER = 0;
/*     */     
/*     */     public XDR(ByteSetter param1ByteSetter) {
/*  83 */       super(param1ByteSetter, (byte)0);
/*     */     }
/*     */     
/*     */     protected void setInt(int param1Int1, int param1Int2) {
/*  87 */       this.data.set((byte)(param1Int1 >>> 24), param1Int2);
/*  88 */       this.data.set((byte)(param1Int1 >>> 16), param1Int2 + 1);
/*  89 */       this.data.set((byte)(param1Int1 >>> 8), param1Int2 + 2);
/*  90 */       this.data.set((byte)param1Int1, param1Int2 + 3);
/*     */     }
/*     */     
/*     */     protected void setLong(long param1Long, int param1Int) {
/*  94 */       this.data.set((byte)(int)(param1Long >>> 56L), param1Int);
/*  95 */       this.data.set((byte)(int)(param1Long >>> 48L), param1Int + 1);
/*  96 */       this.data.set((byte)(int)(param1Long >>> 40L), param1Int + 2);
/*  97 */       this.data.set((byte)(int)(param1Long >>> 32L), param1Int + 3);
/*  98 */       this.data.set((byte)(int)(param1Long >>> 24L), param1Int + 4);
/*  99 */       this.data.set((byte)(int)(param1Long >>> 16L), param1Int + 5);
/* 100 */       this.data.set((byte)(int)(param1Long >>> 8L), param1Int + 6);
/* 101 */       this.data.set((byte)(int)param1Long, param1Int + 7);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class NDR extends ValueSetter {
/*     */     public static final byte NUMBER = 1;
/*     */     
/*     */     public NDR(ByteSetter param1ByteSetter) {
/* 109 */       super(param1ByteSetter, (byte)1);
/*     */     }
/*     */     
/*     */     protected void setInt(int param1Int1, int param1Int2) {
/* 113 */       this.data.set((byte)(param1Int1 >>> 24), param1Int2 + 3);
/* 114 */       this.data.set((byte)(param1Int1 >>> 16), param1Int2 + 2);
/* 115 */       this.data.set((byte)(param1Int1 >>> 8), param1Int2 + 1);
/* 116 */       this.data.set((byte)param1Int1, param1Int2);
/*     */     }
/*     */     
/*     */     protected void setLong(long param1Long, int param1Int) {
/* 120 */       this.data.set((byte)(int)(param1Long >>> 56L), param1Int + 7);
/* 121 */       this.data.set((byte)(int)(param1Long >>> 48L), param1Int + 6);
/* 122 */       this.data.set((byte)(int)(param1Long >>> 40L), param1Int + 5);
/* 123 */       this.data.set((byte)(int)(param1Long >>> 32L), param1Int + 4);
/* 124 */       this.data.set((byte)(int)(param1Long >>> 24L), param1Int + 3);
/* 125 */       this.data.set((byte)(int)(param1Long >>> 16L), param1Int + 2);
/* 126 */       this.data.set((byte)(int)(param1Long >>> 8L), param1Int + 1);
/* 127 */       this.data.set((byte)(int)param1Long, param1Int);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/binary/ValueSetter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
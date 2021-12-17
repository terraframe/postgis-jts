/*    */ package org.postgis.binary;
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
/*    */ public abstract class ByteSetter
/*    */ {
/*    */   public abstract void set(byte paramByte, int paramInt);
/*    */   
/*    */   public static class BinaryByteSetter
/*    */     extends ByteSetter
/*    */   {
/*    */     private byte[] array;
/*    */     
/*    */     public BinaryByteSetter(int param1Int) {
/* 39 */       this.array = new byte[param1Int];
/*    */     }
/*    */     
/*    */     public void set(byte param1Byte, int param1Int) {
/* 43 */       this.array[param1Int] = param1Byte;
/*    */     }
/*    */     
/*    */     public byte[] result() {
/* 47 */       return this.array;
/*    */     }
/*    */     
/*    */     public String toString() {
/* 51 */       char[] arrayOfChar = new char[this.array.length];
/* 52 */       for (byte b = 0; b < this.array.length; b++) {
/* 53 */         arrayOfChar[b] = (char)(this.array[b] & 0xFF);
/*    */       }
/* 55 */       return new String(arrayOfChar);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class StringByteSetter extends ByteSetter {
/* 60 */     protected static final char[] hextypes = "0123456789ABCDEF".toCharArray();
/*    */     private char[] rep;
/*    */     
/*    */     public StringByteSetter(int param1Int) {
/* 64 */       this.rep = new char[param1Int * 2];
/*    */     }
/*    */     
/*    */     public void set(byte param1Byte, int param1Int) {
/* 68 */       param1Int *= 2;
/* 69 */       this.rep[param1Int] = hextypes[param1Byte >>> 4 & 0xF];
/* 70 */       this.rep[param1Int + 1] = hextypes[param1Byte & 0xF];
/*    */     }
/*    */     
/*    */     public char[] resultAsArray() {
/* 74 */       return this.rep;
/*    */     }
/*    */     
/*    */     public String result() {
/* 78 */       return new String(this.rep);
/*    */     }
/*    */     
/*    */     public String toString() {
/* 82 */       return new String(this.rep);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/binary/ByteSetter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
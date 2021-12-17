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
/*    */ 
/*    */ public abstract class ByteGetter
/*    */ {
/*    */   public abstract int get(int paramInt);
/*    */   
/*    */   public static class BinaryByteGetter
/*    */     extends ByteGetter
/*    */   {
/*    */     private byte[] array;
/*    */     
/*    */     public BinaryByteGetter(byte[] param1ArrayOfbyte) {
/* 40 */       this.array = param1ArrayOfbyte;
/*    */     }
/*    */     
/*    */     public int get(int param1Int) {
/* 44 */       return this.array[param1Int] & 0xFF;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class StringByteGetter extends ByteGetter {
/*    */     private String rep;
/*    */     
/*    */     public StringByteGetter(String param1String) {
/* 52 */       this.rep = param1String;
/*    */     }
/*    */     
/*    */     public int get(int param1Int) {
/* 56 */       param1Int *= 2;
/* 57 */       byte b1 = unhex(this.rep.charAt(param1Int));
/* 58 */       byte b2 = unhex(this.rep.charAt(param1Int + 1));
/* 59 */       return (b1 << 4) + b2;
/*    */     }
/*    */     
/*    */     public static byte unhex(char param1Char) {
/* 63 */       if (param1Char >= '0' && param1Char <= '9')
/* 64 */         return (byte)(param1Char - 48); 
/* 65 */       if (param1Char >= 'A' && param1Char <= 'F')
/* 66 */         return (byte)(param1Char - 65 + 10); 
/* 67 */       if (param1Char >= 'a' && param1Char <= 'f') {
/* 68 */         return (byte)(param1Char - 97 + 10);
/*    */       }
/* 70 */       throw new IllegalArgumentException("No valid Hex char " + param1Char);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/rich/Downloads/postgis-jts-1.5.3.jar!/org/postgis/binary/ByteGetter.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       1.1.3
 */
package com.initiative.smsnow.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;

public class BlowFish {
  private static final String key = "1234";
  public static String encrypt(String messageText){
    try{
      byte[] keyData = key.getBytes();
      SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
      Cipher cipher = Cipher.getInstance("Blowfish");
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
      byte[] doFinal = cipher.doFinal(messageText.getBytes());
      return new String(Base64.encode(doFinal, android.util.Base64.DEFAULT));
    } catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public static String decrypt(String messageText){
    try{
      byte[] keyData = (key).getBytes();
      SecretKeySpec secretKeySpec = new SecretKeySpec(keyData, "Blowfish");
      Cipher cipher = Cipher.getInstance("Blowfish");
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
      byte[] decryptedByte = cipher.doFinal(Base64.decode(messageText, Base64.DEFAULT));
      return new String(decryptedByte);
    } catch (Exception ex){
      ex.printStackTrace();
      return null;
    }
  }
}

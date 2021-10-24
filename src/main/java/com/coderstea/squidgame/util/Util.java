package com.coderstea.squidgame.util;

public class Util {
  private static String OS = System.getProperty("os.name").toLowerCase();

  public static void sleep(int time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static boolean isWindows() {
    return (OS.indexOf("win") >= 0);
  }
}

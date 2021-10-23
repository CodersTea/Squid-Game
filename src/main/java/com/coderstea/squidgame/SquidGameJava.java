package com.coderstea.squidgame;

import com.coderstea.squidgame.games.RedLightGreenLight;

import java.util.Scanner;

import static java.lang.System.exit;

public class SquidGameJava {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int gameNo = 0;
    try {
      do {
        // This will allow the cmd to use the ANSI colors and emoji.
        // check README.md for the known issues.
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "chcp", "65001").inheritIO();
        Process p = pb.start();
        p.waitFor();

        if(!p.isAlive()) {
          System.out.println("Starting Squid Game. Please select a Game to play");
          System.out.println("1. Red Light Green Light");
          System.out.println("5. Don't Want to Play");
          System.out.println("Your Choice: ");
          gameNo = sc.nextInt();
          startGame(gameNo);
        }
      } while (gameNo > 5);
    } catch (Exception e){
      e.printStackTrace();
    }

    sc.close();
  }

  private static void startGame(int gameNo) throws InterruptedException {
    switch (gameNo) {
      case 1:
        new RedLightGreenLight().startGame();
        break;
    }
  }
}

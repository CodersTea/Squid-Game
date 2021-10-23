package com.coderstea.squidgame.games;

import java.util.Random;
import java.util.Scanner;

import static com.coderstea.squidgame.util.Util.sleep;

public class RedLightGreenLight implements SquidGame {

  //Use Windows ANSI color code
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_CYAN = "\u001B[36m";

  // Windows User may not be able to see actual Red Emoji
  private static final String RED_LIGHT = ANSI_RED + "Red Light 🔴 👧" + ANSI_RESET;
  private static final String GREEN_LIGHT = ANSI_GREEN + "Green Light 🟢 👧"+ ANSI_RESET;
  private static final String GAME_OVER = ANSI_CYAN + "💔 Game Over 🤯🔫  😵 (press Enter)"+ ANSI_RESET;


  private static final int TIME_LIMIT = 1000 * 60; //  1 Minute
  private static final int BREATHER = 500;
  private static final int GREEN_LIGHT_MAX_TIME = 4000;

  // game variables
  private static int pressCount = 0;
  private static boolean gameOver = false;

  private static Scanner scanner;

  @Override
  public void startGame() throws InterruptedException {
    scanner = new Scanner(System.in);
    boolean continuePlaying;
    do {
      // resetting varibales
      pressCount = 0;
      gameOver = false;
      System.out.println("\n =============================");
      System.out.printf("Starting the Squid Game #1: %s / %s", RED_LIGHT, GREEN_LIGHT);
      System.out.println();
      System.out.println("When Green Light, Please press the Enter button continuously.");
      System.out.println("Stop pressing once you see Red light");

      Thread frontMan = new Thread(RedLightGreenLight::frontMan);
      Thread player = new Thread(RedLightGreenLight::player);

      // Starting the threads
      frontMan.start();
      player.start();

      // waiting threads to finish
      frontMan.join();
      player.join();

      System.out.println("============================");
      System.out.println("Do you want to continue?: [Y/N] : ");
      String next = scanner.next();
      continuePlaying = next.equalsIgnoreCase("Y");
    } while (continuePlaying); // if pressed Y, restart the game
  }

  private static void frontMan() {
    int timeRemain = TIME_LIMIT;
    Random random = new Random();
    do {
      // calculate time for "Mugunghwa Kkoci Pieot Seumnida"
      int dollCountingTime = random.nextInt(Math.min(timeRemain, GREEN_LIGHT_MAX_TIME)) + 1000;
      System.out.printf("%s Time remain: %d seconds (current score %d ): %n", GREEN_LIGHT, timeRemain / 1000, pressCount);
      System.out.println();

      // countdown the timer
      timeRemain -= dollCountingTime;

      // doll 👧 is now chanting "Mugunghwa Kkoci Pieot Seumnida"
      sleep(dollCountingTime);

      // Stop pressing enter now
      System.out.println(RED_LIGHT);

      // The Doll is now scanning for players' movement, Don't Move

      int lastPressedInGreenLight = pressCount;
      int scanningForMovements = random.nextInt(2000) + BREATHER;
      sleep(scanningForMovements); // Scanning for any movement
      timeRemain -= scanningForMovements;

      // IF player has moved (pressed enter in Red light time) game is over
      if (pressCount != lastPressedInGreenLight) {
        gameOver = true;
        pressCount = lastPressedInGreenLight;
        break;
      }
    } while (timeRemain >= 0);

    System.out.printf("%s: Your score is %d", GAME_OVER, pressCount);
  }

  private static void player() {
    // keep on reading Enter until game is over
    while (!gameOver) {
//      if (gameOver) {
//        System.out.println(GAME_OVER);
//        break;
//      }
      scanner.nextLine();
      pressCount++;
    }
  }
}
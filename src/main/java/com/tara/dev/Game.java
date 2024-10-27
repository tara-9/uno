package com.tara.dev;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class Game {
    List<Integer> gameset = new ArrayList<>();

    Queue<Integer> cards = new LinkedList<>();

    Random random = new Random();

    List<List<Integer>> players = new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Game game = new Game();
        game.initialze();
        game.play();
    }


    public void initialze() {
        for (int i = 0; i < 108; i++) {
            gameset.add(i);
        }
        Collections.shuffle(gameset, random);
        gameset.stream().forEach(num -> cards.offer(num));

        for(int i=0;i<4;i++) {
            players.add(new ArrayList<>());
            List<Integer> player = players.get(i);
            for(int j=0;j<8;j++) {
                player.add(cards.poll());
            }
        }
    }

    public void play()  {
        int currentCard = cards.poll();
        String currentColor = color(currentCard);
        System.out.println("First card details");
        showCardDetails(currentCard);
        showTheCards(0);

        while (true) {
            int cardPlayed = scanner.nextInt();
            if(isCardAWildCard(cardPlayed)) {
                currentColor = scanner.nextLine();
                break;
            }
            if(isCardAWildFourCard(cardPlayed)) {
                currentColor = scanner.nextLine();
                break;
            } else {
                if(isCardAWildFourCard(currentCard) || isCardAWildCard(currentCard)) {
                    String cardPlayedColor = color(cardPlayed);
                    if(cardPlayedColor != currentColor) continue;
                } else {
                    if(isCardAPlus2Card(currentCard) && isCardAPlus2Card(cardPlayed)) break;
                    else if(isCardAReverseCard(currentCard) && isCardAReverseCard(cardPlayed)) break;
                    else if(isCardASkipCard(currentCard) && isCardASkipCard(cardPlayed)) break;
                    else {
                        String colorOfThePlayedCard = color(cardPlayed);
                        if(currentColor == colorOfThePlayedCard || getNoOfCard(currentCard) == getNoOfCard(cardPlayed)) break;
                        else {
                            System.out.println("You have chosen a wrong card");
                        }
                    }

                }
            }
        }



    }



    private boolean isCardAWildCard(int cardNo){
        return cardNo>=100 && cardNo < 104;
    }

    private boolean isCardAWildFourCard(int cardNo) {
        return cardNo >= 104;
    }

    private boolean isCardASkipCard(int cardNo) {
        cardNo = cardNo%25;
        return cardNo > 18 && cardNo <=20;
    }

    private boolean isCardAReverseCard(int cardNo) {
        cardNo = cardNo%25;
        return cardNo > 20 && cardNo <=22;
    }

    private boolean isCardAPlus2Card(int cardNo) {
        cardNo = cardNo%25;
        return cardNo > 22 && cardNo < 25;
    }

    private String color(int cardNo) {
        if(cardNo < 25) return "RED";
        else if(cardNo < 50) return "BLUE";
        else if(cardNo < 75) return "GREEN";
        else if(cardNo < 100) return "YELLOW";
        return "";
    }

    private int getNoOfCard(int cardNo) {
        cardNo = cardNo%25;
        if(cardNo >= 10) return cardNo-9;
        return cardNo;
    }

    private void showTheCards(int playerNo) {
        System.out.println("You have cards ");
        players.get(playerNo).forEach(card -> showCardDetails(card));
    }

    private void showCardDetails(int card) {
        if(isCardAWildCard(card)) {
            System.out.println("A wild card with card no "+ card);
        }
        else if(isCardAWildFourCard(card)) {
            System.out.println(" A wild four card with card no "+ card);
        }
        else {
            String color = color(card);
            if(isCardASkipCard(card)) System.out.println(color + " Skip card with card no " + card);
            else if(isCardAReverseCard(card)) System.out.println(color + " Reverse card with card no "+ card);
            else if(isCardAPlus2Card(card)) System.out.println(color + " Plus 2 card with card no "+ card);
            else {
                int cardNo = getNoOfCard(card);
                System.out.println(color + " "+ cardNo +" card with card no "+ card);
            }
        }
    }
}

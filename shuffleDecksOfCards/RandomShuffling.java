package object_oriented_design.shuffleDecksOfCards;

import java.util.Random;

/*
 * Implement a simple shuffling algorithm that simply randomizes the deck in-place. 
 */
public class RandomShuffling implements ShuffleStrategy {

	@Override
	public void shuffle(Deck d) {
		Card[] cards = d.getCards();
		Random ran = new Random();
		
		for(int i = cards.length-1; i>= 0; i--){
			Card tmp = cards[i];
			int index = ran.nextInt(i);
			cards[i] = cards[index];
			cards[index] = tmp;
		}
	}

}

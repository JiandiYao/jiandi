package object_oriented_design.shuffleDecksOfCards;

import java.util.Random;

/*
 * Implement a more complex algorithm that simulates hand-shuffling, 
 * i.e. splitting the deck in half and interleaving the two halves, 
 * repeating the process multiple times. 
 */
public class HandShuffling implements ShuffleStrategy {

	
	@Override
	public void shuffle(Deck d) {
		Card[] cards = d.getCards();
		shuffle(cards, Constant.HAND_SHUFFLE_TIMES);
	}
	
	private void shuffle(Card[] cards, int times){
		int i = 0;
		int j = Constant.CARD_NUMBER/2;
		Random ran = new Random();
		Card[] tmpCards = new Card[Constant.CARD_NUMBER];
		
		int k = 0;
		
		while(i<Constant.CARD_NUMBER/2 && j < Constant.CARD_NUMBER){
			int index1 = ran.nextInt(Constant.CARD_NUMBER/2 - i)+i;
			int index2 = ran.nextInt(Constant.CARD_NUMBER - j)+j;
			
			while(i <= index1){
				tmpCards[k] = cards[i];
				k++;
				i++;
			}
			while(j <= index2){
				tmpCards[k] = cards[j];
				k++;
				j++;
			}
		}
		
		if(i < Constant.CARD_NUMBER/2){
			tmpCards[k] = cards[i];
			k++;
			i++;
		}
		
		if(j < Constant.CARD_NUMBER){
			tmpCards[k] = cards[j];
			k++;
			j++;
		}
		
		for(int m = 0; i < Constant.CARD_NUMBER; i++){
			cards[m] = tmpCards[m];
		}
	}

}

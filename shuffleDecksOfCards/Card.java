package object_oriented_design.shuffleDecksOfCards;

public class Card {

	private String cardSuit;
	private String faceValue;
	
	public Card(String cardSuit, String faceValue){
		this.faceValue = faceValue;
		this.cardSuit = cardSuit;
	}

	public String getCardSuit() {
		return cardSuit;
	}

	public String getFaceValue() {
		return faceValue;
	}
	
	@Override
	public String toString(){
		return faceValue + "-"+cardSuit;
	}
}

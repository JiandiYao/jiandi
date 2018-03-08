package object_oriented_design.shuffleDecksOfCards;

/*
 * A card may be represented as a simple string such as “5-heart”, or “K-spade”. 
· A deck is an ordered list of 52 standard playing cards. 
 */
public class Deck {

	private String deckName;
	private Card[] cards;
	public Deck(String deckName){
		this.deckName = deckName;
		cards = new Card[52];
		generateCards();
	}
	
	
	
	public String getDeckName() {
		return deckName;
	}



	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}



	public Card[] getCards() {
		return cards;
	}



	public void setCards(Card[] cards) {
		this.cards = cards;
	}



	private void generateCards(){
		
		for(int i = 1; i <= 13; i++){
			cards[i-1] = new Card(Constant.CLUB, generateFaceValue(i));
			cards[i-1+13] = new Card(Constant.DIAMOND, generateFaceValue(i));
			cards[i-1+26] = new Card(Constant.HEART, generateFaceValue(i));
			cards[i-1+39] = new Card(Constant.SPADE, generateFaceValue(i));
		}
	}
	
	private String generateFaceValue(int i){
		switch(i){
		case 1: 
			return "1";
			
		case 2:
			return "2";
		case 3:
			return "3";
		case 4:
			return "4";
		case 5: 
			return "5";
		case 6:
			return "6";
		case 7:
			return "7";
		case 8:
			return "8";
		case 9:
			return "9";
		case 10:
			return "10";
		case 11:
			return "J";
		case 12:
			return "Q";
		case 13:
			return "K";
		default:
			return null;
			
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Card c: cards){
			sb.append(c).append(",");
		}
		return sb.toString().substring(0, sb.length()-1);
	}
}

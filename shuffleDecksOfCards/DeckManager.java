package object_oriented_design.shuffleDecksOfCards;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/*
 * PUT an idempotent request for the creation of a new named deck.
 New decks are created in some initial sorted order. 
· POST a request to shuffle an existing named deck. 
· GET a list of the current decks persisted in the service. 
· GET a named deck in its current sorted/shuffled order. 
· DELETE a named deck. 
 */
public class DeckManager {

	private ConcurrentMap<String, Deck> decks;
	public DeckManager(){
		decks = new ConcurrentHashMap<String, Deck>();
	}
	
	public Deck createDeck(String deckName) throws Exception{
		if(decks.containsKey(deckName)){
			throw new Exception("The deck name already exists, choose a different one!");
		}
		
		Deck d = new Deck(deckName);
		
		decks.put(deckName, d);
		return d;
	}
	
	public Deck shuffleDeckByHand(String deckName){
		return shuffleDeckByHand(decks.get(deckName));
	}

	public Deck shuffleDeckByHand(Deck deck) {
		ShuffleStrategy strategy = new HandShuffling();
		strategy.shuffle(deck);
		return deck;
	}
	public Deck shuffleDeckByRandom(String deckName){
		return shuffleDeckByRandom(decks.get(deckName));
	}

	public Deck shuffleDeckByRandom(Deck deck) {
		ShuffleStrategy strategy = new RandomShuffling();
		strategy.shuffle(deck);
		return deck;

	}
	
	public Map<String, String> getDecks(){
		Map<String, String> map = new HashMap<String, String>();
		for(String key: decks.keySet()){
			map.put(key, decks.get(key).toString());
		}
		return map;
	}

	public void setDecks(ConcurrentMap<String, Deck> decks) {
		this.decks = decks;
	}
	
	public Deck getDeckByName(String deckName){
		return decks.get(deckName);
	}
	
	public void deleteDeckByName(String deckName){
		decks.remove(deckName);
	}
}

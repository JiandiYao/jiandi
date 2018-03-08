package object_oriented_design.shuffleDecksOfCards;

/*
 * Create a RESTful microservice that implements a card shuffling algorithm, as defined below. Should have evidence of test-driven development with unit tests. Use best practices of interfaces and generics for abstraction, preferably implementing a strategy pattern for deploy-time dependency injection of a shuffling algorithm. 
Requirements: 
· Create a microservice that stores and shuffles card decks. 
· A card may be represented as a simple string such as “5-heart”, or “K-spade”. 
· A deck is an ordered list of 52 standard playing cards. 
· Expose a RESTful interface that allows a user to: 
· PUT an idempotent request for the creation of a new named deck.
 New decks are created in some initial sorted order. 
· POST a request to shuffle an existing named deck. 
· GET a list of the current decks persisted in the service. 
· GET a named deck in its current sorted/shuffled order. 
· DELETE a named deck. 
· Design your own data and API structure(s) for the deck. 
· Persist the decks in-memory only, but stub the persistence layer such that it can be later upgraded to a durable datastore. 
· Implement a simple shuffling algorithm that simply randomizes the deck in-place. 
· Implement a more complex algorithm that simulates hand-shuffling, i.e. splitting the deck in half and interleaving the two halves, repeating the process multiple times. 
· Allow switching the algorithms at deploy-time only via configuration.


 */
public interface ShuffleStrategy {

	public void shuffle(Deck d);
}

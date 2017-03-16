package experiments;

public class Card {
	private String cardName;
	private CardType cardType;

	public Card(String name, CardType cardType) {

		cardName = name;
		this.cardType = cardType;

	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

}

package clueGame;

public class Card {  //card Class
	private String cardName;
	private CardType cardType;

	public Card(String name, CardType cardType) { //setting the card name

		cardName = name;
		this.cardType = cardType;

	}

	public String getCardName() { //to string function for the card name
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public CardType getCardType() { //returns the card type
		return cardType;
	}

	public void setCardType(CardType cardType) {//setter for card type
		this.cardType = cardType;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardName == null) {
			if (other.cardName != null)
				return false;
		} else if (!cardName.equals(other.cardName))
			return false;
		if (cardType != other.cardType)
			return false;
		return true;
	}
	

}

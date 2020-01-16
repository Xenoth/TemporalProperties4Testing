package exemples;

import fr.ufc.m2info.svam.ATM;
import fr.ufc.m2info.svam.Account;
import fr.ufc.m2info.svam.Card;

public class ATMAdapter {

    ATM sut = new ATM();
    
    public void reset() {
        sut = new ATM();
    }
    
    public void insertCard() {
        // create a card with pin code 1234 and associated account of 100 euros in balance
        Card c = new Card(1234, new Account(100));
        System.out.println("Inserted card");
        sut.insertCard(c);
    }
    
    public void cancel() {
        System.out.println("Pressed cancel");
        sut.cancel();
    }
    
}

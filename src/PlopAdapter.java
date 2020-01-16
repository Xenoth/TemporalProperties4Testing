import fr.ufc.m2info.svam.ATM;
import fr.ufc.m2info.svam.Account;
import fr.ufc.m2info.svam.Card;

public class PlopAdapter {

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

    public void takeBills() {
        System.out.println("Taking bills");
        int[] bills = sut.takeBills();
        assert bills != null;
    }

    public void wait6SecondsTakingBills() {
        System.out.println("Wait 6s on taking bills");
        try {
            Thread.sleep(6001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int[] bills = sut.takeBills();
        assert bills == null;
    }

    public void takeCard() {
        System.out.println("Taking card");
        Card card = sut.takeCard();
        assert card != null;
    }

    public void wait6SecondsTakingCard() {
        System.out.println("Wait 6s on taking card");
        try {
            Thread.sleep(6001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Card card = sut.takeCard();
        assert card == null;
    }

}
import fr.ufc.m2info.svam.ATM;
import fr.ufc.m2info.svam.Account;
import fr.ufc.m2info.svam.Card;

public class ATMAdapter {

    ATM sut = new ATM();

    public void reset() {
        sut = new ATM();
    }

    public void insertCardValid() {
        // create a card with pin code 1234 and associated account of 100 euros in balance
        Card c = new Card(1234, new Account(100));
        System.out.println("Inserted card");
        int val = sut.insertCard(c);
        assert val == 0;
    }

    public void insertCardBlocked() {
        // create a card with pin code 1234 and associated account of 100 euros in balance
        Card c = new Card(1234, new Account(100));
        System.out.println("Inserted card");
        c.checkPin(1111);
        c.checkPin(1111);
        c.checkPin(1111);
        int val = sut.insertCard(c);
        assert val == -2;
    }

    public void cancel() {
        System.out.println("Pressed cancel");
        sut.cancel();
    }

    public void chooseAmountValid(){

    }

    public void chooseAmountNoValid(){

    }

    public void inputPinValid(){
        System.out.println("InputPin");
        sut.inputPin(1234);
    }

    public void inputPinNoValid(){
        System.out.println("InputPin");
        sut.inputPin(1111);
    }

    public void inputPinBlocked(){
        System.out.println("InputPin");
        int val = 0;
        do {
            val = sut.inputPin(1111);
        }while (val != -3);
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

import fr.ufc.m2info.svam.ATM;
import fr.ufc.m2info.svam.Account;
import fr.ufc.m2info.svam.Card;
import org.junit.Assert;

public class ATMAdapter {

    ATM sut = new ATM();
    private int state_prop1 = 0;
    private int state_prop2 = 0;
    private int state_prop3 = 0;
    private int state_prop4 = 0;

    public void reset() {
        sut = new ATM();
        state_prop1 = 0;
        state_prop2 = 0;
        state_prop3 = 0;
        state_prop4 = 0;
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

//    public void cancel() {
//        System.out.println("Pressed cancel");
//        sut.cancel();
//    }

    public void chooseAmountValid(){
        System.out.println("chooseAmount Valid");
        int val = sut.chooseAmount(10);
        assert val == 0;
    }

    public void chooseAmountNoValid(){
        System.out.println("chooseAmount No Valid");
        int val = sut.chooseAmount(110);
        assert val == -3;
    }

    public void inputPinValid(){
        System.out.println("InputPin Valid");
        int val = sut.inputPin(1234);
        assert val == 0;
        if(state_prop1 == 0){
            state_prop1 = 2;
        }else{
            Assert.fail("Proprieté 1 : violation !");
        }
        state_prop2 = 2;
    }

    public void inputPinNoValid(){
        System.out.println("InputPin No Valid");
        int val = sut.inputPin(1111);
        assert val == -2;
    }

    public void inputPinBlocked(){
        System.out.println("InputPin Blocked");
        int val = 0;
        do {
            val = sut.inputPin(1111);
        }while (val == -2);
    }

    public void takeBills() {
        System.out.println("Taking bills");
        int[] bills = sut.takeBills();
        assert bills != null;
        state_prop1 = 1;
        if(state_prop2 == 2) {
            state_prop2 = 1;
        }else{
            Assert.fail("Proprieté 2 : violation !");
        }
    }

    public void wait6SecondsTakingBills() {
        System.out.println("Wait 6s on taking bills");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(state_prop4 == 1){
            Assert.fail("Proprieté 4 : violation !");
        }
        state_prop3 = 1;
        int[] bills = sut.takeBills();
        assert bills == null;
        if(state_prop3 != 1){
            Assert.fail("Proprieté 3 : violation !");
        }
        state_prop4 = 1;
    }

    public void takeCard() {
        System.out.println("Taking card");
        Card card = sut.takeCard();
        assert card != null;
    }

    public void wait6SecondsTakingCard() {
        System.out.println("Wait 6s on taking card");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Card card = sut.takeCard();
        assert card == null;
    }

}

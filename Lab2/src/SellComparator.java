import java.util.Comparator;

public class SellComparator implements Comparator<Bid>{

	@Override
	public int compare(Bid bid1, Bid bid2) {
		if(bid1.equals(bid2)) return 0;
		if(bid1.bid > bid2.bid) return -1;
		else return 1;
	}

}

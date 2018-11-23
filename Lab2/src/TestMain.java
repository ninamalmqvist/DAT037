
public class TestMain {
	public static void main(String[] args){
		SellComparator sell = new SellComparator();
		BuyComparator buy = new BuyComparator();
		PriorityQueue <Bid> sell_pq = new PriorityQueue<Bid>(sell);
		PriorityQueue <Bid> buy_pq = new PriorityQueue<Bid>(buy);
		
		Bid bid = new Bid("P0",65536);
		Bid bid2 = new Bid("P1",131072);
		Bid bid3 = new Bid("P1",262144);
//		Bid bid3 = new Bid("gris",1);
//		Bid bid4 = new Bid("gris",0);
//		Bid bid5 = new Bid("gris",4);
//		Bid bid6 = new Bid("gris",5);
		sell_pq.add(bid);
//		sell_pq.add(bid2);
		sell_pq.add(bid2);
//		sell_pq.add(bid4);
//		sell_pq.add(bid5);
//		sell_pq.add(bid6);
		sell_pq.update(bid3);
//		sell_pq.update(bid);
		
		//sell_pq.deleteMinimum();
		//sell_pq.deleteMinimum();
		//System.out.println(sell_pq.printa());
		System.out.print(sell_pq.minimum());
	}

}

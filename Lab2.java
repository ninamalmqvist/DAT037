import java.io.*;
import java.util.*;

public class Lab2 {
	public static String pureMain(String[] commands) {
	
		PriorityQueue <Bid> sell_pq = new PriorityQueue<Bid>(new SellComparator());
		PriorityQueue <Bid> buy_pq = new PriorityQueue<Bid>(new BuyComparator());

		StringBuilder sb = new StringBuilder();
		StringBuilder purchase = new StringBuilder();

		for(int line_no=0;line_no<commands.length;line_no++){
			String line = commands[line_no];
			if( line.equals("") )continue;
			// Divide input into name, action, bid and possibly new bid
			String[] parts = line.split("\\s+");
			if( parts.length != 3 && parts.length != 4)
				throw new RuntimeException("line " + line_no + ": " + parts.length + " words");
			String name = parts[0];
			if( name.charAt(0) == '\0' )
				throw new RuntimeException("line " + line_no + ": invalid name");
			String action = parts[1];
			int price;
			try {
				price = Integer.parseInt(parts[2]);
			} catch(NumberFormatException e){
				throw new RuntimeException(
						"line " + line_no + ": invalid price");
			}
			
			int newPrice;
			Bid bid = new Bid(name,price);
			// Add to sell or buy priority queue dependent on requested action.
			if( action.equals("K") ) {
				buy_pq.add(bid);
			} else if( action.equals("S") ) {
				sell_pq.add(bid);
			} else if( action.equals("NK") || action.equals("NS")){
				try {
					newPrice = Integer.parseInt(parts[3]);
				} catch(NumberFormatException e){
					throw new RuntimeException(
							"line " + line_no + ": invalid new price");
				}
				Bid newBid = new Bid(name,newPrice);
				
				if( action.equals("NK")){
					buy_pq.update(bid,newBid);
				}
				else if(action.equals("NS")){
					sell_pq.update(bid,newBid);
				}
				
			} else {
				throw new RuntimeException(
						"line " + line_no + ": invalid action");
			}
			// Removes bid, when a match is found
			if( sell_pq.size() == 0 || buy_pq.size() == 0 )continue;
			else if(buy_pq.minimum().bid >= sell_pq.minimum().bid ){
				purchase.append(buy_pq.minimum().name + " köper från " + sell_pq.minimum().name + "för " + buy_pq.minimum().bid + "\n");
				buy_pq.deleteMinimum();
				sell_pq.deleteMinimum();		
			}
		}
	
		sb.append(purchase); 
		sb.append("Orderbok:" + "\n");
		
		// Append remaining sell bids to the order book
		sb.append("Säljare: ");
			while(sell_pq.size()>0){
				if(sell_pq.size() == 1){
					sb.append(sell_pq.minimum());
					break;
				}
				sb.append(sell_pq.minimum()+", ");
				sell_pq.deleteMinimum();
				
			}
		sb.append("\n"); 
		
		// Append remaining buy bids to the order book
		sb.append("Köpare: ");
		while(buy_pq.size()>0){
			if(buy_pq.size() == 1){
				sb.append(buy_pq.minimum());
				break;
			}else{
				sb.append(buy_pq.minimum()+", ");
				buy_pq.deleteMinimum();
			}
		}
		return sb.toString();
	}
			

	public static void main(String[] args) throws IOException {
		final BufferedReader actions;
		if( args.length != 1 ){
			actions = new BufferedReader(new InputStreamReader(System.in));
		} else {
			actions = new BufferedReader(new FileReader(args[0]));
		}

		List<String> lines = new LinkedList<String>();
		while(true){
			String line = actions.readLine();
			if( line == null)break;
			lines.add(line);
		}
		actions.close();

		System.out.println(pureMain(lines.toArray(new String[lines.size()])));
	}
}
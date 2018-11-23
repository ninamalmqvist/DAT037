import java.io.*;
import java.util.*;

public class Lab2 {
	public static String pureMain(String[] commands) {
	
		PriorityQueue <Bid> sell_pq = new PriorityQueue<Bid>(new SellComparator());
		PriorityQueue <Bid> buy_pq = new PriorityQueue<Bid>(new BuyComparator());

		StringBuilder sb = new StringBuilder();
		StringBuilder subSbBuy = new StringBuilder();

		for(int line_no=0;line_no<commands.length;line_no++){
			String line = commands[line_no];
			if( line.equals("") )continue;

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
			
			if( action.equals("K") ) {
				buy_pq.add(bid);
				//subSbBuy.append(bid);
			} else if( action.equals("S") ) {
				sell_pq.add(bid);
				//subSbSell.append(bid);
			} else if( action.equals("NK") || action.equals("NS")){
				try {
					newPrice = Integer.parseInt(parts[3]);
				} catch(NumberFormatException e){
					throw new RuntimeException(
							"line " + line_no + ": invalid new price");
				}
				Bid newBid = new Bid(name,newPrice);
				
				if( action.equals("NK")){
					buy_pq.update(newBid);
				}
				else if(action.equals("NS")){
					sell_pq.update(newBid);
				}
				
			} else {
				throw new RuntimeException(
						"line " + line_no + ": invalid action");
			}
			
			if( sell_pq.size() == 0 || buy_pq.size() == 0 )continue;
			else if(buy_pq.minimum().bid >= sell_pq.minimum().bid ){
				subSbBuy.append(buy_pq.minimum().name + " köper från " + sell_pq.minimum().name + "för " + buy_pq.minimum().bid + "\n");
				buy_pq.deleteMinimum();
				sell_pq.deleteMinimum();		
			}
		}
	
		sb.append(subSbBuy);
		sb.append("Orderbok:" + "\n");
	
		
			sb.append("Säljare: ");
			if(sell_pq.size()>0){
				while(true){
					if(sell_pq.size() == 1){
						sb.append(sell_pq.minimum()+"\n");
						break;
					}
					sb.append(sell_pq.minimum()+", ");
					sell_pq.deleteMinimum();
					if(sell_pq.size() == 0) break;
				}
			}
			else sb.append("\n");
		
			// TODO DONE print remaining sellers.
			//       can remove from priority queue until it is empty.
			
			sb.append("Köpare: ");
			if(buy_pq.size()>0){
				while(true){
					if(buy_pq.size() == 1){
						sb.append(buy_pq.minimum()+"\n");
						break;
					}
					sb.append(buy_pq.minimum()+", ");
					buy_pq.deleteMinimum();
					if(buy_pq.size() == 0) break;
				}
			}
			else sb.append("\n");
		// TODO DONE print remaining buyers
		//       can remove from priority queue until it is empty.

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
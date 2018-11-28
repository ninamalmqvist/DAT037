import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class PriorityQueue<E> {

	private ArrayList<E> heap = new ArrayList<E>();
	private Comparator<E> comparator;
	private HashMap<E, Integer> hashMap = new HashMap();

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}

	// Adds an item to the priority queue.
	public void add(E x){
		assert invariant() : showHeap();
		heap.add(x);
		hashMap.put(x,heap.size()-1);
		siftUp(heap.size()); 
		assert invariant() : showHeap();
	}

	// Returns the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public E minimum() {
		if (size() == 0)
			throw new NoSuchElementException();

		return heap.get(0);
	}

	// Removes the smallest item in the priority queue.
	// Throws NoSuchElementException if empty.
	public void deleteMinimum() {
		assert invariant() : showHeap();
		if (size() == 0)
			throw new NoSuchElementException();

		heap.set(0, heap.get(heap.size()-1));
		hashMap.replace(heap.get(heap.size()-1), 0);
//		System.out.println(hashMap.size());
		hashMap.remove(heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		
		if (heap.size() > 0) siftDown(0);
		assert invariant() : showHeap();
	}
	
	// update new bid
	public void update(E x){
		assert invariant() : showHeap();
		Bid xBid = (Bid) x;
		int bidIndex = indexOfName(xBid.name);
		if(bidIndex<0) return; // if there is no old bid, do nothing
		if(heap.size() == 1){
			deleteMinimum();
			add(x);
			return;
		}
//			System.out.println(""+x + heap.get(parent(bidIndex)));
		if(comparator.compare(x,heap.get(parent(bidIndex)))<0){
			heap.set(bidIndex, x);
			hashMap.replace(x, bidIndex);
			System.out.println(9);
			siftDown(bidIndex);
		}
		else{ // ändrat
			heap.set(bidIndex, x);
			hashMap.replace(x, bidIndex);
			siftUp(bidIndex+1);
		}
		assert invariant() : showHeap();
	}
	
	private int indexOfName(String x){
		for(int i = 0; i<heap.size();i++){
				Bid bidOnPos = (Bid) heap.get(i);
				if(x.equals(bidOnPos.name)) return i;
			}
		return -1;
	}
	
	// Sifts a node up.
	// siftUp(index) fixes the invariant if the element at 'index' may
	// be less than its parent, but all other elements are correct.
	private void siftUp(int index) {
		index = index-1;
		E value;
		E parentValue;
		int parentIndex;
		// stop at the root
		while (index>0) { 
			parentValue = heap.get(parent(index));
			value = heap.get(index);
			parentIndex =  parent(index);
	
			if(comparator.compare(value,parentValue)>0){
				heap.set(index, parentValue);
				heap.set(parentIndex, value);
				hashMap.replace(parentValue, index);
				hashMap.replace(value, parentIndex);
			}
			value = parentValue;
			index = parentIndex; 
		}
		
	}
	
	
	// Sifts a node down.
	// siftDown(index) fixes the invariant if the element at 'index' may
	// be greater than its children, but all other elements are correct.
	private void siftDown(int index) {
		E value = heap.get(index);
		// Stop when the node is a leaf.
		while (leftChild(index) < heap.size()) {
			int left    = leftChild(index);
			int right   = rightChild(index);
			// Work out whether the left or right child is smaller.
			// Start out by assuming the left child is smaller...
			int child = left;
			E childValue = heap.get(left);

			// ...but then check in case the right child is smaller.
			// (We do it like this because maybe there's no right child.)
			if (right < heap.size()) {
				E rightValue = heap.get(right);
				if (comparator.compare(childValue, rightValue) < 0) {
					child = right;
					childValue = rightValue;
	
				}
			}

			// If the child is smaller than the parent,
			// carry on downwards.
			if (comparator.compare(value, childValue) < 0) {
				heap.set(index, childValue);
				hashMap.replace(childValue, index);
				index = child;
			} else break;
		}

		heap.set(index, value);
		hashMap.replace(value, index);
	}
	
	// Helper functions for calculating the children and parent of an index.
	private final int leftChild(int index) {
		return 2*index+1;
	}

	private final int rightChild(int index) {
		return 2*index+2;
	}

	private final int parent(int index) {
		return (index-1)/2;
	}
	/*private boolean invariant(){
        // TODO: return true if and only if the heap invariant is true.
	}*/
	
	private boolean invariant(){
		int index = 0;
		int left;
		int right;
		E parent;
		if(heap.size() == 0) return true;
		//System.out.println(heap.size());
		for(int i = 0; i<heap.size(); i++) {
			//System.out.println(i);
			left = leftChild(index);
			right = rightChild(index);
			parent = heap.get(index);
			if (left<heap.size() && !(comparator.compare(parent,heap.get(left)) == 1)) {
				return false;
			}
			else if (right<heap.size() && !(comparator.compare(parent,heap.get(left)) == 1)) {
				return false;
			}
		}
		return true;
	}
	
	private String showHeap(){
		//System.out.println(9);
		String showHeap = heap.get(0).toString();
        for(int i=1; i<heap.size(); i++){
        	showHeap += ", " + heap.get(i).toString();
        }
        return showHeap;
	}
}
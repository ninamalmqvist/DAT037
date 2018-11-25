import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class PriorityQueue<E> {

	private ArrayList<E> heap = new ArrayList<E>();
	private Comparator<E> comparator;

	public PriorityQueue(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	// Returns the size of the priority queue.
	public int size() {
		return heap.size();
	}

	// Adds an item to the priority queue.
	public void add(E x){
		heap.add(x);
		siftUp(heap.size()); 
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
		if (size() == 0)
			throw new NoSuchElementException();

		heap.set(0, heap.get(heap.size()-1));
		heap.remove(heap.size()-1);
		if (heap.size() > 0) siftDown(0);
	}
	
	// update new bid
	public void update(E x){
		Bid xBid = (Bid) x;
		int bidIndex = indexOfName(xBid.name);
		
		if(bidIndex<0) return; // if there is no old bid, do nothing
		if(heap.size() == 1){
			deleteMinimum();
			add(x);
			return;
		}

		if(comparator.compare(x,heap.get(parent(bidIndex)))<0){
			heap.set(bidIndex, x);
			siftDown(bidIndex);
		}
		else{
			heap.set(bidIndex, x);
			siftUp(bidIndex+1);
		}
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
				index = child;
			} else break;
		}

		heap.set(index, value);
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
}
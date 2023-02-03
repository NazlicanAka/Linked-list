import java.util.HashSet;
import java.util.NoSuchElementException;

public class FactoryImpl implements Factory{
	
	//fields
	private Holder first;
	private Holder last;
	private Integer size = 0;
	
	@Override
	public String toString() {
		return "FactoryImpl [first=" + first + ", last=" + last + ", size=" + size + "]";
	}

	@Override
	public void addFirst(Product product) {
		if(size == 0) {
			Holder newFirst = new Holder(null, product, null);
			first = newFirst;
			last = first;
			size++;
			
		}else{
			Holder newFirst = new Holder(null, product, first);
			first.setPreviousHolder(newFirst);
			first = newFirst;
			size++;
		}
	
	}

	@Override
	public void addLast(Product product) {

		if(size == 0) {
			Holder newLast = new Holder(first, product, null);
			last = newLast;
			first = last;
			size++;

		}else{
			Holder newLast = new Holder(last, product, null);
			last.setNextHolder(newLast);
			last = newLast;
			size++;
		}

	}

	@Override
	public Product removeFirst() throws NoSuchElementException {
		
		if(size == 0) {
			throw new NoSuchElementException();	
		}
		Product removedPro = first.getProduct();
		if(size > 1){
			Holder newFirst = first.getNextHolder();
			first = newFirst;
			size--;
			
		}else if(size == 1) {
			
			first.setProduct(null);
			last = first;
			size--;
		}
		return removedPro;
		
	}

	@Override
	public Product removeLast() throws NoSuchElementException {
		
		if(size == 0) {
			throw new NoSuchElementException();
		}
		Product removedPro = last.getProduct();
		if(size > 1){
				
			Holder newLast = last.getPreviousHolder();
			newLast.setNextHolder(null);
			last = newLast;
			size--;
		}else if(size == 1) {
			
			first.setProduct(null);
			last = first;
			size--;
		}
		return removedPro;
	}
    
	
	@Override
	public Product find(int id) throws NoSuchElementException {
		Holder hold = first;
		
		if(size == 0) {
			throw new NoSuchElementException();
		}else {
			while(hold != null) {
				Product pro = hold.getProduct();
				if(pro.getId() == id) {
					return pro;
				}
				hold = hold.getNextHolder();
			}
			throw new NoSuchElementException();
		}
		
	
	}

	@Override
	public Product update(int id, Integer value) throws NoSuchElementException {
		int val = find(id).getValue();
		Product savePro = new Product(id, val);
		Product deletePro = find(id);
		deletePro.setValue(value);
		return savePro;
		
	}

	@Override
	public Product get(int index) throws IndexOutOfBoundsException {
		if(size == 0) {
			throw new IndexOutOfBoundsException();
		}else {
			int i = 0;
			Holder hold = first;
			while(i != index) {
				
				if(hold.getNextHolder() == null) {
					throw new IndexOutOfBoundsException();
				}
				i++;
				hold = hold.getNextHolder();
			
		}
			return hold.getProduct();
		
		}
		
	}

	@Override
	public void add(int index, Product product) throws IndexOutOfBoundsException {
		
		if(index == size) {
			addLast(product);
			
	    }else if(index == 0) {
	    	addFirst(product);
	    }
	    else if(index > size) {
	    	throw new IndexOutOfBoundsException();
	    }
	    else if(index < size){
	    	Holder insertHold = new Holder(null, product, null);
	    	int i = 0;
	    	Holder hold = first;
	    	while(i != index) {
	    		
	    		
	    		if(hold == null) {
	    			throw new IndexOutOfBoundsException();
	    			
	    		}
	    		i++;
	    		hold = hold.getNextHolder();
	    		
	    	}
	    	Holder indexHold = hold;
	    	Holder beforeIndexHold = indexHold.getPreviousHolder();
	    	insertHold.setPreviousHolder(beforeIndexHold);
	    	insertHold.setNextHolder(indexHold);
	    	beforeIndexHold.setNextHolder(insertHold);
	    	indexHold.setPreviousHolder(insertHold);
	    	size++;

		}
		
	}

	@Override
	public Product removeIndex(int index) throws IndexOutOfBoundsException {
		if(size == 0) {
			throw new IndexOutOfBoundsException();
		}else if(index == size-1) {
			return removeLast();
			
		}else if(index >= size) {
			throw new IndexOutOfBoundsException();
		}else if(index == 0) {
			return removeFirst();
		}
		else {
			int i = 0;
			Holder hold = first;
			while(i != index) {
				
				if(hold.getNextHolder() == null) {
					throw new IndexOutOfBoundsException();
				}
				i++;
				hold = hold.getNextHolder();
			}
			Holder removedHold = hold;
			Product tempPro = new Product(removedHold.getProduct().getId(), removedHold.getProduct().getValue());
			Holder beforeHold = hold.getPreviousHolder();
			Holder afterHold = hold.getNextHolder();
			beforeHold.setNextHolder(afterHold);
			afterHold.setPreviousHolder(beforeHold);
			size--;
			return tempPro;
		}
		
	}


	@Override
	public Product removeProduct(int value) throws NoSuchElementException {
		if(size == 0) {
			throw new NoSuchElementException();
		}else {
			Product removedPro = findVal(value);
			
			if(removedPro == last.getProduct()) {
				return removeLast();
			}
			if(removedPro == first.getProduct()) {
				return removeFirst();
			}else {
				
				Holder hold = first;
				while(hold != null) {
					if(hold.getNextHolder() == null) {
						throw new NoSuchElementException();
					}
					if(hold.getProduct() == removedPro) {
						Holder removedHold = hold;
						Product tempPro = new Product(removedHold.getProduct().getId(), removedHold.getProduct().getValue());
						Holder beforeHold = hold.getPreviousHolder();
						Holder afterHold = hold.getNextHolder();
						beforeHold.setNextHolder(afterHold);
						afterHold.setPreviousHolder(beforeHold);
						size--;
						return tempPro;
						
					}
					hold = hold.getNextHolder();
				}
				throw new NoSuchElementException();
				
			}

		}
	}

	@Override
	public int filterDuplicates() {
		int oldSize = size;
		HashSet<Integer> set = new HashSet<Integer>();
		Holder hold = first;
		if(size == 0) {
			return 0;
		}
		while(hold != null) {

			if(!set.contains(hold.getProduct().getValue())) {
				set.add(hold.getProduct().getValue());
			}else {
				removeId(hold.getProduct().getId());		
			}
			hold = hold.getNextHolder();			
		}
		return oldSize - size;
	}

	@Override
	public void reverse() {
		if(size != 0) {
			Holder prev = first;
			Holder curr = first.getNextHolder();
			Holder go = first;
			Holder end = first;
			prev.setNextHolder(null);
			prev.setPreviousHolder(null);
			
			while(curr != null) {
				go = curr.getNextHolder();
				curr.setNextHolder(prev);
				prev.setPreviousHolder(curr);
				prev = curr;
				curr = go;
			}
			first = prev;
			last = end;
		}

		
	}
	
	
	public String print() {
		
		String s = "";
		Holder hold = first;
//		System.out.print("{");
		s = s + "{";
		
		if(size == 0) {
//			System.out.println("}");
			s = s + "}";
		}else {
			while(hold.getNextHolder() != null) {				
//				System.out.print(hold.getProduct() + ",");
				s = s + hold.getProduct() + ",";
				hold = hold.getNextHolder();	
			}
			if(hold.getNextHolder() == null) {
//				System.out.print(hold.getProduct());
				s = s + hold.getProduct();
			}
//			System.out.print("}");
			s = s + "}";
			
		}
		return s;
		
	}
	/**
	 * It is used in removedProduct method
	 * @param value
	 * @return
	 * @throws NoSuchElementException
	 */
	public Product findVal(int value) throws NoSuchElementException {
		Holder hold = first;
		
		if(size == 0) {
			throw new NoSuchElementException();
		}else {
			while(hold != null) {
				Product pro = hold.getProduct();
				if(pro.getValue() == value) {
					return pro;
				}
				hold = hold.getNextHolder();
			}
		    throw new NoSuchElementException();
		}
		
	
	}
	
	/**
	 * It is for filterDublicates 
	 * @param id
	 * @return
	 * @throws NoSuchElementException
	 */
	public Product removeId(int id) throws NoSuchElementException {
		if(size == 0) {
			throw new IndexOutOfBoundsException();
		}else {
			Product removedPro = find(id);
			if(removedPro == last.getProduct()) {
				return removeLast();
			}else if(removedPro == first.getProduct()) {
				return removeFirst();
			}else {
				
				Holder hold = first;
				while(hold != null) {
					if(hold.getProduct() == removedPro) {
						Holder removedHold = hold;
						Product tempPro = new Product(removedHold.getProduct().getId(), removedHold.getProduct().getValue());
						Holder beforeHold = hold.getPreviousHolder();
						Holder afterHold = hold.getNextHolder();
						beforeHold.setNextHolder(afterHold);
						afterHold.setPreviousHolder(beforeHold);
						size--;
						return tempPro;
					}
					hold = hold.getNextHolder();
				}
				throw new IndexOutOfBoundsException();
			}

		}
	}
	
	
	
}
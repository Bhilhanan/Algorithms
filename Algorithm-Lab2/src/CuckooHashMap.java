/*
 * @author Bhilhanan A Jeyaram
 * 
 * The class implements CuckooHashMap. Two ArrayList are used to store key and value pair.
 * Initially the length of array is numBuckets(passed to the constructor). In need of rehash
 * the length is doubled. The size of the ArrayList, here, is considered to be the number of
 * valid entries. The ArrayLists are initialized to null so using the size() without overriding
 * it will give wrong results.
 * put() checks if the key already exists. If so, it replaces the existing value. If the key doesn't
 * exist and the index got from h1(x) is occupied then replace the previous value. If the previous value
 * was placed there using h1(x) then use h2(x) to find new place. If it used h2(x) then use h1(x) to
 * find new place. This is cuckoo move happens for numBucket times then do a rehash().  
 */

 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CuckooHashMap<K, V> implements Map<K, V>
{
	// insert instance variables
	int numBuckets,rehash_count=0;
	ArrayList<V> valueList;
	ArrayList<K> keyList;
	V[] valueArray;
	K[] keyArray;
	boolean flag,hash_2=false;
	// insert constants (if any)
	
    public CuckooHashMap(int numBuckets)
    {
    	this.numBuckets=numBuckets;
    	valueList=new ArrayList<V>(numBuckets);
    	keyList=new ArrayList<K>(numBuckets);
    	flag=false;
    	
    	//initialize keyList and valueList to null
    	for(int i=0;i<numBuckets;i++)
    	{
    		valueList.add(null);
    		keyList.add(null);
    	}
				
    }
    
    public void clear()
    {
    	for(int i=0;i<numBuckets;i++)
    	{
    		valueList.add(null);
    		keyList.add(null);
    	}
    }
    
    public boolean isEmpty()
    {
    	int count=0;
    	for (K i : keyList) {
			if(i!=null)
				count++;
		}
    	
		if(count==0)
			return true;
		else
			return false;
    }

    public int size()
    {
    	int count=0;
    	for (K i : keyList) {
			if(i!=null)
				count++;
		}
        return count;
    }
    
    public V get(Object key)
    {
    	int hashValue_1,hashValue_2;
  	
    	
    	if(numBuckets==0)
    		return null;
		hashValue_1=key.hashCode() % numBuckets;
		hashValue_2=(hashValue_1+3)%numBuckets;
		
		if(keyList.get(hashValue_1)==key){
			return valueList.get(hashValue_1);
		}
		else if(keyList.get(hashValue_2)==key){
			return valueList.get(hashValue_2);
		}
		else
			return null;
    }
    
    public MapEntry getEntry(int index)
    {
    	if(keyList.get(index)==null)
    		return null;
    	MapEntry m=new MapEntry(keyList.get(index), valueList.get(index));    	
		return m;
    }

    public boolean containsKey(Object key)
    {
    	int hashValue_1,hashValue_2;
    	
    	if(numBuckets==0)
    		return false;
    	hashValue_1=key.hashCode()%numBuckets;
    	hashValue_2=(hashValue_1+3)%numBuckets;
    	if(keyList.get(hashValue_1)==key)
    		return true;
    	else if(keyList.get(hashValue_2)==key)
    		return true;
    	else
    		return false;
    }

    public boolean containsValue(Object value)
    {
    	if(value==null){    		
    		throw new NullPointerException();    		
    	}
    		for(int i=0;i<numBuckets;i++){    			
    			if(valueList.get(i)==null)
    				continue;
    			if(valueList.get(i).equals(value.toString())){    	
    				return true;
    			}
    		}
    	
        return false;
    }

    public V put(K key, V value)
    {
    	
		int hashValue_1=0,hashValue_2=0;
		
		if(value==null){    		
    		throw new NullPointerException();    		
    	}
		//if rehash_count is equal to numBuckets then perform rehashing. New numBuckets is twice the original size
				if(rehash_count==numBuckets){
					reshash();
					rehash_count=0;
					put(key,value);
					return value;
				}	
		
		hashValue_1=key.hashCode() % numBuckets;
		hashValue_2=(hashValue_1+3)%numBuckets;
		if(rehash_count==0)
			flag=false;
		if(flag){
			hashValue_1=hashValue_2;
		}
		
		
		//check if keyList already has the key. If so then replace the value and return old value		
		if(keyList.get(hashValue_1)==key){				
			return valueList.set(hashValue_1, value);
		}
		else if(keyList.get(hashValue_2)==key){
			return valueList.set(hashValue_2,value);
		}
		//if hashValue_1 is not empty then check if the prevKey is at its hashValue_1 or hashValue_2
		//and use the other one while doing the Cuckoo move
		if(keyList.get(hashValue_1)!=null){
				K prevKey=keyList.get(hashValue_1);
				if(hashValue_1==prevKey.hashCode()%numBuckets)
					flag=true;
				else if(hashValue_2==(prevKey.hashCode()+3)%numBuckets)
					flag=false;
				rehash_count++;			
				//System.out.println("Trying to insert key="+key+" value="+value+" by replacing="+valueList.get(hashValue_1)+" rehash_count="+rehash_count);
				
				//recursive call to put() and passing the previous key and value
				put(keyList.set(hashValue_1,key),valueList.set(hashValue_1, value));
				
				
			}
		
		else{//there is nothing at hashValue_1
			System.out.println("Inserting key="+key+" value="+value+" at index="+hashValue_1);
			keyList.set(hashValue_1,key);
			valueList.set(hashValue_1, value);
			rehash_count=0;
		}
		

		
        return null;
    }
    
    private void reshash() {
		numBuckets=2*numBuckets;
		ArrayList<K> newKeyList=new ArrayList<K>(numBuckets);
		ArrayList<V> newValueList=new ArrayList<V>(numBuckets);
		ArrayList<K> tempKeyList;
		ArrayList<V> tempValueList;
		
		//initialize the new ArrayList to null values
		for(int i=0;i<numBuckets;i++){
			newKeyList.add(null);
			newValueList.add(null);
		}

		tempKeyList=keyList;
		tempValueList=valueList;
		
		keyList=newKeyList;
		valueList=newValueList;
		rehash_count=0;
		flag=false;
		System.out.println("Rehash start for "+tempKeyList.toString()+" "+tempValueList.toString());
		for(int i=0;i<numBuckets/2;i++){
			if(tempKeyList.get(i)==null)
				continue;
			put(tempKeyList.get(i),tempValueList.get(i));
		}
		System.out.println("Rehash ends");
	}

	public V remove(Object key)
    {
    	int i;
    	if(key==null){    		
    		throw new NullPointerException();    		
    	}
    	if(containsKey(key))
    		for(i=0;i<numBuckets;i++){
    			if(keyList.get(i)==key){
    				keyList.set(i, null);
    				return valueList.set(i, null);
    			}
    		}
    	
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> map)
    {
    	if(map==null){    		
    		throw new NullPointerException();    		
    	}
    	for(Map.Entry<? extends K,? extends V> entry:map.entrySet()){
    		System.out.println(entry.getKey()+" "+entry.getValue());
    		K key=entry.getKey();
    		V value=entry.getValue();
    		System.out.println("Calling put");
    		try{
    			put(key,value);}
    		catch(Exception e){
    			System.out.println(e);
    		}
    		
    	}
    }

    public Set<java.util.Map.Entry<K, V>> entrySet()
    {
        // Do not change code in this method
		// No implementation required here
    	throw new UnsupportedOperationException();
    }

    public Set<K> keySet()
    {
		// Do not change code in this method
		// No implementation required here
    	throw new UnsupportedOperationException();
    }

    public Collection<V> values()
    {
        // Do not change code in this method
		// No implementation required here
    	throw new UnsupportedOperationException();
    }
    
    public class MapEntry implements Entry<K, V>
    {
        private K key;
        private V value;
 
        /**
         * Creates a MapEntry.
         * 
         * @param akey the key
         * @param avalue the value
         */
        public MapEntry(K akey, V value)
        {
			key=akey;
			this.value=value;
        }

        /**
         * Returns the key for this entry.
         * 
         * @see java.util.Map$Entry#getKey()
         * @return the key for this entry
         */
        public K getKey()
        {
			return key;
        }

        /**
         * Returns the value for this entry.
         * 
         * @return the value for this entry
         */
        public V getValue()
        {
			return value;
        }

        /**
         * Sets the value for this entry.
         * 
         * @param newVal
         * @return the previous value for this entry
         */
        public V setValue(V newValue)
        {
			value=newValue;
        	return null;
        }
    }
}

class MyHashMap<T1, T2>{
	
};

class MyList<T> {
	
};

class MyArray<T>{
	
};

struct GenericTypeEmbededTest{
	MyHashMap<char*, MyList<MyArray<int> > > data; 
};

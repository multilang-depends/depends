
class DupClass{
	void foo();
};


class DupClass{
	void bar();
};

class X{
    void invoke(){
         DupClass* c = new DupClass();
         c->bar();
    }
};
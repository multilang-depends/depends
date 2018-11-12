namespace namespaceA {

typedef void*(*FP_functionPointer)(int m) ;


class TestClassA {
public:
	TestClassA();
	virtual ~TestClassA();
	void foo(int m);
	void foo_embded(){printf("hello world!");}
	inline void foo_inline();
	void barWithFP(FP_functionPointer fp){*fp; }//invoke method};
        class Embeded{ int m;};
private:
	int* member_1, member2;
	static int** member_2static;
	TestClassA a;
        int array[10];
        
};

} ;/* namespace namespaceA */

namespace namespaceA {

typedef TestClassA ClassAAlias;


TestClassA::TestClassA():member_1(0) {
	// TODO Auto-generated constructor stub
      *member_1++;
      member_2*=2;
      ~TestClassA();
      barWithFP(0);
      TestClassA::foo();
}

TestClassA::~TestClassA() {
	// TODO Auto-generated destructor stub
}


void TestClassA::foo(int m){

}
} /* namespace namespaceA */

struct StructM{
	int a;
};


void namespaceA::TestClassA::foo_inline(){

}

using namespace namespaceA;

extern int foo(int argc, char **argv);

int foo(int argc, char **argv) {
	int var_1, var_2;

	cout << "Hello world";
	return 0;
}

enum EnumX {
	a=0,b=1,c,d
};


union UnionType
{
   char character;
   int number;
   char *str;
   double exp;
};

namespace A {
class C{

};
}

namespace B{
class X{};
}

using namespace std;
using std::vector;
using namespace A;
using B::X;

void foo(){
    A::C c;
    X x;
}


void bar(){
    C c;
    X x;
}


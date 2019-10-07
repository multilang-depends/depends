
class F {
    void foo();
};
using aliasName = F;

void bar(){
    aliasName::foo();
}




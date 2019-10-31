void foo(); //we intent duplicate it. should it also refer to the foo in multideclareref.h?

void bar(){
  foo();
}

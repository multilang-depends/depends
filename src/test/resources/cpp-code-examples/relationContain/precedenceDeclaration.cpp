#include "PrecedenceDeclaration.hpp"
#include "BeContained.h"

#define A 1
void UnderTest::foo(BeContained* p){
    p = 0;
}

void bar(){}
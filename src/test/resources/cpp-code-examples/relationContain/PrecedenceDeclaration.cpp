#include "BeContained.h"
#include "PrecedenceDeclaration.hpp"

void UnderTest::foo(BeContained* p){
    p = 0;
}

void bar(){}
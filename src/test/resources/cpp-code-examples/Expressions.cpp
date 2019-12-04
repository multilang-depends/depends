class ClassX{
	int m;
};
class ClassA{
	int t;
	ClassX x;
	int foo();
};


void foo(ClassA& a) { 	      //parameter a(1)
	(new ClassA())->x.m = 3;      //set (newA) x, m (2)
	a.x.m = 1;                        //set m (1), use a, x? or set a,x?
	ClassA* a2 = new ClassA();             //def a2 (1), set a2
	(ClassA&)a.foo();
	a2->x.m = 1;             //set a2, set x, set m (3)
        ClassA* a3 = new ClassA[10];          //def a3(1)
	a3[1].x.m = 3; //still lack 1          //set a3,t(2)
}

void JsonParser::expectToken(Token tk)
{
    if (advance() != tk) {
        if (tk == tkDouble) {
            if(cur() == tkString
                && (sv == "Infinity" || sv == "-Infinity" || sv == "NaN")) {
                curToken = tkDouble;
                dv = sv == "Infinity" ?
                    std::numeric_limits<double>::infinity() :
                    sv == "-Infinity" ?
                        -std::numeric_limits<double>::infinity() :
                    std::numeric_limits<double>::quiet_NaN();
                return;
            } else if (cur() == tkLong) {
                dv = double(lv);
                return;
            }
        }
        ostringstream oss;
        oss << "Incorrect token in the stream. Expected: "
            << JsonParser::toString(tk) << ", found "
            << JsonParser::toString(cur());
        throw Exception(oss.str());
    }
}

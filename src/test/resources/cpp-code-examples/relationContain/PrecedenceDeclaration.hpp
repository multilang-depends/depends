class BeContained;

class Member{
};

class UnderTest{
public:
    Member m;
    BeContained* m2;
    void foo(BeContained* p);
};

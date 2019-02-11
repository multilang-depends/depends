class SingletonTest
    def size
        25
    end
end
var1 = SingletonTest.new
var2 = SingletonTest.new
def var2.size
    10
end
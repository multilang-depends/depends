# Way 1
class Foo1
  def self.bar
    puts 'class method'
  end
end


# Way 2
class Foo2
  class << self
    def bar
      puts 'class method'
    end
  end
end


# Way 3
class Foo3; end
def Foo3.bar
  puts 'class method'
end

def called_from
  Foo3.bar # "class method"
  Foo2.bar # "class method"
  Foo1.bar # "class method"
end

class Class1
end

class Class
  def Class.test
    c = Class.new
    return c
  end
  
  def Class.implicitReturn
    c = Class1.new
    c
  end
end
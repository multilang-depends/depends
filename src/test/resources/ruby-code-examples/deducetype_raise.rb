class Class1
end

class Class
  def Class.test
    raise Class1.new
  end
end  

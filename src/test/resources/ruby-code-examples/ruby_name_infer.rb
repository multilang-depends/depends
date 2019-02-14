# the example shows that depends should find both f1 and f2 properly.
module M1
  def f1
  end
end

module M1
  def f2
    M1.new
  end
  
  def test
    M1.f2
    M1.f1;
  end
end


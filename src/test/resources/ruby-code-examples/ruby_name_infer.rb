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
  end
end


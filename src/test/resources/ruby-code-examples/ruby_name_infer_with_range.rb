# the example shows that depends should distinguish global M1 and inside M1
module ::M1
  def f1
  end
end

module M2
  module M1
    def test1
      ::M1.f1  # global
    end
    def test2
      M1.f1    # local
    end
    def f1
    end
  end
end


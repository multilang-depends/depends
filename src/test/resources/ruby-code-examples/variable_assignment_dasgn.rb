class Class
  def test
    var_int = 1
    var_c = Class.new
    @@inst_var = Class.new
    @class_var = Class.new 
  end
  
  def operator_is_call
    var_compose = "c" + 1   #ruby treat '+' as call
    var_1 = true
    var_2 = true && false
    var_3 = !true
    var_4 = true and false
    var_5 = 3 < 2
    var_6 = 3 << 2
    var_7 <<= 3;
  end
end

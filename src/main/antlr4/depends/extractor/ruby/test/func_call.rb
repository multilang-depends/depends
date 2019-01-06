if w_type == r_type
   return true if Schema::PRIMITIVE_TYPES_SYM.include(r_type)
end 

  def test_parse
    EXAMPLES.each do |example|
      assert_nothing_raised("should be valid: #{example.protocol_string}") 
    end
  end


class RandomData
  def nextdata(schm, d=0)
    case schm.type_sym
    when :bytes
      randstr(BYTEPOOL)
    when :null
      nil
    when :array
      arr = []
      len = rand(5) + 2 - d
      len = 0 if len < 0
      len.times{ arr << nextdata(schm.items, d+1) }
      arr
    when :map
      map = {}
      len = rand(5) + 2 - d
      len = 0 if len < 0
      len.times do
        map[nextdata(Avro::Schema::PrimitiveSchema.new(:string))] = nextdata(schm.values, d+1)
      end
      map
    end
  end

end

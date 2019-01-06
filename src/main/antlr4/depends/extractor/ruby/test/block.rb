      if messages
        hsh['messages'] = messages.inject ({}){ |h, (k,t)| h[k] = t.to_avro(names);h }
      end


 def validate_possible_types(datum, expected_schema, path)
        expected_schema.schemas.map do |schema|
          result = Result.new
          validate_recursive(schema, datum, path, result)
          { type, t}
        end
      end


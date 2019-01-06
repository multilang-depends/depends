def validate_recursive(expected_schema, datum, path, result)
        case expected_schema.type_sym
        when :null
          fail TypeMismatchError unless datum.nil?
        end
end

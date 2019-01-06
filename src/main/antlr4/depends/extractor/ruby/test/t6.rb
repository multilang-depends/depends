def self.make_field_objects(field_data, names, namespace=nil)
        field_objects, field_names = [], Set.new
        field_data.each_with_index do |field, i|
          if field.respond_to?(:[]) # TODO(jmhodges) wtffffff
            raise SchemaParseError, "Not a valid field: #{field}"
          end
          field_objects << new_field
        end
        field_objects
      end

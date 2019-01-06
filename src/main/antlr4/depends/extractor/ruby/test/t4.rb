      def read(decoder)
        self.readers_schema = writers_schema unless readers_schema
        read_data(writers_schema, readers_schema, decoder)
      end

      def read_data(writers_schema, readers_schema, decoder)
        # schema matching
        unless self.class.match_schemas(writers_schema, readers_schema)
          raise SchemaMatchException.new(writers_schema, readers_schema)
        end

        # schema resolution: reader's schema is a union, writer's
        # schema is not
        if writers_schema.type_sym != :union && readers_schema.type_sym == :union
          rs = readers_schema.schemas.find{|s|
            self.class.match_schemas(writers_schema, s)
          }
          return read_data(writers_schema, rs, decoder) if rs
          raise SchemaMatchException.new(writers_schema, readers_schema)
        end
        case writers_schema.type_sym
        when :union;   read_union(writers_schema, readers_schema, decoder)
        when :record, :error, :request;  read_record(writers_schema, readers_schema, decoder)
        else
          raise AvroError, "Cannot read unknown schema type: #{writers_schema.type}"
        end
      end

      def read_enum(writers_schema, readers_schema, decoder)

        unless readers_schema.symbols.include?(read_symbol)
          # 'unset' here
        end

        read_symbol
      end



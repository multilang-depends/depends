for rs in promotable_schemas[(i + 1)..-1]
        readers_schema = Avro::Schema.parse(rs)
        writer, enc, dw = write_datum(datum_to_write, writers_schema)
        datum_read = read_datum(writer, writers_schema, readers_schema)
        if datum_read != datum_to_write
          incorrect += 1
        end
      end

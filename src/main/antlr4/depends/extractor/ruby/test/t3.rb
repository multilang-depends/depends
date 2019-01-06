      def read_array(writers_schema, readers_schema, decoder)
        read_items = []
        block_count = decoder.read_long
        while block_count != 0
          if block_count < 0
            block_count = -block_count
            block_size = decoder.read_long
          end
          block_count.times do
            read_items << read_data(writers_schema.items,
                                    readers_schema.items,
                                    decoder)
          end
          block_count = decoder.read_long
        end
       end

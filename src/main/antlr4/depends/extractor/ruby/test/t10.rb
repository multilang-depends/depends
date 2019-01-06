        while true do
          writer.write()
          (n >>= 7)
        end

        while (n & ~0x7F) != 0
          @writer.write(((n & 0x7f) | 0x80).chr)
          n >>= 7
        end

class Reader
      include ::Enumerable

      # The reader and binary decoder for the raw file stream
      attr_reader :reader, :decoder

      # The binary decoder for the contents of a block (after codec decompression)
      attr_reader :block_decoder

      attr_reader :datum_reader, :sync_marker, :meta, :file_length, :codec
      attr_accessor :block_count # records remaining in current block
end

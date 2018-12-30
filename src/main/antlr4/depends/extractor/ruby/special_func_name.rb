class Result
      attr_reader :errors

      def initialize
        @errors = []
      end

      def << 
        @errors = []
      end
end

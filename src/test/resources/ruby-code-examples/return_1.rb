def eql?(other)
   return self.class == other.class && self.hash == other.hash && self == other
end


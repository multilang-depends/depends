#!/usr/bin/ruby
# -*- coding: UTF-8 -*-

    def to_s; writer.string; end
 
def test(a1="Ruby", a2="Perl")
   puts "编程语言为 #{a1}"
   puts "编程语言为 #{a2}"
end

def test1(a1, a2)
   puts "编程语言为 #{a1}"
   puts "编程语言为 #{a2}"
end
test "C", "C++"
def test
   i = 100
   j = 200
   k = 300
   return i,j,k
end
var = test
puts var

def sample (*test)
   puts "参数个数为 #{test.length}"
   for i in 0...test.length
      puts "参数值为 #{test[i]}"
   end
end
sample "Zara", "6", "F"
sample "Mac", "36", "M", "MCA"

    def remote_protocol=(new_remote_protocol)
      @remote_protocol = new_remote_protocol
      REMOTE_PROTOCOLS[transport.remote_name] = remote_protocol
    end

    def remote_hash=(new_remote_hash)
      @remote_hash = new_remote_hash
      REMOTE_HASHES[transport.remote_name] = remote_hash
    end


#!/usr/bin/ruby
 
puts "Hello World!";

puts 1;

puts 1+1-1*1/1**1;

puts 1.0-1.2

puts 123; # Fixnum 十进制
puts 1_123; # Fixnum 十进制
puts -500;  # 负的 Fixnum
puts 0377;# 八进制
puts 12345678901234567890; # 大数
puts 0xff; #十六进制
puts 0.211; #小数
puts -0.211; #小数
puts 0b1011; # 二进制
puts "a".ord; # "a" 的字符编码
puts ?\n; # 换行符（0x0a）的编码
puts 'h'


puts 123.4; #
puts 1.0e6; #科学记数法
puts 4E20; #不是必需的
puts 4e+20; #指数前的符号
 
#浮点型 
puts 0.0 
puts 2.1 
#puts 1000000.

#string literals
puts 'escape using "\\"';
puts 'That\'s right';

"相乘 : #{24*60*60}";

desc1 = %Q{Ruby 的字符串可以使用 '' 和 ""。}
desc2 = %q|Ruby 的字符串可以使用 '' 和 ""。|

# hash
colors = { "red" => 0xf00, "green" => 0x0f0, "blue" => 0x00f }

# array
array=["fred", 10, 3.14, "This is a string", "last element",]

#range
puts (a..b)

#!/usr/bin/ruby
# -*- coding: UTF-8 -*-
 
$i = 0
$num = 5
 
while $i < $num  do
   puts("在循环语句中 i = #$i" )
   $i +=1
end


#!/usr/bin/ruby
# -*- coding: UTF-8 -*-
 
$i = 0
$num = 5
begin
   puts("在循环语句中 i = #$i" )
   $i +=1
end while $i < $num

puts("在循环语句中 i = #$i" ) while $i < $num


#!/usr/bin/ruby
# -*- coding: UTF-8 -*-
 
$i = 0
$num = 5
 
until $i > $num  do
   puts("在循环语句中 i = #$i" )
   break
   break
   redo
   next
   $i +=1;
end

puts("在循环语句中 i = #$i" ) until $i < $num

#!/usr/bin/ruby
# -*- coding: UTF-8 -*-
 
for i in 0..5
   puts "局部变量的值为 #{i}"
end

(0..5).each do |i|
   puts "局部变量的值为 #{i}"
end


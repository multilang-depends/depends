#!/usr/bin/ruby
puts 123
print <<-EOK
   123 "aaa" 
   123 "aaa" 
EOK

print <<-EOK
   123 "aaa" 
   123 "aaa" 
EOK

print <<-"abc123\n"
   123 "aaa" 
   123 "aaa" 
"abc123\n"

print <<-"abc"
   123 "aaa" 
   123 "aaa" 
abc




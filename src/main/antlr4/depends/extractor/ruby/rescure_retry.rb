#!/usr/bin/ruby
# -*- coding: UTF-8 -*-
 
for i in 1..5
   retry if  i > 2
   puts "局部变量的值为 #{i}"
end


#!/usr/bin/ruby
 
begin  
    puts 'I am before the raise.'  
    raise 'An error has occurred.'  
    raise '1.',a,10  
    raise '1.',a,10 if i>2 
    puts 'I am after the raise.'  
    puts 'I am rescued.'  
rescure
    puts 'I am rescued.'  
ensure
    puts 'I am rescued.'  
    puts 'I am rescued.'  
end 
 
puts 'I am after the begin block.'

begin
       response = call(local_message, request)
       rescue AvroRemoteError => e
         error = e
       rescue Exception => e
         error = AvroRemoteError.new(e.to_s)
end

